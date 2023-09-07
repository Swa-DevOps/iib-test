package com.iib.platform.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.json.JSONObject;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.iib.platform.services.EmandateBankDataService;
import com.iib.platform.services.EncryptDecryptService;

/**
 * IFSC Code Verify Servlet
 *
 * @author
 *
 */
@Component(service = { Servlet.class }, name = "IFSC Code Verify Servlet", enabled = true, property = {
		Constants.SERVICE_DESCRIPTION + "=" + "IFSC Code Verify Servlet",
		"sling.servlet.methods=" + HttpConstants.METHOD_POST, "sling.servlet.paths=" + "/bin/ifscVerify" })
public class IfscCodeVerifyServlet extends SlingAllMethodsServlet {

	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(IfscCodeVerifyServlet.class);

	transient JsonParser jsonParser = new JsonParser();

	@Reference
	transient EncryptDecryptService encryptDecryptService;

	@Reference
	transient EmandateBankDataService emandateBankDataService;

	@Activate
	public void activate() {
		log.info("Activated IfscCodeVerifyServlet");
	}

	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		String repaymentBankName = "";
		String ifscCode = "";
		String requestedBankCode = "";
		String appName="";
		String mKey = request.getHeader("X-AUTH-SESSION");
		String jsonObject = request.getHeader("X-AUTH-TOKEN");
		String encryptKey = jsonObject.substring(jsonObject.indexOf(':') + 1);
		jsonObject = jsonObject.substring(0, (jsonObject.indexOf(':')));
		String requestParamJson = "";
		requestParamJson = encryptDecryptService.decrypt(encryptKey, encryptKey, jsonObject);
		JsonObject requestJson = null;

		try {
			requestJson = (JsonObject) jsonParser.parse(requestParamJson);
			repaymentBankName = requestJson.get("repaymentBankName").getAsString();
			ifscCode = requestJson.get("ifsc").getAsString();
			if(requestJson.has("appname"))
				appName=requestJson.get("appname").getAsString();
			requestedBankCode = ifscCode.substring(0, Math.min(ifscCode.length(), 4));
		} catch (Exception e) {
			log.error("Exception ", e);
		}
		log.info("REquest WE GET inn Loan API Servlet {} {} {} {}", jsonObject, encryptKey, requestParamJson, mKey);
		JSONObject jsonObj;
		if(appName.equalsIgnoreCase("CFDENACH"))
			jsonObj = emandateBankDataService.getCFDIfscStatus(requestedBankCode, repaymentBankName);
		else
			jsonObj = emandateBankDataService.getIfscStatus(requestedBankCode, repaymentBankName);
		out.println(encryptDecryptService.encrypt(encryptKey, encryptKey, jsonObj.toString()));

	}
}
