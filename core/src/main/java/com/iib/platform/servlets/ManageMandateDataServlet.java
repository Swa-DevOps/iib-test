package com.iib.platform.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.json.JSONArray;
import org.json.JSONObject;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iib.platform.services.DatabaseConnectionService;
import com.iib.platform.services.HttpAPIService;
import com.iib.platform.services.SoapAPIService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.iib.platform.common.utils.EncryptDecrypt;

/**
 * Mandate Data Servlet
 *
 * @author
 *
 */
@Component(service = { Servlet.class }, name = "Mandate Data Servlet", enabled = true, property = {
		Constants.SERVICE_DESCRIPTION + "=" + "Mandate Data Servlet",
		"sling.servlet.methods=" + HttpConstants.METHOD_POST, "sling.servlet.paths=" + "/bin/manageMandateData" })
public class ManageMandateDataServlet extends SlingAllMethodsServlet {

	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(ManageMandateDataServlet.class);
	transient JsonParser jsonParser = new JsonParser();

	@Reference
	transient HttpAPIService httpAPIService;

	@Reference
	transient SoapAPIService soapApiService;

	@Reference
	transient DatabaseConnectionService databaseConService;

	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();

		String mobileNumber = "";
		String dob = "";
		String mandateflag = "";
		String accounNumber = "";
		JSONObject accounts =null;
		JSONArray mandateData = new JSONArray();
		JSONObject res = new JSONObject();

		String key = request.getHeader("X-AUTH-SESSION");

		String jsonObject = request.getHeader("X-AUTH-TOKEN");
		String encryptKey = jsonObject.substring(jsonObject.indexOf(':') + 1);
		jsonObject = jsonObject.substring(0, (jsonObject.indexOf(':')));
		String requestParamJson = "";
		requestParamJson = EncryptDecrypt.decrypt(encryptKey, encryptKey, jsonObject);
		JsonObject requestJson = null;

		try {
			requestJson = (JsonObject) jsonParser.parse(requestParamJson);
			mobileNumber = requestJson.get("V1").getAsString();
			dob = requestJson.get("V2").getAsString();
			mandateflag = requestJson.get("V3").getAsString();
			accounNumber =requestJson.get("V5").getAsString();
		
		} catch (Exception e) {
			log.error("Exception in ManageMandateDataServlet 1 ", e);
		}

