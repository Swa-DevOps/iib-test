package com.iib.platform.services.impl;

import org.json.JSONArray;
import org.json.JSONObject;
import org.osgi.framework.Constants;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iib.platform.services.EmandateBankDataService;
import com.iib.platform.services.config.EmandateBankDataServiceConfig;

/**
 * E-Mandate Bank Data Service Implementation
 *
 * @author TechChefz (TCZ Consulting LLP)
 *
 */
@Component(immediate = true, service = { EmandateBankDataService.class }, enabled = true, property = {
		Constants.SERVICE_DESCRIPTION + "=E-Mandate Bank Data Service Implementation" })
@Designate(ocd = EmandateBankDataServiceConfig.class)
public class EmandateBankDataServiceImpl implements EmandateBankDataService {

	/* Logger */
	private static Logger log = LoggerFactory.getLogger(EmandateBankDataServiceImpl.class);
	private static final String SUCC = "success";
	private static final String TRUE = "true";
	private static final String FALSE = "false";
	
	String[] bankData = {};
	String[] frequencyBankData = {};
	String[] tempBankData = {};
	String[] billDeskbankData = {};

	@Activate()
	public void activate(EmandateBankDataServiceConfig config) {
		log.info("Activated EmandateBankDataServiceImpl");

		bankData = config.getBankCode();
		frequencyBankData = config.getFrequencyBankData();
		billDeskbankData=config.getBankCodeBillDesk();
	}

	@Override
	public JSONObject getIfscStatus(String ifsc, String repaymentBankName) {
		JSONObject jsonObj = new JSONObject();

		try {
			for (int i = 0; i < bankData.length; i++) {
				tempBankData = bankData[i].split(":");
				if ((repaymentBankName.equalsIgnoreCase(tempBankData[0])) && (ifsc.equalsIgnoreCase(tempBankData[1]))) {
					jsonObj.put(SUCC, TRUE);
					break;
				} else {
					jsonObj.put(SUCC, FALSE);
				}
			}
		} catch (Exception e) {
			log.error("Exception in getIfscCode Method :: " , e);
		}

		return jsonObj;
	}
	
	@Override
	public JSONObject getBillDeskIfscStatus(String ifsc, String repaymentBankName) {
		JSONObject jsonObj = new JSONObject();

		try {
			for (int i = 0; i < billDeskbankData.length; i++) {
				tempBankData = billDeskbankData[i].split(":");
				if ((repaymentBankName.equalsIgnoreCase(tempBankData[0])) && (ifsc.equalsIgnoreCase(tempBankData[1]))) {
					jsonObj.put(SUCC, TRUE);
					break;
				} else {
					jsonObj.put(SUCC, FALSE);
				}
			}
		} catch (Exception e) {
			log.error("Exception in getBillDeskIfscStatus Method :: " , e);
		}

		return jsonObj;
	}

	@Override
	public JSONObject getBillDeskRepaymentBankName(String repaymentBankCode) {
		JSONObject res = new JSONObject();

		try {
			for (int i = 0; i < billDeskbankData.length; i++) {
				tempBankData = billDeskbankData[i].split(":");
				if (repaymentBankCode.equalsIgnoreCase(tempBankData[1])) {
					res.put(SUCC, TRUE);
					res.put("bankName", tempBankData[0]);
					for (int j = 0; j < frequencyBankData.length; j++) {
						if (frequencyBankData[j].equalsIgnoreCase(tempBankData[0])) {
							res.put("frequencyType", "MNTH");
							break;
						} else {
							res.put("frequencyType", "ADHO");
						}
					}

					break;
				} else {
					res.put(SUCC, FALSE);
				}
			}
		} catch (Exception e) {
			log.error("Exception in getRepaymentBankName Method :: " , e);
		}
		return res;
	}
	
	@Override
	public JSONObject getRepaymentBankName(String repaymentBankCode) {
		JSONObject res = new JSONObject();

		try {
			for (int i = 0; i < bankData.length; i++) {
				tempBankData = bankData[i].split(":");
				if (repaymentBankCode.equalsIgnoreCase(tempBankData[1])) {
					res.put(SUCC,TRUE);
					res.put("bankName", tempBankData[0]);
					for (int j = 0; j < frequencyBankData.length; j++) {
						if (frequencyBankData[j].equalsIgnoreCase(tempBankData[0])) {
							res.put("frequencyType", "MNTH");
							break;
						} else {
							res.put("frequencyType", "ADHO");
						}
					}

					break;
				} else {
					res.put(SUCC,FALSE);
				}
			}
		} catch (Exception e) {
			log.error("Exception in getRepaymentBankName Method :: " , e);
		}
		return res;
	}

	@Override
	public Boolean getEnachRepaymentBankNameVerify(String bankCode) {
		Boolean status = false;
		log.error("Bank we passing to match {}",bankCode);
		try {
			for (int i = 0; i < billDeskbankData.length; i++) {
				
				tempBankData = billDeskbankData[i].split(":");
				if (bankCode.equalsIgnoreCase(tempBankData[1])) {
					status = true;
					break;
				} else {
					status = false;
				}
			}
		} catch (Exception e) {
			log.error("Exception in getEnachRepaymentBankNameVerify Method :: " , e);
		}
		return status;
	}
	
	@Override
	public Boolean getCFDEnachRepaymentBankNameVerify(String bankCode) {
		Boolean status = false;

		try {

			for (int i = 0; i < bankData.length; i++) {

				  tempBankData = bankData[i].split(":");
				if (bankCode.equalsIgnoreCase(tempBankData[0])) {
					status = true;
					break;
				} else {
					status = false;
				}
			}
		} catch (Exception e) {
			log.error("Exception in getCFDEnachRepaymentBankNameVerify Method :: " , e);
		}
		return status;
	}
	
	@Override
	public Boolean getRepaymentBankCodeVerify(String bankCode) {
		Boolean status = false;

		try {
			for (int i = 0; i < bankData.length; i++) {
				tempBankData = bankData[i].split(":");
				if (bankCode.equalsIgnoreCase(tempBankData[1])) {
					status = true;
					break;
				} else {
					status = false;
				}
			}
		} catch (Exception e) {
			log.error("Exception in getRepaymentBankCodeVerify Method :: " , e);
		}
		return status;
	}

	@Override
	public JSONArray getBankList() {
		JSONArray bankList = new JSONArray();
		try {
			int i;

			for (i = 0; i < bankData.length; i++) {
				tempBankData = bankData[i].split(":");
				bankList.put(tempBankData[0]);
			}

		} catch (Exception e) {
			log.error("Exception in getBankList Method :: " , e);
		}

		return bankList;
	}

	@Override
	public JSONObject getCFDIfscStatus(String ifsc, String repaymentBankName) {
		JSONObject jsonObj = new JSONObject();

		try {
			for (int i = 0; i < bankData.length; i++) {
				tempBankData = bankData[i].split(":");
				if ((repaymentBankName.equalsIgnoreCase(tempBankData[0])) && (ifsc.equalsIgnoreCase(tempBankData[1]))) {
					jsonObj.put(SUCC, TRUE);
					break;
				} else {
					jsonObj.put(SUCC, FALSE);
				}
			}
		} catch (Exception e) {
			log.error("Exception in getCFDIfscStatus Method :: " , e);
		}

		return jsonObj;
	}
	
	
	@Deactivate
	protected void deactivate(ComponentContext ctx) throws Exception {
		log.info("Deactivated EmandateBankDataServiceImpl");
	}
}
