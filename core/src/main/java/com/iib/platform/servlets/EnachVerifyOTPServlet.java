package com.iib.platform.servlets;

import java.io.IOException;
import java.io.PrintWriter;


import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.json.JSONArray;
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
import com.iib.platform.common.utils.EncryptDecrypt;


/**
 * e-NACH Verify OTP Servlet
 *
 * @author ayasya
 *
 */
@Component(immediate = true, service = { Servlet.class }, property = {
		Constants.SERVICE_DESCRIPTION + "=" + "E-NACH Verify OTP Servlet",
		"sling.servlet.methods=" + HttpConstants.METHOD_POST, "sling.servlet.paths=" + "/bin/enachVerify" })
public class EnachVerifyOTPServlet extends SlingAllMethodsServlet {

	private static final long serialVersionUID = 1L;
	
	private static String DBENCRYPTYKEY= "!ndus!nd!ndus!nd";

	/** Static Logger */
	private static Logger log = LoggerFactory.getLogger(EnachVerifyOTPServlet.class);

	transient JsonParser jsonParser = new JsonParser();

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
		String mode = "";

		try {
			requestJson = (JsonObject) jsonParser.parse(requestParamJson);
			mobile = requestJson.get("mobile").getAsString();
			otp = requestJson.get("otp").getAsString();
			if(requestJson.has("mode"))
				mode=requestJson.get("mode").getAsString();

		} catch (Exception e) {
			log.error("Exception", e);
		}


		try {

			if ((null == ekKey) || (null == requestParamJson))
				out.println("unauthorized access");
			else if (ekKey.length() != 36)
				out.println("unauthorized access");

			else if (databaseConService.getLoginFSSession(ekKey, mobile, encryptKey)) {
				out.println("unauthorized access");
			}

			else {

				// Database Changes START
			//	boolean validOtpCheck = databaseConService.validateOtpDetailsWithKey(mobile, otp, "enach-mandate",
			//			ekKey, encryptKey);
				
				
				
				boolean validOtpCheck = databaseConService.validateOtpDetailsWithKey(EncryptDecrypt.encrypt(DBENCRYPTYKEY, DBENCRYPTYKEY, mobile), otp, "enach-mandate",
									ekKey, encryptKey);

				if (validOtpCheck) {

					if(mode.equalsIgnoreCase("")) {
					response.setStatus(200);
					JSONObject val = fetchAccountDetails(mobile, ekKey);
					String str = val.toString();
					out.print(EncryptDecrypt.encrypt(encryptKey, encryptKey, "passtoGo" + str));
					}else
					{
						response.setStatus(200);
						out.print(EncryptDecrypt.encrypt(encryptKey, encryptKey, "passtoGo"));
						
					}
					out.flush();
				} else {
					out.print(EncryptDecrypt.encrypt(encryptKey, encryptKey, "passnoGo"));
					out.flush();
				}

				// Database Changes END

			}
		} catch (Exception e) {
			out.println(e.getMessage());
		}

	}

	public JSONObject fetchAccountDetails(String mobileNumber, String mKey)

	{
		JSONObject response = null;
		try {
			JSONArray jsonArray = soapAPIService.getAccountDetails(mobileNumber);
			Boolean flag = false;
			boolean cifUpdated = databaseConService.updateCifDetails(EncryptDecrypt.encrypt(DBENCRYPTYKEY, DBENCRYPTYKEY, mobileNumber), EncryptDecrypt.encrypt(DBENCRYPTYKEY, DBENCRYPTYKEY, jsonArray.toString()), mKey);
			if (!cifUpdated) {
				log.info("Unable to update database details for mobile Number : {}", mobileNumber);
			}

			String previousDob = "";
			String nextDob = "";

			JSONObject tempJsoni;
			JSONObject tempJsonj;
			JSONArray accountsFromDOB = new JSONArray();

			for (int i = 0; i < jsonArray.length(); i++) {
				tempJsoni = jsonArray.getJSONObject(i);
				previousDob = tempJsoni.getString("DOB");
				accountsFromDOB.put(tempJsoni);
				for (int j = 0; j < jsonArray.length(); j++) {
					tempJsonj = jsonArray.getJSONObject(j);
					nextDob = tempJsonj.getString("DOB");

					if (previousDob.equalsIgnoreCase(nextDob)) {
						flag = true;
					} else {
						flag = false;
						break;
					}
				}
			}

			if (Boolean.TRUE.equals(flag)) {
				response = new JSONObject();
				response.put("success", "true");
				response.put("isDobRequired", "false");
				response.put("accountsFromDOB", accountsFromDOB.toString());
			} else {
				response = new JSONObject();
				response.put("success", "false");
				response.put("isDobRequired", "true");
			}

			return response;
		} catch (Exception e) {
			log.error("Exception", e);
			return response;
		}

	}
}