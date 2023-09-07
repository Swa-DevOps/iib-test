package com.iib.platform.servlets;

import java.io.IOException;

import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iib.platform.services.SoapAPIService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.iib.platform.common.utils.EncryptDecrypt;
import com.iib.platform.services.DatabaseConnectionService;
import com.iib.platform.services.EmandateBankDataService;

/**
 * E-NACH DOB Servlet
 *
 * @author Indigo Consultuing - Niket Goel
 *
 */
@Component(service = { Servlet.class }, name = "CFD E-NACH DEAL Check Servlet", enabled = true, property = {
		Constants.SERVICE_DESCRIPTION + "=" + "CFD E-NACH DEAL Check Servlet",
		"sling.servlet.methods=" + HttpConstants.METHOD_POST, "sling.servlet.paths=" + "/bin/cfd/dealVerify" })
public class CFDEnachDealServlet extends SlingAllMethodsServlet {

	private static final long serialVersionUID = 1L;
	
	private static final String ERROR_MSG2="We are unable to process your e-NACH mandate registration as the bank linked to your CFD is not participating in e-NACH registration. If you wish to process with e-NACH by changing your Bank account details please click 'PROCEED'. If you wish to proceed with soft NACH registration for the existing bank account linked to CFD, please click 'OKAY'.";
	private static final String SUCCESS="success";
	private static final String ERROR="error";
	private static Logger log = LoggerFactory.getLogger(CFDEnachDealServlet.class);
	transient JsonParser jsonParser = new JsonParser();
	
	@Reference
	transient EmandateBankDataService emandateBankDataService;

	@Reference
	transient SoapAPIService soapAPIService;

	@Reference
	transient EncryptDecrypt ecryptDecrypt;

	@Reference
	transient DatabaseConnectionService databaseConService;

	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		String mKey = request.getHeader("X-AUTH-SESSION");

		String jsonObject = request.getHeader("X-AUTH-TOKEN");
		String encryptKey = jsonObject.substring(jsonObject.indexOf(':') + 1);
		jsonObject = jsonObject.substring(0, (jsonObject.indexOf(':')));
		String requestParamJson = "";
		requestParamJson = EncryptDecrypt.decrypt(encryptKey, encryptKey, jsonObject);
		JsonObject requestJson = null;

		String mobileNumber = "";
		String dealno = "";
		try {
			requestJson = (JsonObject) jsonParser.parse(requestParamJson);
			mobileNumber = requestJson.has("mobile")?requestJson.get("mobile").getAsString():"";
			log.error("Request Json We Pass {}", requestJson);
			if(requestJson.has("dealno"))
			dealno =  (requestJson.has("dealno") && requestJson.get("dealno") != null)?requestJson.get("dealno").getAsString() :"";
		} catch (Exception e) {
			log.error("Exception e", e);
		}
		log.debug("REquest WE GET {} {} {}", jsonObject, encryptKey, requestParamJson);

		try {

			if ((null == mKey))
				out.println("unauthorized access 1");
			else if (mKey.length() != 36)
				out.println("unauthorized access 2");
			else if (databaseConService.getLoginCFDFSSession(mKey, mobileNumber, encryptKey))
				out.println("unauthorized access 3");
			else {

				
				response.setStatus(200);
				JSONObject val = fetchAccountDetails(mobileNumber,dealno);
				String str = val.toString();
				log.info("Deal we fecth and passed {}", str);
				if ((val.getString(SUCCESS).equalsIgnoreCase("false") ))
					out.print(EncryptDecrypt.encrypt(encryptKey, encryptKey, "passtoNoGo" + str));
				else	
					out.print(EncryptDecrypt.encrypt(encryptKey, encryptKey, "passtoGo" + str));
				out.flush();

			}

		} catch (Exception e) {
			log.error("Exception in e-NACH DEAL Servlet", e);
		}
	}
	
	public JSONObject fetchAccountDetails(String mobileNumber, String dealno)

	{
		JSONObject response = null;
		try {
			JSONArray jsonArray = soapAPIService.getCFDDealDetails(mobileNumber,dealno);
			String errorString = "";
			JSONObject tempJsoni;
			boolean flag = false;
			
			for(int i = 0; i < jsonArray.length(); i++)
			{
				tempJsoni = jsonArray.getJSONObject(i);
				if(tempJsoni.has(ERROR))
				{
					errorString = tempJsoni.getString(ERROR);
					flag=false;
				}else
				{
					flag=true;
					String check = checkValidBillDeskBank(jsonArray);
					if(check.equalsIgnoreCase(ERROR_MSG2))
					{
						flag = false;
						errorString = check;
					}
					
				}
			}
			

			if (flag) {
				response = new JSONObject();
				response.put(SUCCESS, "true");
				response.put("dealresult", jsonArray.getJSONObject(0).toString());
			} else {
				response = new JSONObject();
				if(errorString.equalsIgnoreCase(ERROR_MSG2)) {
					response.put(SUCCESS, "action");
					response.put(ERROR, errorString);
					response.put("dealresultA", jsonArray.getJSONObject(0).toString());
				}
				else	
					response.put(SUCCESS, "false");
				response.put(ERROR, errorString);
			}

			return response;
		} catch (Exception e) {
			log.error("Exception", e);
			return response;
		}

	}
	
	private String checkValidBillDeskBank(JSONArray jsonArray)
	{
		String erromsg = "";
		JSONArray result = jsonArray;
		String bankname="";
		try {
			bankname = result.getJSONObject(0).getJSONArray("Customer_Dt").getJSONObject(0).getString("Customer_Bank");
		} catch (JSONException e) {
			//To Do
		}
		boolean verifiedBankCode = emandateBankDataService.getCFDEnachRepaymentBankNameVerify(bankname);

		if (!verifiedBankCode) {
			erromsg=ERROR_MSG2;
		}else
		{
			erromsg = result.toString();
		}
		
		return erromsg;
		
	}
}
