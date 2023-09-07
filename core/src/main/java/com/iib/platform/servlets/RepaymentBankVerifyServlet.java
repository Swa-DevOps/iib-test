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
 * Repayment Bank Verify Servlet
 *
 * @author
 *
 */
@Component(service = { Servlet.class }, name = "Repayment Bank Verify Servlet", enabled = true, property = {
		Constants.SERVICE_DESCRIPTION + "=" + "Repayment Bank Verify Servlet",
		"sling.servlet.methods=" + HttpConstants.METHOD_POST, "sling.servlet.paths=" + "/bin/repaymentBankVerify" })
public class RepaymentBankVerifyServlet extends SlingAllMethodsServlet {

	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(RepaymentBankVerifyServlet.class);

	transient JsonParser jsonParser = new JsonParser();

	@Reference
	transient EncryptDecryptService encryptDecryptService;

	@Reference
	transient EmandateBankDataService emandateBankDataService;

	@Activate
	public void activate() {
		log.info("Activated RepaymentBankVerifyServlet");
	}

	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		String repaymentBankCode = "";

		String key = request.getHeader("X-AUTH-SESSION");
		String jsonObject = request.getHeader("X-AUTH-TOKEN");
		String encryptKey = jsonObject.substring(jsonObject.indexOf(':') + 1);
		jsonObject = jsonObject.substring(0, (jsonObject.indexOf(':')));
		String requestParamJson = "";
		requestParamJson = encryptDecryptService.decrypt(encryptKey, encryptKey, jsonObject);
		JsonObject requestJson = null;

		try {
			requestJson = (JsonObject) jsonParser.parse(requestParamJson);
			repaymentBankCode = requestJson.get("repaymentBankCode").getAsString();

		} catch (Exception e) {
			log.error("Exception", e);
		}

		if ((null == jsonObject) || (null == key))
			out.println("1. unauthorized access");
		else if (encryptKey.length() != 16)
			out.println("2. unauthorized access");
		else {

			JSONObject jsonObj = null;
			jsonObj = emandateBankDataService.getBillDeskRepaymentBankName(repaymentBankCode);
			out.println(encryptDecryptService.encrypt(encryptKey, encryptKey, jsonObj.toString()));
		}

	}

}
