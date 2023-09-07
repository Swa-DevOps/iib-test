package com.iib.platform.servlets;

import java.io.IOException;
import java.io.PrintWriter;
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
import com.iib.platform.services.DatabaseConnectionService;
import com.iib.platform.services.EncryptDecryptService;

/**
 * Verify OTP Servlet
 *
 * @author Niket Goel
 *
 */
@Component(immediate = true, service = { Servlet.class }, property = {
		Constants.SERVICE_DESCRIPTION + "=" + "Verify OTP Servlet",
		"sling.servlet.methods=" + HttpConstants.METHOD_POST, "sling.servlet.paths=" + "/bin/verifyotp" })
public class VerifyOTP extends SlingAllMethodsServlet {

	private static final long serialVersionUID = 1L;

	/** Static Logger */
	private static Logger log = LoggerFactory.getLogger(VerifyOTP.class);

	transient JsonParser jsonParser = new JsonParser();

	@Reference
	transient EncryptDecryptService encryptDecryptService;

	@Reference
	transient DatabaseConnectionService databaseConService;

	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		String mobile = "";
		String otp = "";

		String key = request.getHeader("X-AUTH-SESSION");
		String jsonObject = request.getHeader("X-AUTH-TOKEN");
		String encryptKey = jsonObject.substring(jsonObject.indexOf(':') + 1);
		jsonObject = jsonObject.substring(0, (jsonObject.indexOf(':')));
		String requestParamJson = "";
		requestParamJson = encryptDecryptService.decrypt(encryptKey, encryptKey, jsonObject);
		JsonObject requestJson = null;

		try {
			requestJson = (JsonObject) jsonParser.parse(requestParamJson);
			mobile = requestJson.get("mobile").getAsString();
			mobile = mobile.substring(mobile.length() - 10);
			otp = requestJson.get("otp").getAsString();

		} catch (Exception e) {
			log.error("Exception in Verify OTP", e);
		}

		boolean validate = false;

		validate = databaseConService.getEnachPLOTPSession(key, mobile, encryptKey);

		if ((null == key) || (null == requestParamJson))
			out.println("1. unauthorized access");
		else if (encryptKey.length() != 16)
			out.println("2. unauthorized access");
		else if (validate) {
			out.println("3. unauthorized access");
		}

		else {

			try {

				// Database Changes START

				boolean validOtpCheck = databaseConService.validateOtpDetails(mobile, otp, "enach-pl");

				if (validOtpCheck) {
					response.setStatus(200);
					out.print("Verified" + encryptDecryptService.encrypt(encryptKey, encryptKey,
							databaseConService.getMandateDetails(mobile, "enach-pl")));
					out.flush();
				} else {
					out.print(encryptDecryptService.encrypt(encryptKey, encryptKey, "Invalid OTP"));
					out.flush();
				}

			} catch (Exception e) {
				out.println(e.getMessage());
			}

		}

	}
}
