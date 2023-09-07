package com.iib.platform.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;


import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import javax.servlet.Servlet;
import javax.servlet.ServletException;


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
import org.json.JSONException;
import org.json.JSONObject;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iib.platform.services.config.CFDDigioCreateMandateServletConfig;
import com.iib.platform.services.DatabaseConnectionService;
import com.iib.platform.services.HttpAPIService;
import com.iib.platform.cfdenach.models.CFDEnachSessionForm;
import com.iib.platform.core.smsemail.EmailSmsApiService;

/**
 * DigioCreateMandate Servlet
 *
 * @author Ayasya Digital Solutions LLP
 *
 */
@Component(immediate = true, service = { Servlet.class }, enabled = true, property = {
		Constants.SERVICE_DESCRIPTION + "=" + "DigioCreateMandate Servlet",
		"sling.servlet.methods=" + HttpConstants.METHOD_POST, "sling.servlet.paths=" + "/bin/cfd/digioCreateMandate" })
@Designate(ocd = CFDDigioCreateMandateServletConfig.class)
public class CFDDigioCreateMandateServlet extends SlingAllMethodsServlet {

	private static final long serialVersionUID = 1L;

	private static final String UNAUTORIZE = "unauthorized access";
	private static final String STATUS = "status";
	private static final String INTERACT = "interactionId";

	private static Logger log = LoggerFactory.getLogger(CFDDigioCreateMandateServlet.class);

	transient JsonParser jsonParser = new JsonParser();

	@Reference
	transient HttpAPIService httpAPIService;

	@Reference
	transient DatabaseConnectionService databaseConService;

	@Reference
	transient EmailSmsApiService emailSmsApiService;



	/** Digio API Properties */
	private String digioUrl;
	private String managementCategory;
	private String instrumentType;
	private String isRecurring;
	private String enachType;
	private String authorization;
	private String corporateconfigid;
	private String authmode;
	private String envType;

	private String interactionId;

	@Activate
	public void activate(CFDDigioCreateMandateServletConfig config) {
		log.info("Activated CFD DigioCreateMandateServlet");


		config.accountNo();
		config.cifId();
		
		config.status();

		config.f11();
	
		config.f13();

		this.envType=config.getEnvType();
		this.digioUrl = config.getDigioUrl();
		config.sponserBankId();
		this.managementCategory = config.managementCategory();
		this.instrumentType = config.instrumentType();
		this.isRecurring = config.isRecurring();
		this.enachType = config.enachType();
		this.authorization = config.authorization();
		this.corporateconfigid = config.corporate_config_id();
		this.authmode = config.auth_mode();
	}

	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		String jsonObject = request.getHeader("X-AUTH-TOKEN");
		String key = request.getHeader("X-AUTH-SESSION");
		String requestParamJson = "";
		requestParamJson = decrypt(key, key, jsonObject);
		JsonObject requestJson = null;

		if ((null == jsonObject) || (null == key) || (key.length() != 16))
			out.println(UNAUTORIZE);

		try {
			requestJson = (JsonObject) jsonParser.parse(requestParamJson);
			log.info("Exception {}", requestJson);

		} catch (Exception e) {
			log.error("Exception", e);
		}
		
