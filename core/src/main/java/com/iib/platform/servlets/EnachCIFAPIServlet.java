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
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iib.platform.services.SoapAPIService;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.iib.platform.common.utils.EncryptDecrypt;
import com.iib.platform.services.DatabaseConnectionService;

@Component(service = { Servlet.class }, name = "AccountDetails", enabled = true, property = {
		Constants.SERVICE_DESCRIPTION + "=" + "Account Details Servlet",
		"sling.servlet.methods=" + HttpConstants.METHOD_POST, "sling.servlet.paths=" + "/bin/accountDetails" })
public class EnachCIFAPIServlet extends SlingAllMethodsServlet {

	@Reference
	transient SoapAPIService soapApiService;

	@Reference
	transient DatabaseConnectionService databaseConService;

	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(EnachCIFAPIServlet.class);
	transient JsonParser jsonParser = new JsonParser();

	@Activate
	public void activate() {
		log.info("E-NACH CIF API Servlet has been Activated!");
	}

	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		String key = request.getHeader("X-AUTH-SESSION");
		String jsonObject = request.getHeader("X-AUTH-TOKEN");
		String encryptKey = jsonObject.substring(jsonObject.indexOf(':') + 1);
		jsonObject = jsonObject.substring(0, (jsonObject.indexOf(':')));
		String requestParamJson = "";
		requestParamJson = EncryptDecrypt.decrypt(encryptKey, encryptKey, jsonObject);
		JsonObject requestJson = null;

		String mobileNumber = "";

		try {
			requestJson = (JsonObject) jsonParser.parse(requestParamJson);
			mobileNumber = requestJson.get("mobile").getAsString();

		} catch (Exception e) {
			log.error("Exception in EnachCIFAPIServlet ", e);
		}

		try {

			if ((null == key) || (key.length() != 36))
				out.println("unauthorized access");
			JSONObject resObj;
			resObj = fetchAccountDetails(mobileNumber, key);

			out.print(EncryptDecrypt.encrypt(encryptKey, encryptKey, resObj.toString()));
			response.flushBuffer();

			/*
			 * boolean flag = false;
			 * 
			 * flag = resObj.getBoolean("success");
			 * 
			 * 
			 * 
			 * if(flag) { out.print(EncryptDecrypt.encrypt(encryptKey, encryptKey,
			 * resObj.toString())); response.flushBuffer(); } else {
			 * out.print(EncryptDecrypt.encrypt(encryptKey, encryptKey, resObj.toString()));
			 * response.flushBuffer(); }
			 */
		} catch (Exception e) {
			log.error("Exception in e-NACH CIF API :: ", e);
		}

	}

	public JSONObject fetchAccountDetails(String mobileNumber, String key)

	{
		JSONObject response = null;
		try {
			JSONArray jsonArray = soapApiService.getAccountDetails(mobileNumber);
			boolean flag = false;
			// Database Changes start
			boolean cifUpdated = databaseConService.updateCifDetails(mobileNumber, jsonArray.toString(), key);
			if (!cifUpdated) {
				log.info("Unable to update database details for mobile Number : {}", mobileNumber);
			}

			String previousDob = "";
			String nextDob = "";

			JSONObject tempJsoni = null;
			JSONObject tempJsonj = null;

			for (int i = 0; i < jsonArray.length(); i++) {
				tempJsoni = jsonArray.getJSONObject(i);
				previousDob = tempJsoni.getString("DOB");

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

			if (flag) {
				response = new JSONObject();
				response.put("success", "true");
				response.put("isDobRequired", "false");
			} else {
				response = new JSONObject();
				response.put("success", "false");
				response.put("isDobRequired", "true");
			}

			return response;
		} catch (Exception e) {
			log.error("Exception in e-NACH CIF API  API :: ", e);
			return response;
		}

	}
}