		try {

			if ((null == key))
				out.println("unauthorized access");
			else if (key.length() != 36)
				out.println("unauthorized access");
			else {

				log.info("Values from session {} {} {}", mobileNumber, dob, accounNumber);

				/*
				 * Get Account Number based on DOB
				 

				List<String> accountNumber = fetchAccountNumber(mobileNumber, dob, key);

				log.info("When we get :---> {}", accountNumber);

				for (String accountNo : accountNumber) {
					accounts = (JSONObject) httpAPIService.getManageEnachDetails(accountNo).get("accounts");

					JSONObject responseObject = accounts.getJSONObject("response");
					for (int respCntr = 0; respCntr < responseObject.length(); respCntr++) {
						String stringCounter = "" + respCntr;
						if (responseObject.has(stringCounter)) {
							JSONObject currentJsonObject = new JSONObject();

							currentJsonObject.put("umrn",
									getKeyValuefromJsonObject(responseObject.getJSONObject(stringCounter), "umrn"));
							currentJsonObject.put("amount",
									getKeyValuefromJsonObject(responseObject.getJSONObject(stringCounter), "amt"));
							currentJsonObject.put("accountnumber",
									getKeyValuefromJsonObject(responseObject.getJSONObject(stringCounter), "benfNum"));
							currentJsonObject.put("referenceNo", getKeyValuefromJsonObject(
									responseObject.getJSONObject(stringCounter), "utilityCode"));
							currentJsonObject.put("bankName",
									getKeyValuefromJsonObject(responseObject.getJSONObject(stringCounter), "payBank"));
							currentJsonObject.put("mobileNumber",
									"+91-" + getKeyValuefromJsonObject(responseObject.getJSONObject(stringCounter),
											"mobileNumber"));
							currentJsonObject.put("startDate",
									getKeyValuefromJsonObject(responseObject.getJSONObject(stringCounter), "stDate"));
							currentJsonObject.put("endDate",
									getKeyValuefromJsonObject(responseObject.getJSONObject(stringCounter), "expDate"));
							currentJsonObject.put("status",
									getKeyValuefromJsonObject(responseObject.getJSONObject(stringCounter), "status"));
							currentJsonObject.put("untilCancel", getKeyValuefromJsonObject(
									responseObject.getJSONObject(stringCounter), "untilCancel"));
							currentJsonObject.put("freq",
									getKeyValuefromJsonObject(responseObject.getJSONObject(stringCounter), "freq"));
							currentJsonObject.put("txnType",
									getKeyValuefromJsonObject(responseObject.getJSONObject(stringCounter), "txnType"));
							currentJsonObject.put("benfNum", getKeyValuefromJsonObject(
									responseObject.getJSONObject(stringCounter), "payerAccountNumber"));

							mandateData.put(currentJsonObject);
						}
					}

					log.info("Manadate Data to pass {}", mandateData);
				} */
				
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				
				long requestId;
				requestId = System.currentTimeMillis();
				
				accounts = (JSONObject) httpAPIService.getDetailsViaNewAPI(mobileNumber, accounNumber, String.valueOf(requestId));

				log.info("Manadate Data to result {}", accounts);
				
			//	String stubjson = "{\"mandates\":{\"status-code\":\"Accepted\",\"status-message\":\"Request Succeeded\",\"mandateData\":[{\"umrn\":\"INDB0000000000016676\",\"amount\":\"1111\",\"accountnumber\":null,\"orgName\":null,\"referenceNo\":\"INDB300150424cmu2\",\"endDate\":\"Until Cancel\",\"purpseCode\":null,\"mobileNumber\":\"9811532425\",\"bankName\":\"IndusInd Bank\",\"startDate\":\"16-04-2015\",\"status\":\"Accepted\"},{\"umrn\":\"INDB0000000000016676\",\"amount\":\"1111\",\"accountnumber\":null,\"orgName\":null,\"referenceNo\":\"INDB300150424cmpf\",\"endDate\":\"Until Cancel\",\"purpseCode\":null,\"mobileNumber\":\"9811532425\",\"bankName\":\"IndusInd Bank\",\"startDate\":\"16-04-2015\",\"status\":\"Accepted\"},{\"umrn\":\"HDFC0000000000209587\",\"amount\":\"1111111\",\"accountnumber\":\"12334\",\"orgName\":\"ADITYA BIRLA HOUSING FINANCE LTD AF\",\"referenceNo\":\"INDB300150724003fpr\",\"endDate\":\"Until Cancel\",\"purpseCode\":\"EMI - Loan repayment\",\"mobileNumber\":\"+91-9811532425\",\"bankName\":\"IndusInd Bank\",\"startDate\":\"05-08-2015\",\"status\":\"Accepted\"},{\"umrn\":\"ICIC6000000007159751\",\"amount\":\"1111\",\"accountnumber\":\"1111111\",\"orgName\":\"Aditya Birl Capital Ltd\",\"referenceNo\":\"1536892876089\",\"endDate\":\"14-09-2021\",\"purpseCode\":null,\"mobileNumber\":\"9811532425\",\"bankName\":\"IndusInd Bank\",\"startDate\":\"14-09-2018\",\"status\":\"Accepted\"},{\"umrn\":\"UTIB7000000008837779\",\"amount\":\"11111\",\"accountnumber\":null,\"orgName\":\"Aditya Birla Finance Limited\",\"referenceNo\":\"INDB300191009lpha\",\"endDate\":\"31-12-2099\",\"purpseCode\":null,\"mobileNumber\":\"9811532425\",\"bankName\":\"IndusInd Bank\",\"startDate\":\"05-12-2019\",\"status\":\"Accepted\"}]}}";

			//	accounts =  new JSONObject(stubjson);
				/*
				 * "{\"mandates\":{\"status-code\":\"Accepted\",\"status-message\":\"Request Succeeded\",\"mandateData\":[{\"umrn\":\"INDB0000000000016676\",\"amount\":\"1111\",\"accountnumber\":null,\"orgName\":null,\"referenceNo\":\"INDB300150424cmu2\",\"endDate\":\"Until Cancel\",\"purpseCode\":null,\"mobileNumber\":\"9811532425\",\"bankName\":\"IndusInd Bank\",\"startDate\":\"16-04-2015\",\"status\":\"Accepted\"},{\"umrn\":\"INDB0000000000016676\",\"amount\":\"1111\",\"accountnumber\":null,\"orgName\":null,\"referenceNo\":\"INDB300150424cmpf\",\"endDate\":\"Until Cancel\",\"purpseCode\":null,\"mobileNumber\":\"9811532425\",\"bankName\":\"IndusInd Bank\",\"startDate\":\"16-04-2015\",\"status\":\"Accepted\"},{\"umrn\":\"HDFC0000000000209587\",\"amount\":\"1111111\",\"accountnumber\":\"12334\",\"orgName\":\"ADITYA BIRLA HOUSING FINANCE LTD AF\",\"referenceNo\":\"INDB300150724003fpr\",\"endDate\":\"Until Cancel\",\"purpseCode\":\"EMI - Loan repayment\",\"mobileNumber\":\"+91-9811532425\",\"bankName\":\"IndusInd Bank\",\"startDate\":\"05-08-2015\",\"status\":\"Accepted\"},{\"umrn\":\"ICIC6000000007159751\",\"amount\":\"1111\",\"accountnumber\":\"1111111\",\"orgName\":\"Aditya Birl Capital Ltd\",\"referenceNo\":\"1536892876089\",\"endDate\":\"14-09-2021\",\"purpseCode\":null,\"mobileNumber\":\"9811532425\",\"bankName\":\"IndusInd Bank\",\"startDate\":\"14-09-2018\",\"status\":\"Accepted\"},{\"umrn\":\"UTIB7000000008837779\",\"amount\":\"11111\",\"accountnumber\":null,\"orgName\":\"Aditya Birla Finance Limited\",\"referenceNo\":\"INDB300191009lpha\",\"endDate\":\"31-12-2099\",\"purpseCode\":null,\"mobileNumber\":\"9811532425\",\"bankName\":\"IndusInd Bank\",\"startDate\":\"05-12-2019\",\"status\":\"Accepted\"}]}}\n\t\t"
				 * 
				 * 
				 *  {"mandates":{"status-code":"Accepted","status-message":"Request Succeeded","mandateData":[{"umrn":"INDB0000000000016676","amount":"1111","accountnumber":null,"orgName":null,"referenceNo":"INDB300150424cmu2","endDate":"Until Cancel","purpseCode":null,"mobileNumber":"9811532425","bankName":"IndusInd Bank","startDate":"16-04-2015","status":"Accepted"},
				 *  {"umrn":"INDB0000000000016676","amount":"1111","accountnumber":null,"orgName":null,"referenceNo":"INDB300150424cmpf",
				 *  "endDate":"Until Cancel","purpseCode":null,"mobileNumber":"9811532425","bankName":"IndusInd Bank","startDate":"16-04-2015"
				 *  ,"status":"Accepted"},{"umrn":"HDFC0000000000209587","amount":"1111111","accountnumber":"12334","orgName":"ADITYA BIRLA HOUSING FINANCE LTD AF","referenceNo":"INDB300150724003fpr","endDate":"Until Cancel","purpseCode":"EMI - Loan repayment","mobileNumber":"+91-9811532425","bankName":"IndusInd Bank","startDate":"05-08-2015","status":"Accepted"},{"umrn":"ICIC6000000007159751","amount":"1111","accountnumber":"1111111","orgName":"Aditya Birl Capital Ltd","referenceNo":"1536892876089","endDate":"14-09-2021","purpseCode":null,"mobileNumber":"9811532425","bankName":"IndusInd Bank","startDate":"14-09-2018","status":"Accepted"},{"umrn":"UTIB7000000008837779","amount":"11111","accountnumber":null,"orgName":"Aditya Birla Finance Limited","referenceNo":"INDB300191009lpha","endDate":"31-12-2099","purpseCode":null,"mobileNumber":"9811532425","bankName":"IndusInd Bank","startDate":"05-12-2019","status":"Accepted"}]}}
				 * 
				 */
				JSONArray responseObject = accounts.getJSONObject("mandates").getJSONArray("mandateData");
				
				for (int respCntr = 0; respCntr < responseObject.length(); respCntr++) {
					
						JSONObject currentJsonObject = new JSONObject();

						currentJsonObject.put("umrn",responseObject.getJSONObject(respCntr).has("umrn")?responseObject.getJSONObject(respCntr).get("umrn"):"NA");
						currentJsonObject.put("amount",
								responseObject.getJSONObject(respCntr).has("amount")?responseObject.getJSONObject(respCntr).get("amount"):"NA");
						currentJsonObject.put("accountnumber",
								responseObject.getJSONObject(respCntr).has("accountnumber")?responseObject.getJSONObject(respCntr).get("accountnumber"):"NA");
						currentJsonObject.put("referenceNo", 
								responseObject.getJSONObject(respCntr).has("referenceNo")?responseObject.getJSONObject(respCntr).get("referenceNo"):"NA");
						currentJsonObject.put("bankName",
								responseObject.getJSONObject(respCntr).has("bankName")?responseObject.getJSONObject(respCntr).get("bankName"):"NA");
						currentJsonObject.put("mobileNumber",
								"+91-" + (responseObject.getJSONObject(respCntr).has("mobileNumber")?responseObject.getJSONObject(respCntr).get("mobileNumber"):"NA"));
						currentJsonObject.put("startDate",
								responseObject.getJSONObject(respCntr).has("startDate")?responseObject.getJSONObject(respCntr).get("startDate"):"NA");
						currentJsonObject.put("endDate",
								responseObject.getJSONObject(respCntr).has("endDate")?responseObject.getJSONObject(respCntr).get("endDate"):"NA");
						currentJsonObject.put("status",
								responseObject.getJSONObject(respCntr).has("status")?responseObject.getJSONObject(respCntr).get("status"):"NA");
						currentJsonObject.put("untilCancel",
								responseObject.getJSONObject(respCntr).has("untilCancel")?responseObject.getJSONObject(respCntr).get("untilCancel"):"NA");
						currentJsonObject.put("freq",
								responseObject.getJSONObject(respCntr).has("freq")?responseObject.getJSONObject(respCntr).get("freq"):"NA");
						currentJsonObject.put("txnType",
								responseObject.getJSONObject(respCntr).has("txnType")?responseObject.getJSONObject(respCntr).get("txnType"):"NA");
						currentJsonObject.put("benfNum",
								responseObject.getJSONObject(respCntr).has("orgName")?responseObject.getJSONObject(respCntr).get("orgName"):"NA");

						mandateData.put(currentJsonObject);
					
				}

				log.info("Manadate Process Data to pass {}", mandateData);
				
				// Database Changes START

				boolean mandateUpdated = databaseConService.updateMandateDetails(mobileNumber, mandateData.toString(),
						"enach-mandate");

				log.info("Manadate Data to database {}", mandateUpdated);

				if (mandateUpdated) {
					res.put("success", "true");
					res.put("mandate", mandateData.toString());
				} else {
					res.put("success", "false");
				}

				// Database Chagnes END

				out.println(EncryptDecrypt.encrypt(encryptKey, encryptKey, res.toString()));
				response.flushBuffer();

			}

		} catch (Exception e) {
			out.println("Exception in Mandate Data Servlet :: " + e.getMessage());
		} finally {
			// To Do
		}
	}

	private String getKeyValuefromJsonObject(JSONObject passJsonObject, String key) {
		String value = "";

		try {
			if (passJsonObject.has(key)) {
				value = passJsonObject.getString(key);
				return value;
			}
		} catch (Exception e) {
			log.error("Exception in Mandate Key Value Servlet :: ", e);
		}

		return value;
	}

	/*
	 * Method to get accounter number based on mobile number and dob
	 */
	private List<String> fetchAccountNumber(String mobileNumber, String dob, String key) {
		List<String> accountNumber = new ArrayList<>();
		try {
			// Need to add session ID here

			String cifDetailsData = databaseConService.getCifDetails(mobileNumber, key, "manageEnach");

			JSONArray jsonCifArray = new JSONArray(cifDetailsData);
			for (int obji = 0; obji < jsonCifArray.length(); obji++) {
				JSONObject currentObject = jsonCifArray.getJSONObject(obji);
				if (dob.equalsIgnoreCase("")) {
					accountNumber.add(currentObject.getString("Account_Number"));
				}
				if (currentObject.getString("DOB").equalsIgnoreCase(dob)) {
					accountNumber.add(currentObject.getString("Account_Number"));
				}
			}

		} catch (Exception e) {
			log.error("Exceptio in fetchAccountNumber", e);
		}
		return accountNumber;
	}
}
