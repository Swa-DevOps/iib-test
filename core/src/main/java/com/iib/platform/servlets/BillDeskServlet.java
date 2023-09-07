package com.iib.platform.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
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
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.math.BigDecimal;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.iib.platform.services.DatabaseConnectionService;
import com.iib.platform.services.config.BillDeskServletConfig;
import com.iib.platform.services.EncryptDecryptService;

/**
 * Bill Desk Servlet
 *
 * @author
 *
 */
@Component(service = {
		Servlet.class }, enabled = true, configurationPolicy = ConfigurationPolicy.REQUIRE, name = "Bill Desk Servlet", property = {
				Constants.SERVICE_DESCRIPTION + "=" + "BillDesk Servlet",
				"sling.servlet.methods=" + HttpConstants.METHOD_POST, "sling.servlet.paths=" + "/bin/billDeskService" })
@Designate(ocd = BillDeskServletConfig.class)
public class BillDeskServlet extends SlingAllMethodsServlet {

	private static final Logger log = LoggerFactory.getLogger(BillDeskServlet.class);
	org.slf4j.Marker marker;
	private static final long serialVersionUID = 1L;

	private String redirectUrl;
	private String checksumKey;
	private String billDeskurl;
	private String modeofRegistration;

	transient JsonParser jsonParser = new JsonParser();

	@Reference
	transient EncryptDecryptService encryptDecryptService;

	@Reference
	transient DatabaseConnectionService databaseConService;

	@Activate
	protected void activate(BillDeskServletConfig config) {
		log.info("BillDesk Servlet has been activated");
		this.redirectUrl = config.getRedirectionUrl();
		this.checksumKey = config.checkSumKey();
		this.billDeskurl = config.billDeskUrl();
		this.modeofRegistration = config.modeofRegistration();
	}

	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		String freq = "";
		String ifsc = "";
		String bankAccountType = "";
		String tidMob = "";

		String kKey = request.getHeader("X-AUTH-SESSION");
		String jsonObject = request.getHeader("X-AUTH-TOKEN");
		String encryptKey = jsonObject.substring(jsonObject.indexOf(':') + 1);
		jsonObject = jsonObject.substring(0, (jsonObject.indexOf(':')));
		String requestParamJson = "";
		requestParamJson = encryptDecryptService.decrypt(encryptKey, encryptKey, jsonObject);
		JsonObject requestJson = null;

		try {
			requestJson = (JsonObject) jsonParser.parse(requestParamJson);
			freq = requestJson.get("frequency").getAsString();
			ifsc = requestJson.get("ifsc").getAsString();
			bankAccountType = requestJson.get("bankAccountType").getAsString();
			tidMob = requestJson.get("tid").getAsString();
			tidMob = tidMob.substring(tidMob.length() - 10);

		} catch (Exception e) {
			log.error("Eception in doPost@56", e);
		}
		log.debug("REquest WE GET inn Billdesk API Servlet {} {} {} {}", jsonObject, encryptKey, requestParamJson,
				kKey);

		String billDeskCheck = databaseConService.getSetEnachBillDeskSession(tidMob, kKey, "");

