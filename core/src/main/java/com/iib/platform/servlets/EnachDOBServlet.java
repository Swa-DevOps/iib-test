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

/**
 * E-NACH DOB Servlet
 *
 * @author Indigo Consultuing - Niket Goel
 *
 */
@Component(service = { Servlet.class }, name = "E-NACH DOB Check Servlet", enabled = true, property = {
		Constants.SERVICE_DESCRIPTION + "=" + "E-NACH DOB Servlet",
		"sling.servlet.methods=" + HttpConstants.METHOD_POST, "sling.servlet.paths=" + "/bin/dobVerify" })
public class EnachDOBServlet extends SlingAllMethodsServlet {

	private static final long serialVersionUID = 1L;
	
	private static final String UNAUTH  = "unauthorized access";
	private static final String ACT  = "AccountType";
	private static final String ACN  = "Account_Number";
	private static final String BCI  = "Banking_Cif_Id";
	

	private static Logger log = LoggerFactory.getLogger(EnachDOBServlet.class);
	transient JsonParser jsonParser = new JsonParser();

	@Reference
	transient SoapAPIService soapApiService;

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
		String dob = "";
		try {
			requestJson = (JsonObject) jsonParser.parse(requestParamJson);
			mobileNumber = requestJson.get("mobile").getAsString();
			dob = requestJson.get("dob").getAsString();
		} catch (Exception e) {
			log.error("Exception e", e);
		}
		log.debug("REquest WE GET {} {} {}", jsonObject, encryptKey, requestParamJson);

		try {

			if ((null == mKey))
				out.println(UNAUTH);
			else if (mKey.length() != 36)
				out.println(UNAUTH);
			else if (databaseConService.getLoginDOBSession(mKey, mobileNumber, encryptKey))
				out.println(UNAUTH);
			else {

				String input = databaseConService.getCifDetails(mobileNumber, mKey, encryptKey);
				JSONArray inputArray = new JSONArray(input);
				log.info(" e-NACH DOB Servlet {}", inputArray);
				JSONArray jsonArray;
				jsonArray = inputArray;
				String tempDate = "";

				log.info(" e-jsonArray DOB Servlet {}", jsonArray);

				log.info(" e-jsonArray Length {}", jsonArray.length());

				JSONArray accountsFromDOB = new JSONArray();
				if (!dob.equalsIgnoreCase("")) {
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject tempJson = jsonArray.getJSONObject(i);
						tempDate = tempJson.getString("DOB");
						if (tempDate.equals(dob)) {
							JSONObject jsonobj = new JSONObject();
							jsonobj.put(ACT, tempJson.getString(ACT));
							jsonobj.put(ACN, tempJson.getString(ACN));
							jsonobj.put(BCI, tempJson.getString(BCI));
							jsonobj.put("DOB", tempJson.getString("DOB"));

							accountsFromDOB.put(jsonobj);
						}
					}
				} else {
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject tempJson = jsonArray.getJSONObject(i);
						JSONObject jsonobj = new JSONObject();
						jsonobj.put(ACT, tempJson.getString(ACT));
						jsonobj.put(ACN, tempJson.getString(ACN));
						jsonobj.put(BCI, tempJson.getString(BCI));
						jsonobj.put("DOB", tempJson.getString("DOB"));

						accountsFromDOB.put(jsonobj);
					}
				}

				log.debug(" accountsFromDOB Length {} {}", accountsFromDOB.length(), accountsFromDOB);

				out.println(EncryptDecrypt.encrypt(encryptKey, encryptKey, accountsFromDOB.toString()));
			}

		} catch (Exception e) {
			log.error("Exception in e-NACH DOB Servlet", e);
		}
	}
}
