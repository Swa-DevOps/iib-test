package com.iib.platform.services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

import com.iib.platform.cfdenach.models.CFDEnachSessionForm;
import com.iib.platform.common.objects.EnachSSO;

public interface DatabaseConnectionService {

	public boolean storeOtpDetails(String mobileNumber, String otp, String appId);

	public boolean storeOtpDetailsWithKey(String mobileNumber, String otp, String appId, String key);

	public boolean validateOtpDetails(String mobileNumber, String enteredOtp, String appId);

	public boolean validateOtpDetailsWithKey(String mobileNumber, String enteredOtp, String appId, String key,
			String encryptKey);

	public int checkCountOfOTP(String mobileNumber,String appId, String key, String action);
	
	public boolean getLoginFSSession(String key, String mobileNo, String encryptKey);

	public boolean getLoginDOBSession(String key, String mobileNo, String encryptKey);

	public boolean getEnachPLOTPSession(String key, String mobileNo, String encryptKey);

	public String getSetEnachPLLoginSession(String mobileNo, String session, String loanAPICompleteCheck);

	public String getSetEnachBillDeskSession(String mobileNo, String session, String billDeskvalue);

	public boolean updateMandateDetails(String mobileNumber, String mandateData, String appId);

	public String getMandateDetails(String mobileNumber, String appId);

	public boolean updateCifDetails(String mobileNumber, String cifDetails, String key);

	public String getCifDetails(String mobileNumber, String key, String encryptKey);

	public boolean updateNPCIDetails(String[] values);

	public boolean updateNPCIAPIDetails(String[] values);
	
	public String checkforapp(String digioId);
	
	public boolean getCreateEnachSession(String key, String mobileNo, String customerName, String custmomeremail);

	public boolean createNachDetails(String uniqueRequestId, String customername, String customerEmail,
			String customerRefNumber, String confirmAccountNo, String totalAmount, String referralCode,
			String totalInstallments, String aadharNo, String destinationBank, String destinationBankId,
			String customerAccountNo, String maximumamount, String transferFrequency, String mobileNo, String startDate,
			String endDate, String digistatus, String digireferenceid, String digiresponse, String uniqueKey, String tnc);

	public List<String> getForMandateCreate(String emandateNumber);

	public boolean updateFluxResult(String emandateNumber, String status, String errorMessage);

	public boolean updateDigioResult(String requestId, String eMandateId, String talismaId, String message);

	public List<String> getMandateIdforFailStatus();

	public List<String> getMandateIdforNullUrn();

	public List<String> getClientDetailsSMSEMAIL(String mandateId, String appid);

	public List<String> getFluxDetailsforNullUrn(String mandateId);

	public boolean updateURMNONENACH(String urmn, String mandateId);
	
	public boolean updateCFDCifDetails(String mobileNumber, String cifDetails, String key);
	
	public boolean getLoginCFDFSSession(String mKey, String mobileNo, String encryptKey);
	
	public String getCFDCifDetails(String mobileNumber, String key, String encryptKey);
	
	public boolean getCFDCreateEnachSession(String key, String mobileNo, String customerName, String custmomeremail);
	
	public boolean cfdcreateNachDetails( CFDEnachSessionForm cFDEnachSessionCreateForm);
	public boolean getCFDStatusUpdate(CFDEnachSessionForm cFDsresultupdate);
	/*public boolean cfdcreateNachDetails(String uniqueRequestId, String customername, String customerEmail,
			String customerRefNumber, String confirmAccountNo, String totalAmount, String referralCode,
			String totalInstallments, String aadharNo, String destinationBank, String destinationBankId,
			String customerAccountNo, String maximumamount, String transferFrequency, String mobileNo, String startDate,
			String endDate, String digistatus, String digireferenceid, String digiresponse, String uniqueKey);*/
	public CFDEnachSessionForm getCFDenachdetails(String sessionid, String allresult);
	public boolean updateFluxResultCFD(String emandateNumber, String status, String errorMessage);
	public List<EnachSSO> getCFDenachdetailsforExl(String query, String parametervalueofDate);	
	public boolean updateCFDNPCIAPIDetails(String[] values);
	public boolean updateCFDNPCIDetails(String[] values);
	public List<String> getCFDClientDetailsSMSEMAIL(String mandateId);
	
	public boolean setCFDplsession(String[] values);
	public List<String> getCFDplsession(String sessionId);
	public List<String> getCFDplsession(String sessionId, String allResult);
	public boolean updateCFDplsession(String sessionId, String billdeskresult, String status);
	
	
	public boolean setCFDenachsession(String[] values);
	public EnachSSO getCFDenachsession(String sessionId);
	public EnachSSO getCFDenachsession(String sessionId, String allResult);
	public boolean updateCFDenachsession(String sessionId, String enachresult, String status);
	
	
	public ResultSet getSelectedResult(String sql, Connection con);
	
	public Connection getConnectionforOutside();
	
	public boolean insertEnachCancelDatabase(String mobileNumber, String urmnumbers, String requestid, String crmId, String crmerror, String cancelApiresult);		

	
}
