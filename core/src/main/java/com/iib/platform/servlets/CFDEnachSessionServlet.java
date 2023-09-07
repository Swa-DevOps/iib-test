package com.iib.platform.servlets;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
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
import com.iib.platform.common.objects.EnachSSO;
import com.iib.platform.services.DatabaseConnectionService;


@Component(service = { Servlet.class }, name = "CFDEnachSessionServlet", enabled = true, property = {
		Constants.SERVICE_DESCRIPTION + "=" + "CFDEnachSessionServlet ",
		"sling.servlet.methods=" + HttpConstants.METHOD_POST, "sling.servlet.paths=" + "/bin/cfdenach/sessionme" })

public class CFDEnachSessionServlet extends HttpServlet {
	
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
			LOGGER.info("request Json value we passed : {}", stringBuilder );

		} catch (IOException e) {
			LOGGER.info("IOException Json value we parsed : ", e );
		}		
		if (matchCheckSumHeader(stringBuilder.toString(), checkSumHeader)) {

			try {
				JsonParser parser = new JsonParser();
				JsonElement jsonElement = null;
				jsonElement = parser.parse(stringBuilder.toString());
				JsonObject jsonObj = jsonElement.getAsJsonObject();
				JSONObject success = new JSONObject();
				boolean status = false;
				boolean isSessionExists = false;
				boolean noSuccessResult = false;
				String apiAction = jsonObj.get("api_action").toString();
				apiAction = complieString(apiAction);
				String sessionID = jsonObj.get("session").toString();
				sessionID = complieString(sessionID);
				String[] getCFDPLProperties;
				List<String> fetchresult= new ArrayList<>();
				EnachSSO fectresultsso = new EnachSSO();
				switch(apiAction) {	
				
				case "insertsession" :
										getCFDPLProperties = getJsonProperties(jsonObj);
										if(getCFDPLProperties.length > 0) {
											LOGGER.info("session value we passed : {}", sessionID );
											fectresultsso = databaseConService.getCFDenachsession(sessionID,"all");
											LOGGER.info("session value we passed : {} niket-{}-niket", fectresultsso, fectresultsso.getSession() );
											
											if ((fectresultsso.getSession() == null) || (fectresultsso.getSession().equalsIgnoreCase(""))) {
												status = databaseConService.setCFDenachsession(getCFDPLProperties);
												isSessionExists = false;
											}else
											{
												LOGGER.error("session already exists");
												status = false;
												isSessionExists = true;
											}
										}else {
										status=false;	
										jsonError=true;
										}
										break;
				case "checkResultsession":
											
											fectresultsso = databaseConService.getCFDenachsession(sessionID);
					
									
										if (((fectresultsso.getSession() != null)) && (!(fectresultsso.getSession().equalsIgnoreCase(""))))		
											status=true;
			
										else
										{
											noSuccessResult = true;
										}
			
										break;		
				default:				
										break;
				}
				
					
			     
				if (status)

				{
					switch(apiAction) {
					
					case "insertsession":
											response.setStatus(HttpServletResponse.SC_OK);
											success.put(MESSAGE, "DB Action Success");
											break;
					case "checkResultsession":
											response.setStatus(HttpServletResponse.SC_OK);
											success.put(MESSAGE, "Successfully Fetched");
											if (((fectresultsso.getSession() != null)) && (!(fectresultsso.getSession().equalsIgnoreCase(""))))		
												{
												success.put("customerEmail",fectresultsso.getEmailid());
												success.put("customerMobile", fectresultsso.getMobileno());									
												success.put("Dcustomername",fectresultsso.getDetcustomername());
												success.put("Dcustomerbank", fectresultsso.getDetcustomerbank());
												success.put("DcustomerbankIFSCI",fectresultsso.getDetcustomerifsc());
												success.put("DcustomerAcc",fectresultsso.getDetcustomeracc());
												success.put("CIFID",fectresultsso.getCifid());
												success.put("DealNo", fectresultsso.getAccountno());
												success.put("startDate",fectresultsso.getStartdate());
												success.put("endDate",fectresultsso.getEnddate());
												success.put("emi_amount",fectresultsso.getEmiamount());
												success.put("max_amount",fectresultsso.getSmiamount());
												success.put("frequency",fectresultsso.getFrequency());
												success.put("urmn", fectresultsso.getUrmn());
												success.put("npcistatus",fectresultsso.getNpcistatus());
												success.put("npcimessage",fectresultsso.getNpcitxid());
												success.put("npcirejectreason",fectresultsso.getNpcirejectreason());
												success.put("fluxstatus",fectresultsso.getFluxstatus());
												success.put("fluxmessage",fectresultsso.getFluxmessage());
												success.put("referralCode",fectresultsso.getReferralCode());
												success.put("enachstatus",fectresultsso.getEnachstatus());
												success.put("digioID", fectresultsso.getDigioid());
												success.put("session",fectresultsso.getSession());
												success.put("updatetime",fectresultsso.getUpdatedtime());
												
											}else
											{
												success.put("bdstatus","Nothing Fetched");
												success.put("bdresult", "Nothing Fetched");
											}
											break;
				
					}
				
				} else {
					response.setStatus(HttpServletResponse.SC_OK);
					if(jsonError)
					{
						success.put(MESSAGE, "Error in JSON Passed");
						success.put(ERROR_CODE, "E0002");
					}
					else if(isSessionExists)
					{	
					success.put(MESSAGE, "Session Already Exists");
					success.put(ERROR_CODE, "E0004");
					}else if(noSuccessResult)
					{	
					success.put(MESSAGE, "Nothing Fetched for SessionID");
					success.put(ERROR_CODE, "E0005");
					} else
					{	
					success.put(MESSAGE, "DB Action Failed");
					success.put(ERROR_CODE, "E0003");
					}
				}
				response.getWriter().write(success.toString());

			} catch (Exception e) {

				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

				LOGGER.error("Exception in CFD Enach SEssion :2: ", e);
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
				LOGGER.error("Exception in  CFD Enach :3: ", e);
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
			LOGGER.error("Exception Occoured  CFD Enach", e);
		}

		return checksumMatched;

	}
	
	private static final String HMAC_SHA256 = "HmacSHA256";

	private String createHashValueN(String sb)  {
        Mac sha512Hmac;
        String result="";
        final String key = "!nt38n@1C@11";

        try {
            final byte[] byteKey = key.getBytes(StandardCharsets.UTF_8);
            sha512Hmac = Mac.getInstance(HMAC_SHA256);
            SecretKeySpec keySpec = new SecretKeySpec(byteKey, HMAC_SHA256);
            sha512Hmac.init(keySpec);
            byte[] macData = sha512Hmac.doFinal(sb.getBytes(StandardCharsets.UTF_8));

            // Can either base64 encode or put it right into hex
            result = Base64.getEncoder().encodeToString(macData);
            return result;
             
        } catch (Exception e) {
        	LOGGER.error("Exception in createHashValueN 2 :: ", e);
            return result;
        } 
        
        
    }
	
	
	
	private String createHashValue(String sb) {
		String valueHex="";
		try {

			String key = "!nt38n@1C@11";
			String message = sb;
			Mac hasher = Mac.getInstance("HmacSHA256");			
			hasher.init(new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
			byte[] hash = hasher.doFinal(message.getBytes(StandardCharsets.UTF_8));			
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
		String dealno = jsonObj.getAsJsonObject(pay).getAsJsonObject(api).get("accountnoordealno").toString();
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
		String click2edit = jsonObj.getAsJsonObject(pay).getAsJsonObject(api).get("click2edit").toString();
		click2edit = complieString(click2edit);
		String autopayfactor = jsonObj.getAsJsonObject(pay).getAsJsonObject(api).get("autopayfactor").toString();
		autopayfactor = complieString(autopayfactor);
		String frequency = jsonObj.getAsJsonObject(pay).getAsJsonObject(api).get("frequency").toString();
		frequency = complieString(frequency);
		String emiamount = jsonObj.getAsJsonObject(pay).getAsJsonObject(api).get("emiamount").toString();
		emiamount = complieString(emiamount);
		String redirectURL = jsonObj.getAsJsonObject(pay).getAsJsonObject(api).get("redirectURL").toString();
		redirectURL = complieString(redirectURL);
		String refno = jsonObj.getAsJsonObject(pay).getAsJsonObject(api).get("referralCode").toString();
		refno = complieString(refno);
		String appid = jsonObj.getAsJsonObject(pay).getAsJsonObject(api).get("appid").toString();
		appid = complieString(appid);
		
	

		String id = jsonObj.get("session").toString();
		id = complieString(id);

		String createdate = jsonObj.get("created_at").toString();
		createdate = complieString(createdate);
		
		String apiAction = jsonObj.get("api_action").toString();
		apiAction = complieString(apiAction);
		return new String[] {mobileno,emailid,cifid,dealno,detcustomername,detcustomerbank,detcustomerifsc,detcustomeracc,startdate,enddate,frequency,emiamount, id, createdate, apiAction,redirectURL,click2edit,autopayfactor,refno,appid};
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
