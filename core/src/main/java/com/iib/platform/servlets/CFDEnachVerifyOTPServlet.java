package com.iib.platform.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iib.platform.services.HttpAPIService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.iib.platform.services.DatabaseConnectionService;
import com.iib.platform.services.SoapAPIService;
import com.iib.platform.services.EmandateBankDataService;
import com.iib.platform.common.utils.EncryptDecrypt;

/**
 * e-NACH Verify OTP Servlet
 *
 * @author ayasya
 *
 */
@Component(immediate = true, service = { Servlet.class }, property = {
		Constants.SERVICE_DESCRIPTION + "=" + "E-NACH Verify OTP Servlet",
		"sling.servlet.methods=" + HttpConstants.METHOD_POST, "sling.servlet.paths=" + "/bin/cfd/enachVerify" })
public class CFDEnachVerifyOTPServlet extends SlingAllMethodsServlet {

	private static final long serialVersionUID = 1L;
	private static String DBENCRYPTYKEY= "!ndus!nd!ndus!nd";
	
	private static final String ERROR_MSG2="We are unable to process your e-NACH mandate registration as the bank linked to your CFD is not participating in e-NACH registration. If you wish to process with e-NACH by changing your Bank account details please click 'PROCEED'. If you wish to proceed with soft NACH registration for the existing bank account linked to CFD, please click 'OKAY'.";
	private static final String SUCCESS="success";
	private static final String ERROR="error";
	private static final String CUSTOMER_DT="Customer_Dt";
	private static final String CUSTOMER="customer";
	private static final String DEALS="deals";
	private static final String FALSE="false";
	private static final String SINGLE="single";
	private static final String ERRORMSG="errormsg";
	
	/** Static Logger */
	private static Logger log = LoggerFactory.getLogger(CFDEnachVerifyOTPServlet.class);

	private String actionIfAny=""; 
	transient JsonParser jsonParser = new JsonParser();

	@Reference
	transient EmandateBankDataService emandateBankDataService;
	
	@Reference
	transient HttpAPIService httpAPIService;

	@Reference
	transient EncryptDecrypt ecryptDecrypt;

	@Reference
	transient SoapAPIService soapAPIService;

	@Reference
	transient DatabaseConnectionService databaseConService;

	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		String ekKey = request.getHeader("X-AUTH-SESSION");
		String jsonObject = request.getHeader("X-AUTH-TOKEN");
		String encryptKey = jsonObject.substring(jsonObject.indexOf(':') + 1);
		jsonObject = jsonObject.substring(0, (jsonObject.indexOf(':')));
		String requestParamJson = "";
		requestParamJson = EncryptDecrypt.decrypt(encryptKey, encryptKey, jsonObject);
		JsonObject requestJson = null;

		String mobile = "";
		String otp = "";
		String fetchDeal ="";
		try {
			requestJson = (JsonObject) jsonParser.parse(requestParamJson);
			mobile = requestJson.get("mobile").getAsString();
			otp = requestJson.get("otp").getAsString();
			if(requestJson.has("custcode"))
				fetchDeal = requestJson.get("custcode").getAsString();

		} catch (Exception e) {
			log.error("Exception at main", e);
		}

	

