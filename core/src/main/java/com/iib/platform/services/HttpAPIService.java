package com.iib.platform.services;

import java.util.Map;

import org.json.JSONObject;

import com.iib.platform.api.request.RequestObject;
import com.iib.platform.api.response.ResponseObject;

/**
 * HTTP API Service
 *
 * @author TechChefz (TCZ Consulting LLP)
 *
 */

public interface HttpAPIService {

	public ResponseObject callAPI(RequestObject requestObject);

	public ResponseObject getSMSResponse(RequestObject requestObject, String mobileNumber, int otp);

	public ResponseObject getSMSResponseWithMode(RequestObject requestObject, String mobileNumber, String mode,
			int otp);

	public ResponseObject getSOAPResponse(RequestObject request, String mobileNumber);

	public JSONObject getviaAccountNo(String mobileNumber);

	public JSONObject getAccountViaDOB();

	public JSONObject getManageEnachDetails(String accountNumber);
	
	public JSONObject callCancelMandate(String accountNumber);
	
	public JSONObject getDetailsViaNewAPI(String mobile, String accountno, String requestId);
	
	public JSONObject callCRMNext(Map<String,String> data, String action);

}
