package com.iib.platform.common.models;

/**
 * Banner Model
 *
 * @author Niket Goel
 *
 */

public class EmailServiceModel {

	private String enTxncode;
	private String enEmailid;
	private String enSubject;
	private String refId;
	private String mobNo;
	private String msg;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getTxncode() {
		return this.enTxncode;
	}

	public void setTxncode(String txncode) {
		this.enTxncode = txncode;
	}

	public String getEmailid() {
		return this.enEmailid;
	}

	public void setEmailid(String emailid) {
		this.enEmailid = emailid;
	}

	public String getSubject() {
		return this.enSubject;
	}

	public void setSubject(String subject) {
		this.enSubject = subject;
	}

	public String getRefId() {
		return this.refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public String getMobNo() {
		return this.mobNo;
	}

	public void setMobNo(String mobNo) {
		this.mobNo = mobNo;
	}

}
