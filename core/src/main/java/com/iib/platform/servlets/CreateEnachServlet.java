package com.iib.platform.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.iib.platform.common.utils.EncryptDecrypt;
import com.iib.platform.services.SoapAPIService;

/**
 * Create E-nach Servlet
 *
 * @author ADS (Niket.goel)
 *
 */
@Component(service = { Servlet.class }, name = "Create E-NACH Servlet", enabled = true, property = {
		Constants.SERVICE_DESCRIPTION + "=" + "Create e-NACH Servlet",
		"sling.servlet.methods=" + HttpConstants.METHOD_POST, "sling.servlet.paths=" + "/bin/createEnach" })
public class CreateEnachServlet extends SlingAllMethodsServlet {

	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(CreateEnachServlet.class);
	transient JsonParser jsonParser = new JsonParser();

	@Reference
	transient SoapAPIService soapApiService;

	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {

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
			log.error("Exception in e-NACH Create Servlet", e);
		}
		log.debug("REquest WE GET {}, {}, {}", jsonObject, encryptKey, requestParamJson);

		PrintWriter out = response.getWriter();

		try {

			JSONArray jsonArray = soapApiService.getAccountDetails(mobileNumber);
			JSONArray accountsFromDOB = new JSONArray();

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject tempJson = jsonArray.getJSONObject(i);
				accountsFromDOB.put(tempJson);
			}

			out.println(EncryptDecrypt.encrypt(encryptKey, encryptKey, accountsFromDOB.toString()));

		} catch (Exception e) {
			log.error("Exception in e-NACH Create Servlet", e);
		}
	}

}