		if ((null == jsonObject) || (null == kKey))
			out.println("1. unauthorized access");
		else if (encryptKey.length() != 16)
			out.println("2. unauthorized access");
		else if (billDeskCheck.equalsIgnoreCase("Yes")) {
			out.println("3. unauthorized access");
		} else {

			try {
				String cookie = databaseConService.getMandateDetails(tidMob, "enach-pl");
				JSONObject jsonObj = new JSONObject(cookie);

				/* SIDetails token */
				String accountnumber = jsonObj.getString("Other_bank_account_number");
				String accounttype = bankAccountType;
				String bdSIamount = jsonObj.getString("Instructed_amt");
				double f = Double.parseDouble(bdSIamount);
				bdSIamount = String.format("%.2f", BigDecimal.valueOf(f));
				String bdSIamounttype = "Max";
				int startdate = jsonObj.getInt("EMI_start_date");
				int enddate = jsonObj.getInt("EMI_END_date");
				String frequency = freq;
				String bdRef1 = "NA";
				String bdRef2 = jsonObj.getString("loanNumber");
				String customername = jsonObj.getString("Name");
				String mandaterefno = "NA";
				String bdIfsc = ifsc;
				String payer2name = "NA";
				String modeofRegesirt = modeofRegistration;

				/* BillDesk Request Object */

				String bdMerchantID = "INDUSBANKB";
				Long bdCustomerID = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;
				String bdSIDetails = null;
				String bdTxnAmount = "1.00";
				String bdBankID = "INDUSAPIEMN";// OLD VALUES is INDUSBANKACH
				String bdFiller1 = "NA";
				String bdFiller2 = "NA";
				String bdFiller3 = "NA";
				String bdCurrencyType = "INR";
				String bdItemCode = "DIRECT";
				String bdTypeField1 = "R";
				String bdUserID = "indusbankb";
				String bdFiller4 = "NA";
				String bdFiller5 = "NA";
				String bdTypeField2 = "F";
				String bdAdditionalInfo1 = jsonObj.getString("loanNumber");
				String bdAdditionalInfo2 = jsonObj.getString("Instructed_amt"); // To be display on RESPONSE Page (Sorry
																				// or Thank-you)
				String bdAdditionalInfo3 = jsonObj.getString("MobileNo");
				String bdAdditionalInfo4 = "NA";
				String bdAdditionalInfo5 = "NA";
				String bdAdditionalInfo6 = "NA";
				String bdAdditionalInfo7 = jsonObj.getString("Bank_code");

				/*
				 * Request GET URL : https://uat.billdesk.com/billpay/EMandateController?action=
				 * EmandateIndusBankReg&msg=
				 */

				String requestSIDetails = accountnumber + ":" + accounttype + ":" + bdSIamount + ":" + bdSIamounttype
						+ ":" + startdate + ":" + enddate + ":" + frequency + ":" + bdRef1 + ":" + bdRef2 + ":"
						+ customername + ":" + mandaterefno + ":" + bdIfsc.toUpperCase() + ":" + bdFiller1 + ":"
						+ bdFiller2 + ":" + payer2name + ":" + modeofRegesirt;
				bdSIDetails = requestSIDetails;
				String requestDetails = bdMerchantID + "|" + bdCustomerID + "|" + bdSIDetails + "|" + bdTxnAmount + "|"
						+ bdBankID + "|" + bdFiller2 + "|" + bdFiller3 + "|" + bdCurrencyType + "|" + bdItemCode + "|"
						+ bdTypeField1 + "|" + bdUserID + "|" + bdFiller4 + "|" + bdFiller5 + "|" + bdTypeField2 + "|"
						+ bdAdditionalInfo1 + "|" + bdAdditionalInfo2 + "|" + bdAdditionalInfo3 + "|"
						+ bdAdditionalInfo4 + "|" + bdAdditionalInfo5 + "|" + bdAdditionalInfo6 + "|"
						+ bdAdditionalInfo7.toUpperCase() + "|" + redirectUrl;
				String checksum = hmacSHA256(requestDetails, checksumKey);
				String requestString = requestDetails + "|" + checksum;

				log.debug("{} :: Request String", requestString);

				String url = billDeskurl + requestString;
				updateDataBase(tidMob, kKey);
				out.println(encryptDecryptService.encrypt(encryptKey, encryptKey, url));

			} catch (Exception e) {
				log.error("Eception in doPost@56", e);
			}
		}
	}

	private void updateDataBase(String tidMob, String kKey) {
		try {
			String result = databaseConService.getSetEnachBillDeskSession(tidMob, kKey, "YES");
			log.debug("success DB Update for Bill Desk REsult {}", result);
		} catch (Exception e) {
			log.info("Error in Stroing the DB Update for Bill Desk REsult");
		}

	}

	private String hmacSHA256(String message, String secret) {
		try {
			Mac sha256HMAC = Mac.getInstance("HmacSHA256");
			SecretKeySpec secretkey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
			sha256HMAC.init(secretkey);
			byte[] raw = sha256HMAC.doFinal(message.getBytes());
			StringBuilder lssb = new StringBuilder();
			for (int i = 0; i < raw.length; i++)
				lssb.append(char2hex(raw[i]));
			return lssb.toString();
		} catch (Exception e) {
			log.error("Eception in SHAD@56", e);
			return null;
		}

	}

	private static String char2hex(byte x) {
		char[] arr = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

		char[] c = { arr[(x & 0xF0) >> 4], arr[x & 0x0F] };
		return (new String(c));
	}
}