		String checkforaction = fetchValue(requestJson, "action");
		log.error("requestJson we passed :{} ",requestJson);
			if(checkforaction.equalsIgnoreCase("statusupdate")){
				JSONObject jsonRes = new JSONObject();
				log.info("Final Request we get for DIGIO journey complete 1 {}",(requestJson!=null?requestJson:"Request is null") );
				CFDEnachSessionForm cFDsresultupdate = new CFDEnachSessionForm();
				cFDsresultupdate.setDigioStatus(fetchValue(requestJson, "message") );
				log.info("Final Request we get for DIGIO journey complete 2 {}", cFDsresultupdate.getDigioStatus() );
				
				cFDsresultupdate.setStep(fetchValue(requestJson, "step") );
				log.info("Final Request we get for DIGIO journey complete 3 {}", cFDsresultupdate.getStep());
				cFDsresultupdate.setDigioID(fetchValue(requestJson, "mandateId"));
				log.info("Final Request we get for DIGIO journey complete 4 {}", cFDsresultupdate.getDigioID() );
				cFDsresultupdate.setSession(fetchValue(requestJson, "sessionid"));
				log.info("Final Request we get for DIGIO journey complete 5 {}", cFDsresultupdate.getSession());
				
				try {
					jsonRes.put("status", databaseConService.getCFDStatusUpdate(cFDsresultupdate));
				} catch (JSONException e) {
					log.info("We reached here for scusse 3",e);
				}
				out.println(jsonRes);
				response.flushBuffer();
				
				
	}else if (checkforaction.equalsIgnoreCase("createform")){
				
		JSONObject jsonRes = new JSONObject();
		try {
			jsonRes.put("status", databaseConService.updateCFDCifDetails(fetchValue(requestJson, "mobileNo"), "WithDealID_"+fetchValue(requestJson, "dealno"), fetchValue(requestJson, "session")));
		} catch (JSONException e) {
			log.info("We reached here for scusse 4",e);
		}
		out.println(jsonRes);
		response.flushBuffer();
		
		
		
	} else {
		

		String destinationBank = fetchValue(requestJson, "destinationBank");
		String destinationBankId = fetchValue(requestJson, "destinationBankId");
		String customerAccountNo = fetchValue(requestJson, "customerAccountNo");
		String maximumamount = fetchValue(requestJson, "maximum_amount");
		String transferFrequency = fetchValue(requestJson, "transferFrequency");
		String mobileNo = fetchValue(requestJson, "mobileNo");
		String startDate = fetchValue(requestJson, "startDate");
		String endDate = fetchValue(requestJson, "endDate");
		String customername = fetchValue(requestJson, "customerName");
		String customerRefNumber = fetchValue(requestJson, "accountNo");
		String confirmAccountNo = fetchValue(requestJson, "confirmAccountNo");
		String totalInstallments = fetchValue(requestJson, "totalInstallments");
		String referralCode = fetchValue(requestJson, "referralCode");
		String aadharNo = fetchValue(requestJson, "aadharNo");
		String customerEmail = fetchValue(requestJson, "customerEmail");
		String cifid = fetchValue(requestJson, "cifid");
		String session = fetchValue(requestJson, "session");
		String step = fetchValue(requestJson, "step");
		String emi_amount = fetchValue(requestJson, "emi_amount");
		String appid = fetchValue(requestJson, "appid");
		
		
		if (databaseConService.getCFDCreateEnachSession(key, fetchValue(requestJson, "mobileNo"),
				fetchValue(requestJson, "customerName"), fetchValue(requestJson, "customerEmail")))
			out.println(UNAUTORIZE);
		else {
			log.info("We reached here for scusse");
			try {
			

				
				Map<String, String> resultParam = new HashMap<>();
				resultParam.put(STATUS, "success");
				resultParam.put(INTERACT, "00000000");
				this.interactionId = resultParam.get(INTERACT);
				log.debug("{} :: Interaction ID", interactionId);
				/**
				 * Talisma API End
				 */

				
				JSONObject postData = new JSONObject();
				JSONObject jsonRes = new JSONObject();
				if(checkforaction.equalsIgnoreCase("submitclicked")){
				JSONObject content = new JSONObject();
				content.put("first_collection_date", startDate);
				content.put("final_collection_date", endDate);
				content.put("destination_bank_id", destinationBankId);
				content.put("destination_bank_name", destinationBank); 
				content.put("management_category", managementCategory); 
				content.put("customer_account_number", customerAccountNo);
				content.put("instrument_type", instrumentType);
				content.put("customer_name", customername);
				content.put("maximum_amount", maximumamount);
				content.put("is_recurring", isRecurring);
				content.put("frequency", transferFrequency);
				int pos =0;
				if (customerRefNumber.contains("_"))
					pos = customerRefNumber.lastIndexOf('_');
				if (pos != 0)
				content.put("customer_ref_number", customerRefNumber.substring(pos+1));
				else
				content.put("customer_ref_number", customerRefNumber.substring(pos));	
				if (envType.equalsIgnoreCase("PROD"))
					postData.put("customer_identifier", mobileNo);// Added Deafult for Time Being as requested by Ankur
				if (envType.equalsIgnoreCase("UAT"))
					postData.put("customer_identifier", "ankur.chaturvedi@indusind.com");
				postData.put("auth_mode", authmode);
				postData.put("mandate_type", enachType);
				postData.put("corporate_config_id", corporateconfigid);
				postData.put("mandate_data", content);
				log.info("Digio Create Request ::{} ", postData);

				HttpClient httpclient = HttpClientBuilder.create().build();
				HttpPost httpPostRequest = new HttpPost(digioUrl);

				StringEntity se = new StringEntity(postData.toString());

				httpPostRequest.setEntity(se);
				httpPostRequest.setHeader("Authorization", authorization);
				httpPostRequest.setHeader("Content-Type", "application/json");
				HttpResponse resp = httpclient.execute(httpPostRequest);

				HttpEntity entity = resp.getEntity();

				

				// Read the content stream
				InputStream instream = entity.getContent();
				BufferedReader br = new BufferedReader(new InputStreamReader(instream));

				StringBuilder sb = new StringBuilder();
				String line;
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}

				log.info("Digio Create Response 2 :: {} ", sb.toString());
				jsonRes = new JSONObject(sb.toString());
				}
				
				
				/*
				 * Call to Add create values in database START
				 */

				String uniqueID = UUID.randomUUID().toString();
				
				String uniqueRequestId = "digioreq_" + uniqueID;
				String resultStatus = "failure";
				String interactionIdDet = null;
				String responseCode = null;
				String mandateId = null;
				if (resultParam.containsKey(STATUS)) {
					resultStatus = resultParam.get(STATUS);
				}

				if (resultParam.containsKey(INTERACT)) {
					interactionIdDet = resultParam.get(INTERACT);
				}

				if (jsonRes != null && jsonRes.has("details")) {
					responseCode = jsonRes.getString("details");

					log.debug("values we get responseCode {}", responseCode);
				}

				if (jsonRes != null && jsonRes.has("id")) {
					mandateId = jsonRes.getString("id");

					log.debug("values we get mandateIdmandateId {}", mandateId);
				}

				 CFDEnachSessionForm cFDEnachSessionCreateForm = new CFDEnachSessionForm();			 
				 cFDEnachSessionCreateForm.setSession(session);
				 cFDEnachSessionCreateForm.setAccdetcustacc(customerAccountNo);
				 cFDEnachSessionCreateForm.setAccdetcustbank(destinationBank);
				 cFDEnachSessionCreateForm.setAccdetcustifsc(destinationBankId);
				 cFDEnachSessionCreateForm.setAccdetcustname(customername);
				 cFDEnachSessionCreateForm.setAccountno(customerRefNumber);
				 cFDEnachSessionCreateForm.setCifid(cifid);
				 cFDEnachSessionCreateForm.setEmail(customerEmail);
				 cFDEnachSessionCreateForm.setEmiamount(emi_amount);
				 cFDEnachSessionCreateForm.setEnddate(endDate.equalsIgnoreCase("")?"1900-01-01":endDate);
				 cFDEnachSessionCreateForm.setFrequency(transferFrequency);
				 cFDEnachSessionCreateForm.setMobileno(mobileNo);
				 cFDEnachSessionCreateForm.setRefno(referralCode);
				 cFDEnachSessionCreateForm.setSmiamount(maximumamount); 
				 cFDEnachSessionCreateForm.setStartdate(startDate);
				 cFDEnachSessionCreateForm.setDigioResponse(parseDigioRepose(jsonRes).toString());
				 cFDEnachSessionCreateForm.setStep(step);
				 cFDEnachSessionCreateForm.setAppid(appid);
				 cFDEnachSessionCreateForm.setDigioID(mandateId);
				 cFDEnachSessionCreateForm.setKey(key);
				 cFDEnachSessionCreateForm.setUniqueId(uniqueRequestId);
				 
				 boolean insertedSuccess = databaseConService.cfdcreateNachDetails(cFDEnachSessionCreateForm);
				 
				/*boolean insertedSuccess = databaseConService.cfdcreateNachDetails(uniqueRequestId, customername,
						customerEmail, customerRefNumber, confirmAccountNo, totalAmount, referralCode,
						totalInstallments, aadharNo, destinationBank, destinationBankId, customerAccountNo,
						maximumamount, transferFrequency, mobileNo, startDate, endDate, responseCode, mandateId,
						responseCode, key);*/
				log.info("Added Successfully in DB create mandate data {}", insertedSuccess);
				jsonRes.put(INTERACT, interactionIdDet);
				out.println(jsonRes);

				log.debug("Final Result we get {}", jsonRes);
			} catch (Exception e) {
				log.error("Exception in DigioCreateMandateServlet 1 :: ", e);
			}
		}
		}

	}

	private String fetchValue(JsonObject json, String variable)

	{
		String value = "";
		
		try {
		if ((json !=null) && (json.has(variable)) && (null != json.get(variable))) {
			
				if(json.get(variable) == null)
					value="";
				else
					value = (json.get(variable).getAsString() != null)?json.get(variable).getAsString():"";
		}
		}catch(Exception e)
		{
			log.error("Exection in FetchValue so setting to balnk",e);
			return "";
		}
		

		return value;
	}
	
	private JSONObject parseDigioRepose(JSONObject jsonresponse)
	{
		JSONObject finalresposne = new JSONObject();
		
		if (jsonresponse != null && jsonresponse.has("id")) {
			try {
				finalresposne.put("digio_id", jsonresponse.getString("id"));
				finalresposne.put("state", jsonresponse.getString("state"));
			} catch (JSONException e) {
				log.error("Exception in parseDigioRepose 1 :: ", e);
			}
			
		}
		if (jsonresponse != null && jsonresponse.has("id")) {
			try {
				finalresposne.put("digio_id", jsonresponse.getString("id"));
				finalresposne.put("state", jsonresponse.getString("state"));
			} catch (JSONException e) {
				log.error("Exception in parseDigioRepose 2 :: ", e);		
				}
			
		}else
		{
			
			try {
				if (jsonresponse != null) {
					finalresposne.put("code", jsonresponse.has("code")?jsonresponse.getString("code"):"NO-CODE");
					finalresposne.put("message", jsonresponse.has("message")?jsonresponse.getString("message"):"");
				}
		} catch (JSONException e) {
			log.error("Exception in parseDigioRepose 3 :: ", e);		
			}
			
		}
	
		
		
		return finalresposne;
	}

	public static String encrypt(String key, String initVector, String value) {
		try {
			IvParameterSpec iv = new IvParameterSpec(initVector.getBytes(StandardCharsets.UTF_8));
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

			byte[] encrypted = cipher.doFinal(value.getBytes());

			return Base64.encodeBase64String(encrypted);
		} catch (Exception ex) {
			log.error("Exception in DigioCreateMandateServlet 3 :: ", ex);
		}

		return null;
	}

	public static String decrypt(String key, String initVector, String encrypted) {
		try {

			IvParameterSpec iv = new IvParameterSpec(key.getBytes(StandardCharsets.UTF_8));
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

			byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));

			return new String(original);
		} catch (Exception ex) {
			log.info("Exception in DigioCreateMandateServlet 4 ::{} {} ", ex, initVector);
		}

		return null;
	}

}
