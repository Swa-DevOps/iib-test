package com.iib.platform.services;

import org.json.JSONArray;
import org.json.JSONObject;
import org.osgi.annotation.versioning.ConsumerType;

@ConsumerType
public interface EmandateBankDataService {

	public JSONObject getIfscStatus(String ifsc, String repaymentBankName);
	
	public JSONObject getBillDeskIfscStatus(String ifsc, String repaymentBankName);

	public JSONObject getBillDeskRepaymentBankName(String repaymentBankCode);
	
	public JSONObject getRepaymentBankName(String repaymentBankCode);

	public Boolean getRepaymentBankCodeVerify(String bankCode);
	
	public Boolean getEnachRepaymentBankNameVerify(String bankNameORCode);
	
	public Boolean getCFDEnachRepaymentBankNameVerify(String bankCode);
	
	public JSONObject getCFDIfscStatus(String bankCode,String bankName );
	

	public JSONArray getBankList();

}
