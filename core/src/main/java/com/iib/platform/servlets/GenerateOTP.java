package com.iib.platform.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.json.JSONArray;
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

import com.iib.platform.api.request.RequestObject;
import com.iib.platform.api.response.ResponseBody;
import com.iib.platform.api.response.ResponseObject;
import com.iib.platform.services.DatabaseConnectionService;
import com.iib.platform.services.HttpAPIService;
import com.iib.platform.services.StoreOTPService;
import com.iib.platform.services.config.GenerateOTPConfig;
import com.iib.platform.services.RecaptchaService;
import com.iib.platform.services.SoapAPIService;
import com.iib.platform.common.utils.EncryptDecrypt;

/**
 * Generate OTP Servlet
 *
 * @author Niket Goel
 *
 */

@Component(immediate = true, service = { Servlet.class }, enabled = true, property = {
		Constants.SERVICE_DESCRIPTION + "=" + "Generate OTP Servlet",
		"sling.servlet.methods=" + HttpConstants.METHOD_POST, "sling.servlet.paths=" + "/bin/generateOtp" })
@Designate(ocd = GenerateOTPConfig.class)
public class GenerateOTP extends SlingAllMethodsServlet {

	private static final long serialVersionUID = 1L;
	
	private static String DBENCRYPTYKEY= "!ndus!nd!ndus!nd";

	/** Static Logger */
	private static Logger log = LoggerFactory.getLogger(GenerateOTP.class);

	transient JsonParser jsonParser = new JsonParser();
	
	

	@Reference
    private transient RecaptchaService recaptchaService;
	
	@Reference
	transient HttpAPIService httpAPIService;

	@Reference
	transient StoreOTPService storeOTPService;

	@Reference
	transient EncryptDecrypt ecryptDecrypt;
	
	@Reference
	transient SoapAPIService soapAPIService;

	@Reference
	transient DatabaseConnectionService databaseConService;

	/** Private Static Fields */
	private String requestUrl;
	private String byPassCaptcha;


	@Activate
	public void activate(GenerateOTPConfig config) {
		log.info("Activated GenerateOTP");

		this.requestUrl = config.requestUrl();
		this.byPassCaptcha = config.byPassCaptach();
		
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

		String mobile = "";
		String mode = "";
		String forservice = "";
		String gcaptcha="";

		try {
			requestJson = (JsonObject) jsonParser.parse(requestParamJson);
			mobile = requestJson.get("mobile").getAsString();
			mode = requestJson.get("mode").getAsString();
			if(requestJson.has("service"))
				forservice = requestJson.get("service").getAsString();
			//For Captcha Implementation 
			if(requestJson.has("gcaptcha"))
				gcaptcha = requestJson.get("gcaptcha").getAsString();

		} catch (Exception e) {
			log.error("Exception, e");
		}
		log.info("REquest WE GET {} {} {} {}", jsonObject, encryptKey, requestParamJson, mKey);
		
	
		try {

			if ((null == mKey) || (mKey.length() != 36) ) {
				out.println("unauthorized access");
			}else
			{			

			boolean resp = (gcaptcha.equalsIgnoreCase(""))?true:recaptchaService.verifyRecaptchaResponseFromGoogle(gcaptcha);
		          	
			if(forservice.equalsIgnoreCase("cfd"))	
				resp=true;
			if(byPassCaptcha.equalsIgnoreCase("Yes"))
				resp=true;
			
			
			JSONObject responseMob = fetchAccountDetails(mobile);
			if(responseMob.get("success").toString().equalsIgnoreCase("true") || ((forservice.equalsIgnoreCase("cfd")))) {
			if(resp)
			{	
				
				
				
				int otp = (int) (Math.floor(Math.random() * 900000) + 100000);
	
				log.info("OTP in Generate OTP Servlet ::{} ", otp);
				
				int noofCounts = 0;
				
				
				
				if(forservice.equalsIgnoreCase("cfd"))
				{
					noofCounts=storeOTPService.checkCountOfOTP(EncryptDecrypt.encrypt(DBENCRYPTYKEY, DBENCRYPTYKEY, mobile), "cfd-enach-mandate", mKey, "check");
					if ( noofCounts < 3) {
							storeOTPService.storeOTPWithSession(EncryptDecrypt.encrypt(DBENCRYPTYKEY, DBENCRYPTYKEY, mobile), otp, "cfd-enach-mandate", mKey);
							
					}
				}
				else
					storeOTPService.storeOTPWithSession(EncryptDecrypt.encrypt(DBENCRYPTYKEY, DBENCRYPTYKEY, mobile), otp, "enach-mandate", mKey);
				
				/*if(forservice.equalsIgnoreCase("cfd"))
				{
					noofCounts=storeOTPService.checkCountOfOTP(mobile, "cfd-enach-mandate", mKey, "check");
					if ( noofCounts < 3) {
							storeOTPService.storeOTPWithSession(mobile, otp, "cfd-enach-mandate", mKey);
							
					}
				}
				else
					storeOTPService.storeOTPWithSession(mobile, otp, "enach-mandate", mKey);*/
	
				if (noofCounts < 3) {
				RequestObject requestObject = new RequestObject();
				requestObject.setRequestUrl(requestUrl);
				ResponseObject responseObject = httpAPIService.getSMSResponseWithMode(requestObject, mobile, mode, otp);
				ResponseBody body = responseObject.getResponseBody();
				String json = body.getResponseContentXML();
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("TID", json);
				if(forservice.equalsIgnoreCase("cfd"))
				{
					storeOTPService.checkCountOfOTP(EncryptDecrypt.encrypt(DBENCRYPTYKEY, DBENCRYPTYKEY, mobile), "cfd-enach-mandate", mKey, "update");
				}
				out.println(jsonObj);
				}else
				{
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("TIDI", "Exceeds");
				out.println(jsonObj);
					
				}
			}else {
				
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("error", "Recaptcha not verified");
				out.println(jsonObj);
            }
			}else
			{
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("error", "Mobile not exists");
				out.println(jsonObj);
			}
			}
			response.flushBuffer();

		} catch (Exception e) {
			log.error("Exception in Generate OTP Servlet :: ", e);
		}

	}
	
	
	
	public JSONObject fetchAccountDetails(String mobileNumber)

	{
		JSONObject response = null;
		try {
			JSONArray jsonArray = soapAPIService.getAccountDetails(mobileNumber);

			if(jsonArray.length()>=1) {
				response = new JSONObject();
			response.put("success", "true");
			}else
			{
				response = new JSONObject();
				response.put("success", "false");
			}
			

			return response;
		} catch (Exception e) {
			log.error("Exception", e);
			response = new JSONObject();
			try {
				response.put("success", "false");
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return response;
		}

	}
}