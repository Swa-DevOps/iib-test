package com.iib.platform.services;

import org.json.JSONArray;

import com.iib.platform.api.request.RequestObject;
import com.iib.platform.api.response.ResponseObject;

/**
 * Soap API Service
 *
 * @author Niket ADS
 *
 */

public interface SoapAPIService {

	public ResponseObject getHelpMeStatus(RequestObject requestObject, String customerName, String mobileNumber,
			String emailId);

	public JSONArray getAccountDetails(String mobileNumber);
	
	public JSONArray getCFDAccountDetails(String mobileNumber);
	
	public JSONArray getCFDDealDetails(String mobileNumber,String dealno);
	

}
