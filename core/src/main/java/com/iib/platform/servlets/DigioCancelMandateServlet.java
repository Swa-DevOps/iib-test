package com.iib.platform.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
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
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.iib.platform.common.utils.EncryptDecrypt;
import com.iib.platform.core.smsemail.EmailSmsApiService;
import com.iib.platform.services.HttpAPIService;
import com.iib.platform.services.DatabaseConnectionService;
import com.iib.platform.services.config.DigioCancelMandateServletConfig;

/**
 * DigioCancelMandate Servlet
 *
 * @author ADS (Niket Goel)
 *
 */
@Component(service = { Servlet.class }, name = "DigioCancelMandateServlet", enabled = true, property = {
		Constants.SERVICE_DESCRIPTION + "=" + "DigioCancelMandate Servlet",
		"sling.servlet.methods=" + HttpConstants.METHOD_POST, "sling.servlet.paths=" + "/bin/digioCancelMandate" })
@Designate(ocd = DigioCancelMandateServletConfig.class)
public class DigioCancelMandateServlet extends SlingAllMethodsServlet {

	private static final long serialVersionUID = 1L;

	/** Static Logger */
	private static Logger log = LoggerFactory.getLogger(DigioCancelMandateServlet.class);

	transient JsonParser jsonParser = new JsonParser();
	private String sponserBankId;
	private String urllink;
	private String authorization;
	
	@Reference
	transient HttpAPIService httpAPIService;
	
	@Reference
	transient EmailSmsApiService emailSmsApiService;
	
	@Reference DatabaseConnectionService databaseConnectionService;
	
	@Activate
	public void activate(DigioCancelMandateServletConfig config) {
		log.info("Activated DigioCancelMandateServlet!");

		this.sponserBankId = config.getSponserBankId();
		this.urllink = config.geturl();
		this.authorization = config.authorization();
		
		
	}
	
	

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
		
		
		
		
		
		
		
		String originalMandateId = "";
		String accountNo = "";
		String mobNo = "";
		String dealDetails="";

		try {
			requestJson = (JsonObject) jsonParser.parse(requestParamJson);
			 originalMandateId = requestJson.get("mandate_id").getAsString();//request.getParameter("mandate_id");
			 accountNo = requestJson.get("accountNo").getAsString();//request.getParameter("accountNo");
			 mobNo = requestJson.get("mobile").getAsString();//request.getParameter("mobile");
			 dealDetails =  requestJson.get("details").getAsString();//srequest.getParameter("details");
		} catch (Exception e) {
			log.error("Exception while transforming the request", e);
		}
		
//Your mandate registered with <<UMR number>> for A/C No ending <<last 4 digit account number>> has been initiated for cancellation on <<cancellation process initiated date>>. Ref No. 123456 – IndusInd Bank
		try {
			/*DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.ENGLISH);
			Date date = new Date();
			String currentDate = dateFormat.format(date);
			String alphaNumeric = RandomStringUtils.randomAlphanumeric(32).toUpperCase();

			JSONObject postData = new JSONObject();

			JSONObject content = new JSONObject();
			content.put("mandate_request_id", alphaNumeric);
			content.put("mandate_creation_date_time", currentDate);
			content.put("sponsor_bank_id", sponserBankId);
			content.put("sponsor_bank_name", "INDUSIND BANK LTD");
			content.put("destination_bank_id", destinationBankId);
			content.put("destination_bank_name", destinationBank);
			content.put("bank_identifier", "INDB");
			content.put("login_id", "INDB00160000010226");
			content.put("mandate_sequence", "001");
			content.put("original_mandate_id", originalMandateId);
			content.put("cancellation_reason", "C001");

			JSONArray signers = new JSONArray();
			JSONObject identifiers = new JSONObject();
			identifiers.put("identifier", mobile);
			signers.put(identifiers);

			postData.put("signers", signers);
			postData.put("comment", "Please sign the document");
			postData.put("expire_in_days", 10);
			postData.put("enach_type", "CANCEL");
			postData.put("partner_entity_email", "CANCEL");
			postData.put("content", content.toString());

			String url = urllink;

			HttpClient httpclient = HttpClientBuilder.create().build();
			HttpPost httpPostRequest = new HttpPost(url);
			StringEntity se = new StringEntity(postData.toString());

			httpPostRequest.setEntity(se);
			httpPostRequest.setHeader("Authorization", authorization);
			httpPostRequest.setHeader("Content-Type", "application/json");
			HttpResponse resp = (HttpResponse) httpclient.execute(httpPostRequest);
			HttpEntity entity = resp.getEntity();

			// Read the content stream
			InputStream instream = entity.getContent();
			BufferedReader br = new BufferedReader(new InputStreamReader(instream));

			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

			JSONObject jsonRes = new JSONObject(sb.toString());
			out.println(jsonRes);*/
			
			JSONObject jsonRes = httpAPIService.callCancelMandate(originalMandateId);
				log.info("Respinse we get {}",jsonRes);
			if(jsonRes.getJSONObject("cancelResult").getString("status").equalsIgnoreCase("success") && jsonRes.getJSONObject("cancelResult").getString("message").equalsIgnoreCase("cancellation has been initiated for the requested mandates") )
			{
				Map<String, String> data = new HashMap<>();
				data.put("Case_Comments_cas",originalMandateId);
				data.put("Close_Sub_Status_cas","Close Resolve");
				data.put("Transaction_Ref_No_cas",originalMandateId);
				data.put("Account_Number_cas",accountNo);
				String amount = dealDetails.substring(dealDetails.indexOf("amount"));
				amount = amount.substring(9,amount.indexOf(',')-1);
				data.put("Amount_cas",amount);
				
				JSONObject repObj = httpAPIService.callCRMNext(data, "enachCancel");

				String[] arr = originalMandateId.split("[,]", 0);
				for(String myStr: arr) {
					
					emailSmsApiService.smsServiceCallCancel(repObj.getString("responseID"), "EA0022", mobNo, myStr, 5, accountNo.substring(accountNo.length()-4));
				}
				databaseConnectionService.insertEnachCancelDatabase(mobNo, originalMandateId, mKey, repObj.getString("responseID"), repObj.getString("responseMSG"), jsonRes.getJSONObject("cancelResult").getString("message"));
				JSONObject jsonResFinal = new JSONObject();
				jsonResFinal.put("message", "success");
				jsonResFinal.put("refid", repObj.getString("responseID"));
				jsonResFinal.put("crmId", repObj.getString("responseID"));
				jsonResFinal.put("crmError", repObj.getString("responseMSG"));
				out.println(EncryptDecrypt.encrypt(encryptKey, encryptKey, jsonResFinal.toString()));
				
			}else {
				databaseConnectionService.insertEnachCancelDatabase(mobNo, originalMandateId, mKey, "NA", "NA", jsonRes.getJSONObject("cancelResult").getString("message"));
				JSONObject jsonResFinal = new JSONObject();
				jsonResFinal.put("message", jsonRes.getJSONObject("cancelResult").getString("message"));
			out.println(EncryptDecrypt.encrypt(encryptKey, encryptKey, jsonResFinal.toString()));
			}

		} catch (Exception e) {
			log.error("Exception in DigioCancelMandateServlet :: ", e);
		}

	}
}
