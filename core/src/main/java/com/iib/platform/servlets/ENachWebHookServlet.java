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
import com.iib.platform.services.createFluxMandate;
import com.iib.platform.core.smsemail.EmailSmsApiService;

@Component(service = { Servlet.class }, name = "eNachWebHookServlet", enabled = true, property = {
		Constants.SERVICE_DESCRIPTION + "=" + "eNachWebHookServlet ",
		"sling.servlet.methods=" + HttpConstants.METHOD_POST, "sling.servlet.paths=" + "/bin/eNachWebHookServlet" })

public class ENachWebHookServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1078831906794611162L;
	private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	private static String mandateid = StringUtils.EMPTY;
	private static final String MESSAGE = "message";

	@Reference
	transient DatabaseConnectionService databaseConService;

	@Reference
	transient createFluxMandate createfluxmandate;

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
				LOGGER.error("When we calling EnachWebHookServelet::: {}",  stringBuilder.toString());

				
				JsonParser parser = new JsonParser();
				JsonElement jsonElement = null;
				jsonElement = parser.parse(stringBuilder.toString());
				JsonObject jsonObj = jsonElement.getAsJsonObject();			
				this.getJsonProperties(jsonObj);
				JSONObject success = new JSONObject();
				boolean status = false;
				String[] getNPCIProperties = getJsonProperties(jsonObj);
				String appid = databaseConService.checkforapp(mandateid);
				
				LOGGER.error("Calling For which Application EnachWebHookServelet::: {}", appid);
				
				if(appid.equalsIgnoreCase("ENACH")) {
				
				status = databaseConService.updateNPCIAPIDetails(getNPCIProperties);

				if (status)

				{
					LOGGER.error("When we calling EnachWebHookServelet::: {} for MandateID {} ::::: {}", getNPCIProperties, mandateid , stringBuilder);

					boolean fluxstatus = createfluxmandate.createMandateforFlux(mandateid);

					response.setStatus(HttpServletResponse.SC_OK);
					success.put("fluxmessage", fluxstatus);
					success.put(MESSAGE, "Success Update");
				} else {
					response.setStatus(HttpServletResponse.SC_OK);
					success.put(MESSAGE, "Failed Update");
				}
				response.getWriter().write(success.toString());

				triggerEmailSMS(mandateid, getNPCIProperties[12], getNPCIProperties[9], appid);
			} else if(appid.equalsIgnoreCase("CFDENACH")) {
				
				
				LOGGER.error("When we calling CFDEnachWebHookServelet::: {} for MandateID {} ::::: {}", getNPCIProperties, mandateid , stringBuilder);

				status = databaseConService.updateCFDNPCIAPIDetails(getNPCIProperties);

				response.setStatus(HttpServletResponse.SC_OK);
				success.put(MESSAGE, "Not Required");
				/*if (status)

				{
					response.setStatus(HttpServletResponse.SC_OK);
					success.put(MESSAGE, "Not Required");
				} else {
					response.setStatus(HttpServletResponse.SC_OK);
					success.put(MESSAGE, "Not Required");
				}*/
				response.getWriter().write(success.toString());

				triggerEmailSMS(mandateid, getNPCIProperties[12], getNPCIProperties[9], appid);
				
			}else
			{
				LOGGER.error("Exception in eNachWebHookServlet  Call Digio ID not Found {} :2: ", mandateid);
			}

			} catch (Exception e) {

				response.setStatus(HttpServletResponse.SC_OK);

				LOGGER.error("Exception in eNachWebHookServlet :2: ", e);
			}

		} else {
			JSONObject error = new JSONObject();
			try {
				error.put(MESSAGE, "CheckSum Match Failed");
				response.getWriter().write(error.toString());
			} catch (JSONException | IOException e) {

				LOGGER.error("Exception in eNachWebHookServlet :3: ", e);
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
			LOGGER.error("HASH {}", hash);
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
		
		
		/* Sample JSON Recived from DIGIO {
			   "entities":[
			      "api_mandate"
			   ],
			   "payload":{
			      "api_mandate":{
			         "txn_timestamp":1535021979000,
			         "txn_reject_reason":null,
			         "umrn":"ONMG7011108200004010",
			         "auth_sub_mode":"net_banking",
			         "txn_reject_code":"000",
			         "current_status":"success",
			         "message_id":"MMI200729144247102UONHM5CNC9F2N1",
			         "id":"ENA200729144235842FDBJYQSQSCS7AP",
			         "npci_txn_id":"8cff752e65c549f2b4e0906cd5e35d3a",
			         "others":{
			            "scheme_ref_number":null,
			            "customer_ref_number":"TMI08126H"
			         },
			         "tags":null
			      }
			   },
			   "created_at":1596013973000,
			   "id":"WHN200729144253697D4H3SA6Q8IHU4D",
			   "event":"apimndt.authsuccess"
			}*/

		mandateid = jsonObj.getAsJsonObject(pay).getAsJsonObject(api).get("id").toString();
		mandateid = complieString(mandateid);
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

		String auth_sub_mode = jsonObj.getAsJsonObject(pay).getAsJsonObject(api).get("auth_sub_mode").toString();
		auth_sub_mode =complieString(auth_sub_mode);
		
		
		String umrn = jsonObj.getAsJsonObject(pay).getAsJsonObject(api).get("umrn").toString();
		umrn = complieString(umrn);

		String txnrejectreason = jsonObj.getAsJsonObject(pay).getAsJsonObject(api).get("txn_reject_reason")== null?"NA":jsonObj.getAsJsonObject(pay).getAsJsonObject(api).get("txn_reject_reason").toString();
		txnrejectreason = complieString(txnrejectreason);

		String id = jsonObj.get("id").toString();
		id = complieString(id);

		String createdat = jsonObj.get("created_at").toString();
		createdat = complieString(createdat);
		
		String event = jsonObj.get("event").toString();
		event = complieString(event);
		return new String[] { apimandid, msgid, customerrefnumber, schemerefnumber, npcitxnid, currentstatus,
				txntimestamp, txnrejectcode, umrn, txnrejectreason, id, createdat, event,auth_sub_mode };
		
	}

	private String complieString(String value) {

		value = StringUtils.remove(value, "\"");
		return value;
	}

	private void triggerEmailSMS(String mandateid, String vstatus, String npciTxtReason, String appid) {
		List<String> customerDetails = null;
		try {
			customerDetails = databaseConService.getClientDetailsSMSEMAIL(mandateid,appid);
		} catch (Exception e) {
			LOGGER.error("triggerEmailSMS Occoured 1{} {} ", e, npciTxtReason);
		}
		if (customerDetails != null) {
			// trigger Email Point
			try {
				if ((vstatus.equalsIgnoreCase("apimndt.authsuccess"))) {
					LOGGER.error("Email trigger for Success");
					String response = emailSmsApiService.emailServiceCallWithExtraParam("EA0022",
							customerDetails.get(1), "Your Emandate Status from IndusInd Bank", customerDetails.get(2),
							customerDetails.get(3), customerDetails.get(4), customerDetails.get(5), mandateid,
							customerDetails.get(6), 1);
					LOGGER.error("Email trigger for Success {}", response);

				} else if ((vstatus.equalsIgnoreCase("apimndt.authfail"))) {
					LOGGER.error("Email trigger for Failure");
					String response = emailSmsApiService.emailServiceCallWithExtraParam("EA0028",
							customerDetails.get(1), "Your Emandate Status from IndusInd Bank", customerDetails.get(2),
							customerDetails.get(3), customerDetails.get(4), customerDetails.get(5), mandateid,
							customerDetails.get(6), 2);
					LOGGER.error("Email trigger for faliure {}", response);
				}

			} catch (Exception e) {
				LOGGER.error("triggerEmailSMS Occoured 2 ", e);
			}

			// trigger SMS Point
			try {
				if ((vstatus.equalsIgnoreCase("apimndt.authsuccess"))) {
					LOGGER.error("SMS trigger for Success");
					String response = emailSmsApiService.smsServiceCall(mandateid, "EA0022", customerDetails.get(0), "",
							1);
					LOGGER.error("SMS trigger for Success{}", response);

				} else if ((vstatus.equalsIgnoreCase("apimndt.authfail"))) {
					LOGGER.error("SMS trigger for faliure");
					String response = emailSmsApiService.smsServiceCall(mandateid, "EA0028", customerDetails.get(0), "",
							2);
					LOGGER.error("SMS trigger for faliure {}", response);
				}

			} catch (Exception e) {
				LOGGER.error("triggerEmailSMS Occoured 3 ", e);
			}
		}
	}

}