		try {

			if ((null == ekKey) || (null == requestParamJson))
				out.println("unauthorized access to server");
			else if (ekKey.length() != 36)
				out.println("unauthorized access");

			else if (databaseConService.getLoginCFDFSSession(ekKey, mobile, encryptKey)) {
				out.println("unauthorized access");
			}

			else if(fetchDeal.equalsIgnoreCase("")){

				
				// Database Changes START
				boolean validOtpCheck = databaseConService.validateOtpDetailsWithKey(EncryptDecrypt.encrypt(DBENCRYPTYKEY, DBENCRYPTYKEY, mobile), otp, "cfd-enach-mandate",
						ekKey, encryptKey);

				if (validOtpCheck) {

					response.setStatus(200);
					JSONObject val = fetchAccountDetails(mobile, ekKey,encryptKey);
					String str = val.toString();
					out.print(EncryptDecrypt.encrypt(encryptKey, encryptKey, "passtoGo" + str));
					out.flush();
				} else {
					out.print(EncryptDecrypt.encrypt(encryptKey, encryptKey, "passnoGo"));
					out.flush();
				}

				// Database Changes END

			}else {
				
				response.setStatus(200);
				JSONObject val = fetchDealDetails(mobile, ekKey,fetchDeal,encryptKey);
				String str = val.toString();
				out.print(EncryptDecrypt.encrypt(encryptKey, encryptKey, "passtoGo" + str));
				out.flush();
			
			}
		} catch (Exception e) {
			out.println(e.getMessage());
		}

	}

	private JSONObject fetchDealDetails(String mobileNumber, String mKey, String fetchDeal,  String encryptKey )

	{
		JSONObject response = new JSONObject();
		try {
			String dealDetails = databaseConService.getCFDCifDetails(EncryptDecrypt.encrypt(DBENCRYPTYKEY, DBENCRYPTYKEY, mobileNumber), mKey, encryptKey);
			JSONArray result = new JSONArray(EncryptDecrypt.decrypt(DBENCRYPTYKEY, DBENCRYPTYKEY, dealDetails));
			Set<String> deals = new HashSet<>();
			for(int i=0; i<result.length();i++)
			{	
				if(result.getJSONObject(i).getString("Customer_Code").equalsIgnoreCase(fetchDeal)) {
			
						deals.add(result.getJSONObject(i).getString("Deal_No"));
						log.info("Added {} ",result.getJSONObject(i).getString("Deal_No"));	
				}
			}
			
			if(deals.size()>1) {
				response.put(DEALS, deals.toString());
				response.put(SUCCESS, "true");
			}
			else {
				String action = checkValidBillDeskBank(mobileNumber,deals.toString());
				if(action.equalsIgnoreCase(ERROR_MSG2) || (action.indexOf("Customer_Code")>-1)) {
					response.put(SINGLE,fetchDeal+":"+action );
					if(action.equalsIgnoreCase(ERROR_MSG2)) {
						response.put(SUCCESS, "action");
						response.put("dealsA", actionIfAny);
					}
					else						
						response.put(SUCCESS, "true");
				}
				else {
					
					response.put(SUCCESS, FALSE);
					response.put(ERRORMSG, action);
				}
					
			}
			
			return response;
		} catch (Exception e) {
			log.error("Exception", e);
			return response;
		}

	}
		
	private String checkValidBillDeskBank(String mobileNumber,String dealID)
	{
		String erromsg = "";
		log.info("Detaisl we passed {}  {}",mobileNumber,dealID);
		JSONArray result = null;
		if(dealID.endsWith("\"") || dealID.endsWith("]") )
			result= soapAPIService.getCFDDealDetails(mobileNumber, dealID.substring(1, dealID.length()-1));
		else
			result= soapAPIService.getCFDDealDetails(mobileNumber, dealID);
		
		log.info("Result we get: {}", result);
		String bankname="";
		
	
		try {
			if(!(result.getJSONObject(0).has(ERROR))) {
			bankname = result.getJSONObject(0).getJSONArray(CUSTOMER_DT).getJSONObject(0).getString("Customer_Bank");
			boolean verifiedBankCode = emandateBankDataService.getCFDEnachRepaymentBankNameVerify(bankname);
			log.info("Result we get: {}", verifiedBankCode);
			
			if ((!verifiedBankCode) && !(result.getJSONObject(0).has(ERROR)) ) {
				erromsg=ERROR_MSG2;
				actionIfAny=result.getJSONObject(0).getJSONArray(CUSTOMER_DT).getJSONObject(0).toString();
			}else
			{
				erromsg = result.getJSONObject(0).getJSONArray(CUSTOMER_DT).getJSONObject(0).toString();
			}
			
			}else
			{
				erromsg = result.getJSONObject(0).getString(ERROR);
			}
		} catch (JSONException e) {
			log.error("chec bank error in cfdenach vefriy otp",e);
		}
		
		
		
		return erromsg;
		
	}
	public JSONObject fetchAccountDetails(String mobileNumber, String mKey, String encryptKey )

	{
		JSONObject response = null;
		try {
			
			JSONArray jsonArray = soapAPIService.getCFDAccountDetails(mobileNumber);
			
			log.info("values we get from db  {} ",jsonArray.toString());
			
			log.info("values we get from arra  {} ",jsonArray.get(0));	
			
			boolean flag = false;
			boolean cifUpdated=false;
			boolean apiIssue = false;
			
			log.info("values we get {} ",jsonArray.getJSONObject(0).has("CustomerCodes"));
			
			if(jsonArray.length()>0) {
			if(jsonArray.getJSONObject(0).has("CustomerCodes"))
				cifUpdated = databaseConService.updateCFDCifDetails(EncryptDecrypt.encrypt(DBENCRYPTYKEY, DBENCRYPTYKEY, mobileNumber), EncryptDecrypt.encrypt(DBENCRYPTYKEY, DBENCRYPTYKEY, jsonArray.getJSONObject(0).getJSONArray("CustomerCodes").toString()), mKey);
			else
				cifUpdated = databaseConService.updateCFDCifDetails(EncryptDecrypt.encrypt(DBENCRYPTYKEY, DBENCRYPTYKEY, mobileNumber), EncryptDecrypt.encrypt(DBENCRYPTYKEY, DBENCRYPTYKEY, jsonArray.getJSONObject(0).toString()), mKey);
			
			}else {
				
				cifUpdated = databaseConService.updateCFDCifDetails(EncryptDecrypt.encrypt(DBENCRYPTYKEY, DBENCRYPTYKEY, mobileNumber), "ciffetchIssue", mKey);
				
			}
			
			
			if (!cifUpdated) {
				log.info("Unable to update database details for mobile Number : {}", mobileNumber);
			}
			String errorString = "";

			JSONObject tempJsoni = null;
		
			
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
				}
			}
			

			if (flag) {
				response = new JSONObject();
				response.put(SUCCESS, "true");
				response.put("isDobRequired", FALSE);
				if(jsonArray.getJSONObject(0).has(CUSTOMER))
					response.put(CUSTOMER, jsonArray.getJSONObject(0).getJSONArray(CUSTOMER).toString());
				if(jsonArray.getJSONObject(0).has(DEALS))
					response.put(DEALS, jsonArray.getJSONObject(0).getJSONArray(DEALS).toString());
				if(jsonArray.getJSONObject(0).has(SINGLE))
				{
					JSONArray jsonObj = new JSONArray(jsonArray.getJSONObject(0).getJSONArray(SINGLE).toString());
					
					String action = checkValidBillDeskBank(mobileNumber, jsonObj.getJSONObject(0).getJSONArray(CUSTOMER_DT).getJSONObject(0).getString("Deal_No"));
					
					
					
					if(action.equalsIgnoreCase(ERROR_MSG2) || (action.indexOf("Customer_Code")>-1)) {
						response.put(SINGLE,jsonObj.getJSONObject(0).getJSONArray(CUSTOMER_DT).getJSONObject(0).getString("Customer_Code")+":"+action );
						if(action.equalsIgnoreCase(ERROR_MSG2)) {
							response.put(SUCCESS, "action");
							response.put("dealsA", actionIfAny);
						}
						else						
							response.put(SUCCESS, "true");
					}
					else {
						
						response.put(SUCCESS, FALSE);
						response.put(ERRORMSG, action);
					}
					
					
					
					
					
					
					
					/*response.put(SINGLE,fetchDeal+":"+action );
					log.info("bank validateion  : {}", action);
					if((action.equalsIgnoreCase(ERROR_MSG2))) {
						response.put(SUCCESS, "action");
						response.put(ERRORMSG,action );
						response.put("dealsA", actionIfAny);
						response.put(SINGLE, "oo");
					}
					else {
						
						response.put(SUCCESS, "true");
						
					}*/
				}
			} else {
				response = new JSONObject();
				response.put(SUCCESS, FALSE);
				response.put(ERRORMSG, errorString);
			}

			if(response.has(ERRORMSG) && response.get(ERRORMSG).toString().equalsIgnoreCase("Some Technical Error Occured"))
				databaseConService.updateCFDCifDetails(EncryptDecrypt.encrypt(DBENCRYPTYKEY, DBENCRYPTYKEY, mobileNumber), "ciffetchIssue", mKey);
			log.info("respone returnred validateion  : {}", response);
			
			return response;
		} catch (Exception e) {
			log.error("Exception", e);
			return response;
		}

	}
}