package com.iib.platform.servlets;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
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

import com.adobe.granite.crypto.CryptoSupport;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import com.iib.platform.services.DatabaseConnectionService;


@Component(service = { Servlet.class }, name = "CFDPLSessionServlet", enabled = true, property = {
		Constants.SERVICE_DESCRIPTION + "=" + "CFDPLSessionServlet ",
		"sling.servlet.methods=" + HttpConstants.METHOD_POST, "sling.servlet.paths=" + "/bin/cfdplsessionme" })

public class CFDPLSessionServlet extends HttpServlet {
	
	@Reference
	transient DatabaseConnectionService databaseConService;
	
	@Reference
	transient CryptoSupport cryptoSupport;
    
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	private static final String MESSAGE = "message";
	private static final String ERROR_CODE="error_code";

	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		boolean jsonError = false;
		HttpServletRequest req = request;
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		StringBuilder stringBuilder = new StringBuilder();
		String requestJson;
		String checkSumHeader = request.getHeader("X-Indusind-Checksum");
		try {
			while ((requestJson = req.getReader().readLine()) != null) {
				stringBuilder.append(requestJson);
			}
		} catch (IOException e) {
			// TO Do
		}
		
		
		if (checkSumHeader !=  null && matchCheckSumHeader(stringBuilder.toString(), checkSumHeader)) {

			try {
				JsonParser parser = new JsonParser();
				JsonElement jsonElement = null;
				jsonElement = parser.parse(stringBuilder.toString());
				JsonObject jsonObj = jsonElement.getAsJsonObject();
				JSONObject success = new JSONObject();
				boolean status = false;
				boolean isSessionExists = false;
				String apiAction = jsonObj.get("api_action").toString();
				apiAction = complieString(apiAction);
				String sessionID = jsonObj.get("session").toString();
				sessionID = complieString(sessionID);
				String[] getCFDPLProperties;
				List<String> fetchresult= new ArrayList<>();
				switch(apiAction) {	
				
				case "insertsession" :
										getCFDPLProperties = getJsonProperties(jsonObj);
										if(getCFDPLProperties.length > 0) {
											LOGGER.info("session value we passed : {}", sessionID );
											fetchresult = databaseConService.getCFDplsession(sessionID,"all");
											if (fetchresult.isEmpty()) {
												status = databaseConService.setCFDplsession(getCFDPLProperties);
												isSessionExists = false;
											}else
											{
												status = false;
												isSessionExists = true;
											}
										}
										else {
										status=false;	
										jsonError=true;
										}
										break;
				case "checkResultsession"  :
											
										fetchresult = databaseConService.getCFDplsession(sessionID);
										if (!fetchresult.isEmpty())
											status=true;
										break;		
				default:				
										break;
				}
				
					
			     
				if (status)

				{
					switch(apiAction) {
					
					case "insertsession" :
											response.setStatus(HttpServletResponse.SC_OK);
											success.put(MESSAGE, "DB Action Success");
											break;
					case "checkResultsession"  :
											response.setStatus(HttpServletResponse.SC_OK);
											success.put(MESSAGE, "Result Action Success");
											if(!fetchresult.isEmpty()) {
											success.put("bdstatus",cryptoSupport.unprotect(fetchresult.get(2)));
											success.put("bdresult", fetchresult.get(3));
											success.put("session",fetchresult.get(0));
											success.put("updatetime", fetchresult.get(1));
											}else
											{
												success.put("bdstatus","Nothing Fetched");
												success.put("bdresult", "Nothing Fetched");
											}
											break;
				
					}
				
				} else {
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					if(jsonError)
					{
						success.put(MESSAGE, "Error in JSON Passed");
						success.put(ERROR_CODE, "E0002");
					}
					else if(isSessionExists)
					{	
					success.put(MESSAGE, "Session Already Exists");
					success.put(ERROR_CODE, "E0004");
					} else 
					{	
					success.put(MESSAGE, "DB Action Failed");
					success.put(ERROR_CODE, "E0003");
					}
				}
				response.getWriter().write(success.toString());

			} catch (Exception e) {

				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

				LOGGER.error("Exception in CDFL SEssion :2: ", e);
			}

		} else {
			JSONObject error = new JSONObject();
			try {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				error.put(MESSAGE, "Authorization Error");
				error.put(ERROR_CODE, "E0001");
				response.getWriter().write(error.toString());
			} catch (JSONException | IOException e) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);	
				LOGGER.error("Exception in eNachWebHookServlet :3: ", e);
			}

		}

	}

	private boolean matchCheckSumHeader(String sb, String checkSumHeader) {

		boolean checksumMatched = false;
		try {

			String valueHex = createHashValue(sb);
			LOGGER.info("checkSumHeader VALUE {}", checkSumHeader);
			LOGGER.info("HASH VALUE {}", valueHex);
			if (checkSumHeader.equalsIgnoreCase(valueHex)) {
				checksumMatched = true;
			} else {
				checksumMatched = false;
			}

		}  catch (Exception e) {
			LOGGER.error("Exception Occoured ", e);
		}

		return checksumMatched;

	}
	
	
	private String createHashValue(String sb) {
		String valueHex="";
		try {

			String key = "!nt38n@1C@11";
			String message = sb;
			Mac hasher = Mac.getInstance("HmacSHA256");
			hasher.init(new SecretKeySpec(key.getBytes(), "HmacSHA256"));
			byte[] hash = hasher.doFinal(message.getBytes());
			LOGGER.info("HASH {}", hash);
			DatatypeConverter.printHexBinary(hash);
			// to base64
			valueHex = DatatypeConverter.printHexBinary(hash);

		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("NoSuchAlgorithmException Occoured ", e);
		} catch (InvalidKeyException e) {
			LOGGER.error("InvalidKeyException Occoured ", e);
		}

		return valueHex;
	}


	public String[] getJsonProperties(JsonObject jsonObj) {

		String pay = "payload";
		String api = "api_mandate";
		try {
		//mobileno,emailid,cifid,dealno,detcustomername,detcustomerbank,detcustomerifsc,detcustomeracc,startdate,enddate,frequency,emiamount
		String mobileno = jsonObj.getAsJsonObject(pay).getAsJsonObject(api).get("mobileno").toString();
		mobileno = complieString(mobileno);
		String emailid = jsonObj.getAsJsonObject(pay).getAsJsonObject(api).get("emailid").toString();
		emailid = complieString(emailid);
		String dealno = jsonObj.getAsJsonObject(pay).getAsJsonObject(api).get("dealno").toString();
		dealno = complieString(dealno);
		String cifid = jsonObj.getAsJsonObject(pay).getAsJsonObject(api).get("cifid").toString();
		cifid = complieString(cifid);	
		String detcustomername = jsonObj.getAsJsonObject(pay).getAsJsonObject(api).get("detcustomername").toString();
		detcustomername = complieString(detcustomername);
		String detcustomerbank = jsonObj.getAsJsonObject(pay).getAsJsonObject(api).get("detcustomerbank").toString();
		detcustomerbank = complieString(detcustomerbank);
		String detcustomerifsc = jsonObj.getAsJsonObject(pay).getAsJsonObject(api).get("detcustomerifsc").toString();
		detcustomerifsc = complieString(detcustomerifsc);
		String detcustomeracc = jsonObj.getAsJsonObject(pay).getAsJsonObject(api).get("detcustomeracc").toString();
		detcustomeracc = complieString(detcustomeracc);
		String startdate = jsonObj.getAsJsonObject(pay).getAsJsonObject(api).get("startdate").toString();
		startdate = complieString(startdate);
		String enddate = jsonObj.getAsJsonObject(pay).getAsJsonObject(api).get("enddate").toString();
		enddate = complieString(enddate);
		String frequency = jsonObj.getAsJsonObject(pay).getAsJsonObject(api).get("frequency").toString();
		frequency = complieString(frequency);
		String emiamount = jsonObj.getAsJsonObject(pay).getAsJsonObject(api).get("emiamount").toString();
		emiamount = complieString(emiamount);
		String redirectURL = jsonObj.getAsJsonObject(pay).getAsJsonObject(api).get("redirectURL").toString();
		redirectURL = complieString(redirectURL);
	

		String id = jsonObj.get("session").toString();
		id = complieString(id);

		String createdate = jsonObj.get("created_at").toString();
		createdate = complieString(createdate);
		
		String apiAction = jsonObj.get("api_action").toString();
		apiAction = complieString(apiAction);
		return new String[] {mobileno,emailid,cifid,dealno,detcustomername,detcustomerbank,detcustomerifsc,detcustomeracc,startdate,enddate,frequency,emiamount, id, createdate, apiAction,redirectURL};
		}catch(Exception e) {
			LOGGER.error("JSON Exception", e);			
		}
		return new String[] {}; 
	}

	private String complieString(String value) {
		value = StringUtils.remove(value, "\"");
		return value;
	}

}
