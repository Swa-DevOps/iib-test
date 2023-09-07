package com.iib.platform.servlets;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang.StringUtils;

import org.apache.sling.api.servlets.HttpConstants;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import com.iib.platform.services.DatabaseConnectionService;
import com.iib.platform.core.smsemail.EmailSmsApiService;

@Component(service = { Servlet.class }, name = "CFD eNachWebHookServlet", enabled = true, property = {
		Constants.SERVICE_DESCRIPTION + "=" + "CFD eNachWebHookServlet ",
		"sling.servlet.methods=" + HttpConstants.METHOD_POST, "sling.servlet.paths=" + "/bin/cfd/eCFDNachWebHookServlet" })

public class CFDENachWebHookServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1078831906794611162L;
	private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	private static String mandateid = StringUtils.EMPTY;
	private static final String MESSAGE = "message";

	@Reference
	 transient  DatabaseConnectionService databaseConService;


	@Reference
	 transient EmailSmsApiService emailSmsApiService;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpServletRequest req = request;
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		StringBuilder stringBuilder = new StringBuilder();
		String requestJson;
		String checkSumHeader = request.getHeader("X-Digio-Checksum");
		try {
			while ((requestJson = req.getReader().readLine()) != null) {
				stringBuilder.append(requestJson);
			}
		} catch (IOException e) {
			// TO Do
		}
		if (matchCheckSumHeader(stringBuilder.toString(), checkSumHeader)) {

			try {
				JsonParser parser = new JsonParser();
				JsonElement jsonElement = null;
				jsonElement = parser.parse(stringBuilder.toString());
				JsonObject jsonObj = jsonElement.getAsJsonObject();
				this.getJsonProperties(jsonObj);
				JSONObject success = new JSONObject();
				boolean status = false;
				String[] getNPCIProperties = getJsonProperties(jsonObj);
				status = databaseConService.updateCFDNPCIAPIDetails(getNPCIProperties);

				if (status)

				{
					response.setStatus(HttpServletResponse.SC_OK);
					success.put(MESSAGE, "Success Update");
				} else {
					response.setStatus(HttpServletResponse.SC_OK);
					success.put(MESSAGE, "Failed Update");
				}
				response.getWriter().write(success.toString());

				triggerEmailSMS(mandateid, getNPCIProperties[12], getNPCIProperties[9]);

			} catch (Exception e) {

				response.setStatus(HttpServletResponse.SC_OK);

				LOGGER.error("Exception in CFD eNachWebHookServlet :2: ", e);
			}

		} else {
			JSONObject error = new JSONObject();
			try {
				error.put(MESSAGE, "CheckSum Match Failed");
				response.getWriter().write(error.toString());
			} catch (JSONException | IOException e) {

				LOGGER.error("Exception in CFD eNachWebHookServlet :3: ", e);
			}

		}

	}

	private boolean matchCheckSumHeader(String sb, String checkSumHeader) {

		boolean checksumMatched = false;
		try {

			String key = "!nd$us!nd";
			String message = sb;
			Mac hasher = Mac.getInstance("HmacSHA256");
			hasher.init(new SecretKeySpec(key.getBytes(), "HmacSHA256"));
			byte[] hash = hasher.doFinal(message.getBytes());
			LOGGER.info("HASH {}", hash);
			DatatypeConverter.printHexBinary(hash);
			// to base64
			String valueHex = DatatypeConverter.printHexBinary(hash);
			if (checkSumHeader.equalsIgnoreCase(valueHex)) {
				checksumMatched = true;
			} else {
				checksumMatched = false;
			}

		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("NoSuchAlgorithmException Occoured ", e);
		} catch (InvalidKeyException e) {
			LOGGER.error("InvalidKeyException Occoured ", e);
		}

		return checksumMatched;

	}

	public String[] getJsonProperties(JsonObject jsonObj) {

		String pay = "payload";
		String api = "api_mandate";

		String srmandateid = jsonObj.getAsJsonObject(pay).getAsJsonObject(api).get("id").toString();
		mandateid = complieString(srmandateid);
		String apimandid = jsonObj.getAsJsonObject(pay).getAsJsonObject(api).get("id").toString();
		apimandid = complieString(apimandid);
		String msgid = jsonObj.getAsJsonObject(pay).getAsJsonObject(api).get("message_id").toString();
		msgid = complieString(msgid);
		String customerrefnumber = jsonObj.getAsJsonObject(pay).getAsJsonObject(api).getAsJsonObject("others")
				.get("customer_ref_number").toString();
		customerrefnumber = complieString(customerrefnumber);
		String schemerefnumber = jsonObj.getAsJsonObject(pay).getAsJsonObject(api).getAsJsonObject("others")
				.get("scheme_ref_number").toString();
		schemerefnumber = complieString(schemerefnumber);
		String npcitxnid = jsonObj.getAsJsonObject(pay).getAsJsonObject(api).get("npci_txn_id").toString();
		npcitxnid = complieString(npcitxnid);
		String currentstatus = jsonObj.getAsJsonObject(pay).getAsJsonObject(api).get("current_status").toString();
		currentstatus = complieString(currentstatus);
		String txntimestamp = jsonObj.getAsJsonObject(pay).getAsJsonObject(api).get("txn_timestamp").toString();
		txntimestamp = complieString(txntimestamp);
		String txnrejectcode = jsonObj.getAsJsonObject(pay).getAsJsonObject(api).get("txn_reject_code").toString();
		txnrejectcode = complieString(txnrejectcode);

		String umrn = jsonObj.getAsJsonObject(pay).getAsJsonObject(api).get("umrn").toString();
		umrn = complieString(umrn);

		String txnrejectreason = jsonObj.getAsJsonObject(pay).getAsJsonObject(api).get("txn_reject_code").toString();
		txnrejectreason = complieString(txnrejectreason);

		String id = jsonObj.get("id").toString();
		id = complieString(id);

		String createdat = jsonObj.get("created_at").toString();
		createdat = complieString(createdat);

		String event = jsonObj.get("event").toString();
		event = complieString(event);
		return new String[] { apimandid, msgid, customerrefnumber, schemerefnumber, npcitxnid, currentstatus,
				txntimestamp, txnrejectcode, umrn, txnrejectreason, id, createdat, event };

	}

	private String complieString(String value) {

		value = StringUtils.remove(value, "\"");
		return value;
	}

	private void triggerEmailSMS(String mandateid, String vstatus, String npciTxtReason) {
		List<String> customerDetails = null;
		try {
			customerDetails = databaseConService.getCFDClientDetailsSMSEMAIL(mandateid);
		} catch (Exception e) {
			LOGGER.info("triggerEmailSMS Occoured 1{} {} ", e, npciTxtReason);
		}
		if (customerDetails != null) {
			// trigger Email Point
			try {
				if ((vstatus.equalsIgnoreCase("apimndt.authsuccess"))) {
					LOGGER.info("Email trigger for Success");
					String response = emailSmsApiService.emailServiceCallWithExtraParam("EA0022",
							customerDetails.get(1), "Your Emandate Status from IndusInd Bank", customerDetails.get(2),
							customerDetails.get(3), customerDetails.get(4), customerDetails.get(5), mandateid,
							customerDetails.get(6), 1);
					LOGGER.info("Email trigger for Success {}", response);

				} else if ((vstatus.equalsIgnoreCase("apimndt.authfail"))) {
					LOGGER.info("Email trigger for Failure");
					String response = emailSmsApiService.emailServiceCallWithExtraParam("EA0028",
							customerDetails.get(1), "Your Emandate Status from IndusInd Bank", customerDetails.get(2),
							customerDetails.get(3), customerDetails.get(4), customerDetails.get(5), mandateid,
							customerDetails.get(6), 2);
					LOGGER.info("Email trigger for faliure {}", response);
				}

			} catch (Exception e) {
				LOGGER.error("triggerEmailSMS Occoured 2 ", e);
			}

			// trigger SMS Point
			try {
				if ((vstatus.equalsIgnoreCase("apimndt.authsuccess"))) {
					LOGGER.info("SMS trigger for Success");
					String response = emailSmsApiService.smsServiceCall(mandateid, "EA0022", customerDetails.get(0), "",
							1);
					LOGGER.info("SMS trigger for Success{}", response);

				} else if ((vstatus.equalsIgnoreCase("apimndt.authfail"))) {
					LOGGER.info("SMS trigger for faliure");
					String response = emailSmsApiService.smsServiceCall(mandateid, "EA0028", customerDetails.get(0), "",
							2);
					LOGGER.info("SMS trigger for faliure {}", response);
				}

			} catch (Exception e) {
				LOGGER.error("triggerEmailSMS Occoured 3 ", e);
			}
		}
	}

}
