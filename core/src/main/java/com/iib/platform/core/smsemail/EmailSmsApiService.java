package com.iib.platform.core.smsemail;

public interface EmailSmsApiService {

	public String emailServiceCall(String refid, String txncode, String emailid, String subject, String otp,
			int tempType);

	public String emailServiceCallWithExtraParam(String txncode, String emailid, String subject, String name,
			String startDate, String endDate, String frequency, String digireferenceid, String maximumAmount,
			int temptype);

	public String smsServiceCall(String refid, String txncode, String mobNo, String msg, int smsType);
	
	public String smsServiceCallCancel(String refid, String txncode, String mobNo, String msg, int smsType, String accCode);
		
}
