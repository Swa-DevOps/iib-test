package com.iib.platform.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URI;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
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

import com.iib.platform.services.DatabaseConnectionService;
import com.iib.platform.services.config.MandateTransactionConfig;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.iib.platform.common.utils.EncryptDecrypt;

@Component(service = { Servlet.class }, name = "MandateDetailsServlet", enabled = true, property = {
		Constants.SERVICE_DESCRIPTION + "=" + "MandateDetails Servlet",
		"sling.servlet.methods=" + HttpConstants.METHOD_POST, "sling.servlet.paths=" + "/bin/getMandateDetails" })
@Designate(ocd = MandateTransactionConfig.class)

public class MandateDetails extends SlingAllMethodsServlet {

	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(MandateDetails.class);
	transient JsonParser jsonParser = new JsonParser();
	private static final String ENDDATE = "endDate";

	@Reference
	transient DatabaseConnectionService databaseConService;

	String url;
	String clientidname;
	String clientidvalue;
	String secretidname;
	String secretidvalue;
	String includeHeader;

	@Activate
	protected void activate(MandateTransactionConfig config) {

		url = config.getURLTransactionDetails();
		clientidname = config.getClientID();
		clientidvalue = config.getClientIDValue();
		secretidname = config.getClientSecretKey();
		secretidvalue = config.getClientSecretValue();
		includeHeader = config.getIncludeHeader();
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

		String umrnNumber = "";
		String mobileNumber = "";
		try {
			requestJson = (JsonObject) jsonParser.parse(requestParamJson);
			umrnNumber = requestJson.get("umrnNumber").getAsString();
			mobileNumber = requestJson.get("V1").getAsString();
		} catch (Exception e) {
			log.error("Exception in MandateDetailsServlet :: ", e);
		}

		try {

			if ((null == key))
				out.println("unauthorized access");
			else if (key.length() != 36)
				out.println("unauthorized access");
			else {
				JSONObject jsonObj = new JSONObject();

				JSONObject mandateDetails = new JSONObject();
// Database Changes START

				String mandateDataDetails = databaseConService.getMandateDetails(mobileNumber, "enach-mandate");

				if (StringUtils.isNotBlank(mandateDataDetails)) {
					JSONArray mandateDataDetArray = new JSONArray(mandateDataDetails);
					log.info("mandateData > {}", mandateDataDetArray);

					for (int obji = 0; obji < mandateDataDetArray.length(); obji++) {
						JSONObject currentObject = mandateDataDetArray.getJSONObject(obji);
						if (currentObject.getString("umrn").equalsIgnoreCase(umrnNumber)) {
							mandateDetails = currentObject;
							break;
						}
					}
				} else {
					log.info("Unable to fetch mandate details for mobile number : {}", mobileNumber);
				}

				// Databse Changes END

				HttpClient httpclient = HttpClientBuilder.create().build();
				URI uri = new URIBuilder(url).build();
				HttpPost httpPostRequest = new HttpPost(uri);

				if (includeHeader.equalsIgnoreCase("Yes")) {
					httpPostRequest.setHeader(secretidname, secretidvalue);
					httpPostRequest.setHeader(clientidname, clientidvalue);
				}

				JSONObject requestJsonWrapper = new JSONObject();

				JSONObject headerJson = new JSONObject(
						"{ \"sourceId\": \"1002\", \"mndAction\": \"ENQUIRY\", \"msgId\": \"\" }");
				JSONObject requestJson1 = new JSONObject();

				long randomNumber = (long) Math.floor(Math.random() * 9000000000000L) + 1000000000000L;

				headerJson.put("msgId", "USFB" + randomNumber);

				requestJson1.put("umrn", umrnNumber);
				requestJson1.put("fromDate", mandateDetails.getString("startDate"));

				Date currentDate = new Date();
				Calendar calendar = Calendar.getInstance();
				SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-DD");
				calendar.setTime(currentDate);
				calendar.add(Calendar.DAY_OF_MONTH, 365);

				String convertedDateStr = dateFormatter.format(calendar.getTime());

				if (mandateDetails.getString(ENDDATE).equalsIgnoreCase("")
						|| (mandateDetails.getString(ENDDATE) == null))
					requestJson1.put("toDate", convertedDateStr);
				else
					requestJson1.put("toDate", mandateDetails.getString(ENDDATE));

				requestJsonWrapper.put("header", headerJson);
				requestJsonWrapper.put("request", requestJson1);
				log.info("Request Json>> {}", requestJsonWrapper);
				StringEntity requestEntity = new StringEntity(requestJsonWrapper.toString(),
						ContentType.APPLICATION_JSON);

				httpPostRequest.setEntity(requestEntity);

				HttpResponse resp = httpclient.execute(httpPostRequest);
				HttpEntity entity = resp.getEntity();

				// Read the content stream
				InputStream instream = entity.getContent();
				BufferedReader br = new BufferedReader(new InputStreamReader((instream)));
				StringBuilder sb = new StringBuilder();
				String line;
				while ((line = br.readLine()) != null) {
					sb.append(line);
					sb.append("\n");
				}

				jsonObj.put("transaction", new JSONObject(sb.toString()));
				jsonObj.put("mandateDetails", mandateDetails);

				out.println(EncryptDecrypt.encrypt(encryptKey, encryptKey, jsonObj.toString()));

				log.info(" Result for URMN number is :-{} ", sb);

			}
		} catch (Exception e) {
			log.error("Exception in MandateDetailsServlet :: ", e);
		}

	}

}
