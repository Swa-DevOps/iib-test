package com.iib.platform.core.smsemail.impl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iib.platform.core.smsemail.EmailSmsApiService;
import com.iib.platform.common.models.EmailServiceModel;
import com.iib.platform.services.config.EmailAPIServiceConfig;
import com.iib.platform.common.utils.NumberToWordsConverter;

@Component(immediate = true, service = { EmailSmsApiService.class }, enabled = true, property = {
		Constants.SERVICE_DESCRIPTION + "=" + "Rest EMAIL SMS API Service" })

@Designate(ocd = EmailAPIServiceConfig.class)
public class EmailSmsApiServiceImpl implements EmailSmsApiService {

	private static final String CONTENT_TYPE_ID = "Content-Type";
	private static final String CONTENT_TYPE_VALUE = "application/xml;charset=utf-8";
	@Reference
	NumberToWordsConverter numbertoword;

	private String clientSecretKey;
	private String clientSecretValue;
	private String clientIdKey;
	private String clientIdValue;
	private String emailServiceUrl;
	private String smsServiceUrl;
	private String smsServiceChannelId;
	private String smsServiceKey;
	private String smsECancelServiceChannelId;
	private String smsEcancelServiceKey;
	private String emailServiceChannelId;
	private String emailServiceKey;

	EmailServiceModel emailService = new EmailServiceModel();

	@Activate
	protected void activate(EmailAPIServiceConfig config) {
		log.info("SOAP API Service has been activated");

		clientSecretKey = config.getClientSecretKey();
		clientSecretValue = config.getClientSecretValue();
		clientIdKey = config.getClientID();
		clientIdValue = config.getClientIDValue();
		emailServiceUrl = config.getEmailServiceUrl();
		smsServiceUrl = config.getSmsServiceUrl();
		smsServiceChannelId = config.getSmsServiceChannelId();
		smsServiceKey = config.getSmsServiceKey();
		emailServiceChannelId = config.getEmailServiceChannelId();
		emailServiceKey = config.getEmailServiceKey();
		smsECancelServiceChannelId = config.getSmsECancelServiceChannelId();
		smsEcancelServiceKey = config.getSmsEcancelServiceKey();
		// ALL API End POints

	}

	private static Logger log = LoggerFactory.getLogger(EmailSmsApiServiceImpl.class);

	@Override
	public String emailServiceCall(String refid, String txncode, String emailid, String subject, String otp,
			int temptype) {

		emailService.setTxncode(txncode);
		emailService.setEmailid(emailid);
		emailService.setRefId(refid);
		emailService.setSubject(subject);
		HttpURLConnection con = null;
		StringBuilder response = null;
		try {
			String url = emailServiceUrl;

			URL obj = new URL(url);
			con = (HttpURLConnection) obj.openConnection();

			con.setRequestMethod("POST");
			con.setRequestProperty(CONTENT_TYPE_ID, CONTENT_TYPE_VALUE);
			if (!clientSecretKey.equals(StringUtils.EMPTY)) {
				con.setRequestProperty(clientSecretKey, clientSecretValue);
				con.setRequestProperty(clientIdKey, clientIdValue);
			}

			String emailTemplate = StringUtils.EMPTY;

			StringBuilder urlParameters = new StringBuilder();
			urlParameters.append("<Root>").append("<ChnlId>" + emailServiceChannelId + "</ChnlId>")
					.append("<Key>" + emailServiceKey + "</Key>")
					.append("<Row>" + " <RefId>" + emailService.getRefId() + "</RefId>")
					.append("<Txncode>" + emailService.getTxncode() + "</Txncode>")
					.append("<Emailid>" + emailService.getRefId() + "</Emailid>")
					.append("<Subject>" + emailService.getSubject() + "</Subject>").append("<Msg><![CDATA[")
					.append(emailTemplate).append("]]></Msg>").append("<Attachment></Attachment>").append(" </Row>")
					.append("</Root>");

			log.info("XML data {}", urlParameters);
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(urlParameters.toString());
			wr.flush();
			wr.close();
			log.info("DataOutputStream successfully closed");
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			response = new StringBuilder();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			log.info("BufferedReader successfully closed");
			log.info("Email service response :: {} ", response);
		} catch (IOException e) {
			log.error("IOException occured in EMAIL API SERVICE IMPL {} ", e);
		}

		if (response != null)
			return response.toString();
		else
			return null;

	}

	@Override
	public String emailServiceCallWithExtraParam(String txncode, String emailid, String subject, String name,
			String startDate, String endDate, String frequency, String digireferenceid, String maximumAmount,
			int temptype) {

		emailService.setTxncode(txncode);
		emailService.setEmailid(emailid);
		emailService.setRefId(digireferenceid);
		emailService.setSubject(subject);
		HttpURLConnection con = null;
		StringBuilder response = null;
		try {
			String url = emailServiceUrl;

			URL obj = new URL(url);
			con = (HttpURLConnection) obj.openConnection();
			log.info("EAMIL SERVICE CALL--{} ", url);
			con.setRequestMethod("POST");
			con.setRequestProperty(CONTENT_TYPE_ID, CONTENT_TYPE_VALUE);
			if (!clientSecretKey.equals(StringUtils.EMPTY)) {
				con.setRequestProperty(clientSecretKey, clientSecretValue);
				con.setRequestProperty(clientIdKey, clientIdValue);
			}

			String emailTemplate = StringUtils.EMPTY;
			switch (temptype) {

			case 1:
				emailTemplate = emandateCreatedSuccessfully(name, startDate, endDate, frequency, digireferenceid,
						maximumAmount);
				break;
			case 2:
				emailTemplate = emandateRequestFailed(digireferenceid);
				break;
			case 3:
				emailTemplate = emandateRecrdingFailed(digireferenceid);
				break;
			case 4:
				emailTemplate = emandateTransactionSuccessRecorded(digireferenceid);
				break;
			default:
				break;

			}
			StringBuilder urlParameters = new StringBuilder();
			urlParameters.append("<Root>").append("<ChnlId>" + emailServiceChannelId + "</ChnlId>")
					.append("<Key>" + emailServiceKey + "</Key>")
					.append("<Row>" + " <RefId>" + emailService.getRefId() + "</RefId>")
					.append("<Txncode>" + emailService.getTxncode() + "</Txncode>")
					.append("<Emailid>" + emailService.getEmailid() + "</Emailid>")
					.append("<Subject>" + emailService.getSubject() + "</Subject>").append("<Msg><![CDATA[")
					.append(emailTemplate).append("]]></Msg>").append("<Attachment></Attachment>").append(" </Row>")
					.append("</Root>");

			log.info("XML data {}", urlParameters);
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(urlParameters.toString());
			wr.flush();
			wr.close();
			log.info("DataOutputStream successfully closed");
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			response = new StringBuilder();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			log.info("BufferedReader successfully closed");
			log.info("Email service response {}", response);
		} catch (IOException e) {
			log.error("IOException occured in EMAIL API SERVICE IMPL {} ", e);
		}
		if (response != null)
			return response.toString();
		else
			return null;

	}
	

	@Override
	public String smsServiceCallCancel(String refid, String txncode, String mobNo, String msg, int smsType, String accCode ) {
		emailService.setRefId(refid);
		emailService.setSubject(msg);
		emailService.setMobNo(mobNo);
		HttpURLConnection con = null;
		StringBuilder response = null;
		emailService.setMobNo(mobNo);
		emailService.setMsg(msg);
		try {
			String url = smsServiceUrl;
			URL obj = new URL(url);
			con = (HttpURLConnection) obj.openConnection();
			log.info("SMS SERVICE CALL-- {}", url);
			con.setRequestMethod("POST");
			con.setRequestProperty(CONTENT_TYPE_ID, CONTENT_TYPE_VALUE);
			if (!clientSecretKey.equals(StringUtils.EMPTY)) {
				con.setRequestProperty(clientSecretKey, clientSecretValue);
				con.setRequestProperty(clientIdKey, clientIdValue);
			}
			String smsTemplate = StringUtils.EMPTY;
			switch (smsType) {

			case 5: 
				smsTemplate = cancelSuccess(refid, msg, accCode);
				break;
			default:
				break;
			}

			String mobileNumber = emailService.getMobNo();
			mobileNumber = "91" + mobileNumber.substring(mobileNumber.length() - 10);
			emailService.setMobNo(mobileNumber);

			if (mobileNumber.indexOf(')') > -1 && mobileNumber.indexOf('(') > -1
					&& mobileNumber.indexOf(')') > mobileNumber.indexOf('(')) {
				mobileNumber = "91" + mobileNumber.substring(mobileNumber.length() - 10);
				emailService.setMobNo(mobileNumber);
			}

			String urlParameters = "<Root>" + "  <ChnlId>" + smsECancelServiceChannelId + "</ChnlId>" + " <Key>"
					+ smsEcancelServiceKey + "</Key>" + " <Row>" + " <RefId>" + emailService.getRefId() + "</RefId>"
					+ "<MobNo>" + emailService.getMobNo() + "</MobNo>" + "<Msg>" + smsTemplate + "</Msg></Row></Root>";
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();
			log.info("SMS service request {}", urlParameters);
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			response = new StringBuilder();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			log.info("SMS service response {}", response);

		} catch (IOException e) {
			log.error("IOException occured in SMS API SERVICE IMPL {} ", e);
		}
		if (response != null)
			return response.toString();
		else
			return null;

	}


	@Override
	public String smsServiceCall(String refid, String txncode, String mobNo, String msg, int smsType) {
		emailService.setRefId(refid);
		emailService.setSubject(msg);
		emailService.setMobNo(mobNo);
		HttpURLConnection con = null;
		StringBuilder response = null;
		emailService.setMobNo(mobNo);
		emailService.setMsg(msg);
		try {
			String url = smsServiceUrl;
			URL obj = new URL(url);
			con = (HttpURLConnection) obj.openConnection();
			log.info("SMS SERVICE CALL-- {}", url);
			con.setRequestMethod("POST");
			con.setRequestProperty(CONTENT_TYPE_ID, CONTENT_TYPE_VALUE);
			if (!clientSecretKey.equals(StringUtils.EMPTY)) {
				con.setRequestProperty(clientSecretKey, clientSecretValue);
				con.setRequestProperty(clientIdKey, clientIdValue);
			}
			String smsTemplate = StringUtils.EMPTY;
			switch (smsType) {

			case 1:
				smsTemplate = digioSuccessMandate(refid);
				break;
			case 2:
				smsTemplate = digioFailedMandate(refid);
				break;
			case 3:
				smsTemplate = urmSuccessGenerate(refid, msg);
				break;
			case 4:
				smsTemplate = npciFaliure(refid, msg);
				break;
			default:
				break;
			}

			String mobileNumber = emailService.getMobNo();
			mobileNumber = "91" + mobileNumber.substring(mobileNumber.length() - 10);
			emailService.setMobNo(mobileNumber);

			if (mobileNumber.indexOf(')') > -1 && mobileNumber.indexOf('(') > -1
					&& mobileNumber.indexOf(')') > mobileNumber.indexOf('(')) {
				mobileNumber = "91" + mobileNumber.substring(mobileNumber.length() - 10);
				emailService.setMobNo(mobileNumber);
			}

			String urlParameters = "<Root>" + "  <ChnlId>" + smsServiceChannelId + "</ChnlId>" + " <Key>"
					+ smsServiceKey + "</Key>" + " <Row>" + " <RefId>" + emailService.getRefId() + "</RefId>"
					+ "<MobNo>" + emailService.getMobNo() + "</MobNo>" + "<Msg>" + smsTemplate + "</Msg></Row></Root>";
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();
			log.info("SMS service request {}", urlParameters);
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			response = new StringBuilder();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			log.info("SMS service response {}", response);

		} catch (IOException e) {
			log.error("IOException occured in SMS API SERVICE IMPL {} ", e);
		}
		if (response != null)
			return response.toString();
		else
			return null;

	}

	public String emandateTransactionSuccessRecorded(String digireferenceid) {

		String data = /*
						 * "<body style=\"margin:0; padding:0;\">" +
						 * "	<table width=\"700\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style=\"border:1px solid #cccccc; background:#FFF6CD; \">"
						 * + "" + "    <tr>" +
						 * "      <td align=\"center\" valign=\"top\" style=\"padding: 0;\">" +
						 * "		  <table width=\"700\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class=\"marginnone\">"
						 * + "	" + "	<tr>" +
						 * "    <td align=\"center\" valign=\"bottom\" bgcolor=\"#FFF6CD\" class=\"responsive-image\" style=\"padding:0; font-size:1px;\">"
						 * +
						 * "    <table width=\"100%\"  border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"marginnone\">"
						 * + "	<tr>" +
						 * "		<td  align=\"center\" valign=\"bottom\" bgcolor=\"#FFF6CD\" class=\"responsive-image\" style=\"padding:0; font-size:1px;\">"
						 * +
						 * "		<img src=\"images/IndusIndBank_Grouping_01_01.jpg\" width=\"117\" height=\"59\" border=\"0\" style=\"display:block;\" alt=\"IndusInd Bank\" /></td>"
						 * +
						 * "		<td  align=\"center\" valign=\"bottom\" bgcolor=\"#FFF6CD\" class=\"responsive-image\" style=\"padding:0; font-size:1px;\">"
						 * +
						 * "			<img src=\"images/IndusIndBank_Grouping_01_02.jpg\" width=\"383\" height=\"59\" border=\"0\" style=\"display:block;\" alt=\"IndusInd Bank\" /></td>"
						 * +
						 * "		<td  align=\"center\" valign=\"bottom\" bgcolor=\"#FFF6CD\" class=\"responsive-image\" style=\"padding:0; font-size:1px;\"><a href=\"http://www.indusind.com/\" target=\"_blank\"><img src=\"images/IndusIndBank_Grouping_01_03.jpg\" width=\"200\" height=\"59\" border=\"0\" style=\"display:block;\" alt=\"IndusInd Bank\" /></a></td>"
						 * + "	</tr>" + "</table></td>" + "  </tr>" + " <tr>" +
						 * "    <td align=\"center\" valign=\"bottom\" bgcolor=\"#FFF6CD\" class=\"responsive-image\" style=\"padding:0; font-size:1px;\"><span class=\"responsive-image\" style=\"padding:0; font-size:1px;\"><img src=\"images/IndusIndBank_Grouping_03.jpg\" width=\"700\" height=\"318\" alt=\"\" border=\"0\" style=\"display:block;\" /></span></td>"
						 * + "   </tr>" +
						 */
				"    <tr>"
						+ "        <td align=\"left\" valign=\"top\" style=\"padding:0 100px 0px 100px; font-family:Arial, Helvetica, sans-serif; font-size:14px; color:#333333; line-height:22px;\" class=\"padding_auto\">"
						+ "		<p>Dear Customer,<br></p>" + "		<p>Congratulations!<br></p>"
						+ "<p>We wish to inform you that your E-Mandate request number '" + digireferenceid
						+ "' has been successfully recorded and forwarded to the selected bank. Your request will get processed within 7 working days. We thank you for choosing IndusInd Bank and look forward to serving you again soon.<br></p>"
						+ "<p><b>Assuring you of our best services always.</b><br></p>" + "Warm regards,<br>"
						+ "Team IndusInd Bank" + "</td>" + "</tr>"
						+ "<tr>"/*
								 * +
								 * "    <td align=\"center\" valign=\"bottom\" bgcolor=\"#FFF6CD\" class=\"responsive-image\" style=\"padding:0; font-size:1px; \"><span class=\"responsive-image\" style=\"padding:0; font-size:1px;\"><img src=\"images/IndusIndBank_Grouping_04.jpg\" width=\"700\" height=\"36\" alt=\"\" border=\"0\" style=\"display:block;\" /></span></td>"
								 * + "   </tr>" + "</table>" + "</td>" + "</tr>" + "</body>"
								 */;
		return data;

	}

	public String emandateRecrdingFailed(String digireferenceid) {
		String data = /*
						 * "<body style=\"margin:0; padding:0;\">" +
						 * "	<table width=\"700\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style=\"border:1px solid #cccccc; background:#FFF6CD; \">"
						 * + "" + "    <tr>" +
						 * "      <td align=\"center\" valign=\"top\" style=\"padding: 0;\">" +
						 * "		  <table width=\"700\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class=\"marginnone\">"
						 * + "	" + "	<tr>" +
						 * "    <td align=\"center\" valign=\"bottom\" bgcolor=\"#FFF6CD\" class=\"responsive-image\" style=\"padding:0; font-size:1px;\">"
						 * +
						 * "    <table width=\"100%\"  border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"marginnone\">"
						 * + "	<tr>" +
						 * "		<td  align=\"center\" valign=\"bottom\" bgcolor=\"#FFF6CD\" class=\"responsive-image\" style=\"padding:0; font-size:1px;\">"
						 * +
						 * "		<img src=\"images/IndusIndBank_Grouping_01_01.jpg\" width=\"117\" height=\"59\" border=\"0\" style=\"display:block;\" alt=\"IndusInd Bank\" /></td>"
						 * +
						 * "		<td  align=\"center\" valign=\"bottom\" bgcolor=\"#FFF6CD\" class=\"responsive-image\" style=\"padding:0; font-size:1px;\">"
						 * +
						 * "			<img src=\"images/IndusIndBank_Grouping_01_02.jpg\" width=\"383\" height=\"59\" border=\"0\" style=\"display:block;\" alt=\"IndusInd Bank\" /></td>"
						 * +
						 * "		<td  align=\"center\" valign=\"bottom\" bgcolor=\"#FFF6CD\" class=\"responsive-image\" style=\"padding:0; font-size:1px;\"><a href=\"http://www.indusind.com/\" target=\"_blank\"><img src=\"images/IndusIndBank_Grouping_01_03.jpg\" width=\"200\" height=\"59\" border=\"0\" style=\"display:block;\" alt=\"IndusInd Bank\" /></a></td>"
						 * + "	</tr>" + "</table></td>" + "  </tr>" + " <tr>" +
						 * "    <td align=\"center\" valign=\"bottom\" bgcolor=\"#FFF6CD\" class=\"responsive-image\" style=\"padding:0; font-size:1px;\"><span class=\"responsive-image\" style=\"padding:0; font-size:1px;\"><img src=\"images/IndusIndBank_Grouping_03.jpg\" width=\"700\" height=\"318\" alt=\"\" border=\"0\" style=\"display:block;\" /></span></td>"
						 * + "   </tr>" +
						 */
				"    <tr>"
						+ "        <td align=\"left\" valign=\"top\" style=\"padding:0 60px 0px 100px; font-family:Arial, Helvetica, sans-serif; font-size:14px; color:#333333; line-height:22px;\" class=\"padding_auto\">"
						+ "		<p>Dear Customer,<br></p>"
						+ "<p>We regret to inform you that your E-Mandate request number '" + digireferenceid
						+ "' could not be processed. We request you to kindly retry.<br>"
						+ "We thank you for choosing IndusInd Bank and look forward to serving you again soon.<br></p>"
						+ "" + "<p><b>Assuring you of our best services always.</b><br></p>" + "" + "Warm regards,<br>"
						+ "Team IndusInd Bank" + "</td>" + "</tr>"
						+ "<tr>"/*
								 * +
								 * "    <td align=\"center\" valign=\"bottom\" bgcolor=\"#FFF6CD\" class=\"responsive-image\" style=\"padding:0; font-size:1px; \"><span class=\"responsive-image\" style=\"padding:0; font-size:1px;\"><img src=\"images/IndusIndBank_Grouping_04.jpg\" width=\"700\" height=\"36\" alt=\"\" border=\"0\" style=\"display:block;\" /></span></td>"
								 * + "   </tr>" + "</table>" + "</td>" + "</tr>" + "</body>"
								 */;
		return data;

	}

	public String emandateRequestFailed(String digireferenceid) {
		String data = /*
						 * "<body style=\"margin:0; padding:0;\">" +
						 * "	<table width=\"700\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style=\"border:1px solid #cccccc; background:#FFF6CD; \">"
						 * + "" + "    <tr>" +
						 * "      <td align=\"center\" valign=\"top\" style=\"padding: 0;\">" +
						 * "		  <table width=\"700\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class=\"marginnone\">"
						 * + "	" + "	<tr>" +
						 * "    <td align=\"center\" valign=\"bottom\" bgcolor=\"#FFF6CD\" class=\"responsive-image\" style=\"padding:0; font-size:1px;\">"
						 * +
						 * "    <table width=\"100%\"  border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"marginnone\">"
						 * + "	<tr>" +
						 * "		<td  align=\"center\" valign=\"bottom\" bgcolor=\"#FFF6CD\" class=\"responsive-image\" style=\"padding:0; font-size:1px;\">"
						 * +
						 * "		<img src=\"images/IndusIndBank_Grouping_01_01.jpg\" width=\"117\" height=\"59\" border=\"0\" style=\"display:block;\" alt=\"IndusInd Bank\" /></td>"
						 * +
						 * "		<td  align=\"center\" valign=\"bottom\" bgcolor=\"#FFF6CD\" class=\"responsive-image\" style=\"padding:0; font-size:1px;\">"
						 * +
						 * "			<img src=\"images/IndusIndBank_Grouping_01_02.jpg\" width=\"383\" height=\"59\" border=\"0\" style=\"display:block;\" alt=\"IndusInd Bank\" /></td>"
						 * +
						 * "		<td  align=\"center\" valign=\"bottom\" bgcolor=\"#FFF6CD\" class=\"responsive-image\" style=\"padding:0; font-size:1px;\"><a href=\"http://www.indusind.com/\" target=\"_blank\"><img src=\"images/IndusIndBank_Grouping_01_03.jpg\" width=\"200\" height=\"59\" border=\"0\" style=\"display:block;\" alt=\"IndusInd Bank\" /></a></td>"
						 * + "	</tr>" + "</table></td>" + "  </tr>" + " <tr>" +
						 * "    <td align=\"center\" valign=\"bottom\" bgcolor=\"#FFF6CD\" class=\"responsive-image\" style=\"padding:0; font-size:1px;\"><span class=\"responsive-image\" style=\"padding:0; font-size:1px;\"><img src=\"images/IndusIndBank_Grouping_03.jpg\" width=\"700\" height=\"329\" alt=\"\" border=\"0\" style=\"display:block;\" /></span></td>"
						 * + "   </tr>" +
						 */
				"    <tr>"
						+ "        <td align=\"left\" valign=\"top\" style=\"padding:0 60px 0px 60px; font-family:Arial, Helvetica, sans-serif; font-size:14px; color:#333333; line-height:22px;\" class=\"padding_auto\">"
						+ "		<p>Dear Customer,<br></p>"
						+ "<p>We regret to inform you that your E-Mandate request number '" + digireferenceid
						+ "' could not be processed. We request you to kindly retry.<br></p>"
						+ "<p>We thank you for choosing IndusInd Bank and look forward to serving you again soon.<br></p>"
						+ "<p><b>Assuring you of our best services always.</b><br></p>" + "Warm regards,<br>"
						+ "Team IndusInd Bank" + "</td>" + "</tr>" + " <tr>"
						+ "<td align=\"center\" height=\"20\" style=\" \"><spacer type=\"block\" height=\"20\"></td>"
						+ "</tr>" + " <tr>"
						+ "        <td align=\"center\" valign=\"top\" style=\"padding:0 60px 0px 60px; font-family:Arial, Helvetica, sans-serif; font-size:14px; color:#333333; line-height:22px;\" class=\"padding_auto\">"
						+ "		For clarifications please write to <a href=\"mailto:corporatecare@indusind.com\" style=\"color:#333333; text decoration:none;\"><font style=\"color: #333333; text-decoration: none;\">reachus@indusind.com</a></font> or call 1860 500 5004"
						+ "</td>" + "</tr>"/*
											 * + " " + "<tr>" +
											 * "    <td align=\"center\" valign=\"bottom\" bgcolor=\"#FFF6CD\" class=\"responsive-image\" style=\"padding:0; font-size:1px; \"><span class=\"responsive-image\" style=\"padding:0; font-size:1px;\"><img src=\"images/IndusIndBank_Grouping_04.jpg\" width=\"700\" height=\"79\" alt=\"\" border=\"0\" style=\"display:block;\" /></span></td>"
											 * + "   </tr>" + "</table>" + "</td>" + "</tr>" + "</body>"
											 */;
		return data;

	}

	public String emandateCreatedSuccessfully(String name, String startDate, String endDate, String frequency,
			String digireferenceid, String maximumAmount) {
		/*
		 * String data ="<body style=\"margin:0; padding:0;\">" +
		 * "	<table width=\"700\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style=\"border:1px solid #cccccc; background:#FFF6CD; \">"
		 * + "" + "    <tr>" +
		 * "      <td align=\"center\" valign=\"top\" style=\"padding: 0;\">" +
		 * "		  <table width=\"700\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class=\"marginnone\">"
		 * + "	" + "	<tr>" +
		 * "    <td align=\"center\" valign=\"bottom\" bgcolor=\"#FFF6CD\" class=\"responsive-image\" style=\"padding:0; font-size:1px;\">"
		 * +
		 * "    <table width=\"100%\"  border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"marginnone\">"
		 * + "	<tr>" +
		 * "		<td  align=\"center\" valign=\"bottom\" bgcolor=\"#FFF6CD\" class=\"responsive-image\" style=\"padding:0; font-size:1px;\">"
		 * +
		 * "		<img src=\"images/IndusIndBank_Grouping_01_01.jpg\" width=\"117\" height=\"59\" border=\"0\" style=\"display:block;\" alt=\"IndusInd Bank\" /></td>"
		 * +
		 * "		<td  align=\"center\" valign=\"bottom\" bgcolor=\"#FFF6CD\" class=\"responsive-image\" style=\"padding:0; font-size:1px;\">"
		 * +
		 * "			<img src=\"images/IndusIndBank_Grouping_01_02.jpg\" width=\"383\" height=\"59\" border=\"0\" style=\"display:block;\" alt=\"IndusInd Bank\" /></td>"
		 * +
		 * "		<td  align=\"center\" valign=\"bottom\" bgcolor=\"#FFF6CD\" class=\"responsive-image\" style=\"padding:0; font-size:1px;\"><a href=\"http://www.indusind.com/\" target=\"_blank\"><img src=\"images/IndusIndBank_Grouping_01_03.jpg\" width=\"200\" height=\"59\" border=\"0\" style=\"display:block;\" alt=\"IndusInd Bank\" /></a></td>"
		 * + "	</tr>" + "</table></td>" + "  </tr>" + " <tr>" +
		 * "    <td align=\"center\" valign=\"bottom\" bgcolor=\"#FFF6CD\" class=\"responsive-image\" style=\"padding:0; font-size:1px;\"><span class=\"responsive-image\" style=\"padding:0; font-size:1px;\"><img src=\"images/IndusIndBank_Grouping_03.jpg\" width=\"700\" height=\"315\" alt=\"\" border=\"0\" style=\"display:block;\" /></span></td>"
		 * + "   </tr>" + "    <tr>" +
		 * "        <td align=\"left\" valign=\"top\" style=\"padding:0 60px 0px 60px; font-family:Arial, Helvetica, sans-serif; font-size:14px; color:#333333; line-height:22px;\" class=\"padding_auto\">"
		 * + "		<p>Dear Customer,<br></p>" +
		 * "Congratulations!  We wish to inform you that your E-Mandate has been successfully created. Please find below the details pertaining to your E-Mandate."
		 * + "</td>" + "      </tr>" + " <tr>" +
		 * "<td align=\"center\" height=\"20\" style=\" \"><spacer type=\"block\" height=\"20\"></td>"
		 * + "</tr>" + "" + "" + "<tr>" +
		 * "         <td align=\"left\" valign=\"top\" style=\"padding: 0 0 0 0;\" class=\"top-space padding_auto\">"
		 * +
		 * "			 <table width=\"50%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"left\"style=\"  margin-top:0px; margin-left:60px\" >"
		 * + "  <tbody>" + "    <tr>" +
		 * "      <td align=\"left\" valign=\"middle\" style=\"padding: 5px 10px; color: #333333; font-size:13px; line-height:20px;   font-family: Arial, 'Times New Roman', sans-serif; border:1px solid #3F3917;  border-top: 1px solid #F7941D; border-left: 1px solid #F7941D;\"  bgcolor=\"#FFEC5F\">Mandate issued to</td>"
		 * +
		 * "      <td align=\"left\" valign=\"middle\" style=\"padding: 5px 10px; color: #333333; font-size:13px; line-height:16px;  font-family: Arial, 'Times New Roman', sans-serif; border:1px solid #3F3917; border-top: 1px solid #F7941D; border-right: 1px solid #F7941D;\" bgcolor=\"#FFEC5F\">"
		 * +name+"</td>" + "     " + "    </tr>" + "    <tr>" +
		 * "      <td bgcolor=\"#FFEC5F\" align=\"left\" valign=\"middle\" style=\"padding: 5px 1px 3px 10px; color: #333333; font-size:13px; line-height:20px;  font-family: Arial, 'Times New Roman', sans-serif; border:1px solid #3F3917; border-left: 1px solid #F7941D; \" width=\"35% \">Start date</td>"
		 * +
		 * "		<td bgcolor=\"#FFEC5F\" align=\"left\" valign=\"middle\" style=\"padding: 5px 10px; color: #333333; font-size:13px; line-height:16px;  font-family: Arial, 'Times New Roman', sans-serif; border:1px solid #3F3917; border-right: 1px solid #F7941D;\" width=\"45%\">"
		 * +startDate+"</td>" + "    </tr>" + "	   <tr>" +
		 * "      <td bgcolor=\"#FFEC5F\" align=\"left\" valign=\"middle\" style=\"padding: 5px 1px 3px 10px; color: #333333; font-size:13px; line-height:20px;  font-family: Arial, 'Times New Roman', sans-serif; border:1px solid #3F3917; border-left: 1px solid #F7941D; \" width=\"35%\">End date"
		 * + "</td>" +
		 * "		<td bgcolor=\"#FFEC5F\" align=\"left\" valign=\"middle\" style=\"padding: 5px 10px; color: #333333; font-size:13px; line-height:16px;  font-family: Arial, 'Times New Roman', sans-serif; border:1px solid #3F3917; border-right: 1px solid #F7941D;\" width=\"45%\">"
		 * +endDate+"</td>" + "    </tr>" + "	" + "	<tr>" +
		 * "      <td bgcolor=\"#FFEC5F\" align=\"left\" valign=\"middle\" style=\"padding: 5px 1px 3px 10px; color: #333333; font-size:13px; line-height:20px;  font-family: Arial, 'Times New Roman', sans-serif; border:1px solid #3F3917; border-left: 1px solid #F7941D; \" width=\"35%\">Frequency "
		 * + "</td>" +
		 * "		<td bgcolor=\"#FFEC5F\" align=\"left\" valign=\"middle\" style=\"padding: 5px 10px; color: #333333; font-size:13px; line-height:16px;  font-family: Arial, 'Times New Roman', sans-serif; border:1px solid #3F3917; border-right: 1px solid #F7941D;\" width=\"45%\">"
		 * +frequency+"" + "</td>" + "    </tr>" + "	<tr>" +
		 * "      <td bgcolor=\"#FFEC5F\" align=\"left\" valign=\"middle\" style=\"padding: 5px 1px 3px 10px; color: #333333; font-size:13px; line-height:20px;  font-family: Arial, 'Times New Roman', sans-serif; border:1px solid #3F3917; border-left: 1px solid #F7941D; \" width=\"35%\">Amount in figures (INR)"
		 * + "</td>" +
		 * "		<td bgcolor=\"#FFEC5F\" align=\"left\" valign=\"middle\" style=\"padding: 5px 10px; color: #333333; font-size:13px; line-height:16px;  font-family: Arial, 'Times New Roman', sans-serif; border:1px solid #3F3917; border-right: 1px solid #F7941D;\" width=\"45%\">"
		 * + "Rs." + maximumAmount +"" + "    </tr>" + "	<tr>" +
		 * "      <td bgcolor=\"#FFEC5F\" align=\"left\" valign=\"middle\" style=\"padding: 5px 1px 3px 10px; color: #333333; font-size:13px; line-height:20px;  font-family: Arial, 'Times New Roman', sans-serif; border:1px solid #3F3917; border-left: 1px solid #F7941D; \" width=\"35%\">Amount in Words "
		 * + "</td>" +
		 * "		<td bgcolor=\"#FFEC5F\" align=\"left\" valign=\"middle\" style=\"padding: 5px 10px; color: #333333; font-size:13px; line-height:16px;  font-family: Arial, 'Times New Roman', sans-serif; border:1px solid #3F3917; border-right: 1px solid #F7941D;\" width=\"45%\">"
		 * + "Rupees " + numbertoword.convert(Integer.parseInt(maximumAmount)) + "</td>"
		 * + "    </tr>" + "	<tr>" +
		 * "      <td bgcolor=\"#FFEC5F\" align=\"left\" valign=\"middle\" style=\"padding: 5px 1px 3px 10px; color: #333333; font-size:13px; line-height:20px;  font-family: Arial, 'Times New Roman', sans-serif; border:1px solid #3F3917; border-left: 1px solid #F7941D; border-bottom: 1px solid #F7941D;\" width=\"35%\">Purpose of mandate"
		 * + "</td>" +
		 * "		<td bgcolor=\"#FFEC5F\" align=\"left\" valign=\"middle\" style=\"padding: 5px 10px; color: #333333; font-size:13px; line-height:16px;  font-family: Arial, 'Times New Roman', sans-serif; border:1px solid #3F3917; border-right: 1px solid #F7941D; border-bottom: 1px solid #F7941D;\" width=\"45%\">Others</td>"
		 * + "    </tr>" + "  </tbody>" + "</table>" + "</td>" + "       </tr>" +
		 * "	   <tr>" +
		 * "<td align=\"center\" height=\"20\" style=\" \"><spacer type=\"block\" height=\"20\"></td>"
		 * + "</tr>" + "  " + "  <tr>" +
		 * "    <td bgcolor=\"#FFF6CD\" colspan=\"2\" align=\"left\" valign=\"top\" bgcolor=\"#ffffff\" style=\"color:#333333; padding:0 0 0 0;  font-family:Arial, 'Times New Roman', sans-serif; font-size:12px; color:#333333; line-height:16px; text-align:justify;\" class=\"padding_auto\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"
		 * + "     " + "      <tr>" +
		 * "        <td align=\"left\" valign=\"top\" style=\"padding:0 52px 0px 60px; font-family:Arial, Helvetica, sans-serif; font-size:14px; color:#333333; line-height:22px;\" class=\"padding_auto\">"
		 * + "		<p>Your reference ID for E-Mandate registration is "+
		 * digireferenceid +".<br></p>" +
		 * "		<p>To View and Manage your E-Mandate, <u><a href=\"http://www.indusind.com\" target=\"_blank\"><font style=\"color: #333333; text-decoration: none;\">click here</a></font></u>.<br></p>"
		 * + "		<p><b>Now you can start using your funds to</b><br></p>" +
		 * "		<ul style=\"margin:0px 15px;padding:0px;\">" +
		 * "<li style=\"margin: 0px 0px 4px;font-size:14px;color: #333333;\">Earn attractive Interest Rates by setting up a Fixed Deposit / Recurring Deposit</li>"
		 * +
		 * "<li style=\"margin: 5px 0px 4px;font-size:14px;color: #333333;\">Enjoy Vouchers for every 3 months your funds are pulled into your IndusInd Bank Account"
		 * + "</li>" + "</ul>" + "</td>" + "      </tr>" + "		<tr>" +
		 * "<td align=\"center\" height=\"10\" style=\" \"><spacer type=\"block\" height=\"10\"></td>"
		 * + "</tr>       " + "      <tr>" +
		 * "        <td bgcolor=\"#FFF6CD\" align=\"left\" valign=\"top\" style=\"font-family:Arial, Helvetica, sans-serif; font-size:14px; color:#333333; line-height:16px; "
		 * + "		padding: 0 0px 0 55px;\" class=\"padding_auto\">" +
		 * "          <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"marginnone\">"
		 * + "            <tbody>" + "              <tr>" +
		 * "                <td align=\"left\" valign=\"top\" style=\"padding:0 0 0 0;\" class=\"paddingnone\">"
		 * +
		 * "                  <table width=\"100%\" border=\"0\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\" class=\"marginnone\">"
		 * + "                    <tbody>" + "                      <tr>" +
		 * "                        <td align=\"left\" valign=\"top\" style=\"padding: 0px;\" class=\"paddingnone\">"
		 * +
		 * "                          <table width=\"180\" border=\"0\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\" style=\"margin-left: 5px;\" class=\"marginnone\">"
		 * + "                            <tbody>" +
		 * "                              <tr>" +
		 * "                                <td align=\"center\" valign=\"top\" style=\"padding: 5px 1px;\">"
		 * +
		 * "                                  <table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class=\"marginnone heightnone\">"
		 * + "                                    <tbody>" +
		 * "                                     " +
		 * "                                      <tr>" +
		 * "                                        <td bgcolor=\"#FFEC5F\" align=\"center\" valign=\"top\"  height=\"50\" class=\"none\" style=\"font-family:Arial, Helvetica, sans-serif; font-size:14px; color:#333333; background: #FFEC5F; border:1px solid #F7941D; line-height:22px; padding: 5px 10px 5px 10px;\">"
		 * +
		 * "									&#x20B9;200 voucher <br>for transferring &#x20B9;5000 x 3</b></td>"
		 * + "                                        </tr>" +
		 * "                                      </tbody>" +
		 * "                                    </table>" +
		 * "                                  </td>" +
		 * "                                </tr>" +
		 * "                              </tbody>" +
		 * "                            </table>" +
		 * "                          <table width=\"180\" border=\"0\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\" style=\"margin-left: 15px;\" class=\"marginnone\">"
		 * + "                            <tbody>" +
		 * "                              <tr>" +
		 * "                                <td align=\"center\" valign=\"top\" style=\"padding: 5px 1px;\">"
		 * +
		 * "                                  <table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class=\"marginnone heightnone\">"
		 * + "                                    <tbody>" +
		 * "                                     " +
		 * "                                      <tr>" +
		 * "                                        <td bgcolor=\"#FFEC5F\" align=\"center\" valign=\"top\"  height=\"50\" class=\"none\" style=\"font-family:Arial, Helvetica, sans-serif; font-size:14px; color:#333333; background: #FFEC5F; border:1px solid #F7941D; line-height:22px; padding: 5px 10px 5px 10px;\">&#x20B9;500 voucher  <br>for transferring &#x20B9;10000 x 3</b></td>"
		 * + "                                        </tr>" +
		 * "                                      </tbody>" +
		 * "                                    </table>" +
		 * "                                  </td>" +
		 * "                                </tr>" +
		 * "                              </tbody>" +
		 * "                            </table>" +
		 * "							<table width=\"180\" border=\"0\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\" style=\"margin-left: 15px;\" class=\"marginnone\">"
		 * + "                            <tbody>" +
		 * "                              <tr>" +
		 * "                                <td align=\"center\" valign=\"top\" style=\"padding: 5px 1px;\">"
		 * +
		 * "                                  <table width=\"100%\"  border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class=\"marginnone heightnone\">"
		 * + "                                    <tbody>" +
		 * "                                      " +
		 * "                                      <tr>" +
		 * "                                        <td bgcolor=\"#FFEC5F\" align=\"center\" valign=\"top\"  height=\"50\" class=\"none\" style=\"font-family:Arial, Helvetica, sans-serif; font-size:14px; color:#333333; background: #FFEC5F; border:1px solid #F7941D; line-height:22px; padding: 5px 10px 5px 10px;\">"
		 * +
		 * "										&#x20B9;1000 voucher <br>for transferring &#x20B9;25000 x 3"
		 * + "</td>" + "                                        </tr>" +
		 * "                                      </tbody>" +
		 * "                                    </table>" +
		 * "                                  </td>" +
		 * "                                </tr>" +
		 * "                              </tbody>" +
		 * "                            </table>" + "                          " +
		 * "                          </td>" + "                        </tr>" +
		 * "                      </tbody>" + "                    </table>" +
		 * "                  " + "                  </td>" + "              </tr>" +
		 * "              </tbody>" + "            </table>" + "          </td>" +
		 * "        </tr>" + "		<tr>" +
		 * "<td align=\"center\" height=\"20\" style=\" \"><spacer type=\"block\" height=\"20\"></td>"
		 * + "</tr>" + "       <tr>" +
		 * "        <td align=\"left\" valign=\"top\" style=\"padding:0 52px 0px 60px; font-family:Arial, Helvetica, sans-serif; font-size:14px; color:#333333; line-height:22px;\" class=\"padding_auto\">"
		 * + "<ul style=\"margin:0px 15px;padding:0px;\">" +
		 * "<li style=\"margin: 0px 0px 15px;font-size:14px;color: #333333;\">Set up SIP and invest in Mutual Funds through IndusSmart- our state-of-the-art online Mutual Fund platform."
		 * +
		 * "</li></ul><p>We thank you for choosing IndusInd Bank and look forward to serving you again soon.<br></p>"
		 * + "<p><b>Assuring you of our best services always.</b><br></p>" +
		 * "Warm regards,<br>" + "Team IndusInd Bank" + "</td>" + "</tr>" + " <tr>" +
		 * "<td align=\"center\" height=\"20\" style=\" \"><spacer type=\"block\" height=\"20\"></td>"
		 * + "</tr>" + "  <tr>" +
		 * "        <td align=\"center\" valign=\"top\" style=\"padding:0 52px 0px 60px; font-family:Arial, Helvetica, sans-serif; font-size:14px; color:#333333; line-height:22px;\" class=\"padding_auto\">"
		 * +
		 * "In case of any issues/ query related to E-mandate registration you may <br id=\"hide\">"
		 * +
		 * "write to <a href=\"mailto:corporatecare@indusind.com\" style=\"color:#333333; text decoration:none;\"><font style=\"color: #333333; text-decoration: none;\">reachus@indusind.com</a></font> or call 1860 500 5004"
		 * + "</td>" + "</tr>" + "</table></td>" + "</tr>" + "<tr>" +
		 * "    <td align=\"center\" valign=\"bottom\" bgcolor=\"#FFF6CD\" class=\"responsive-image\" style=\"padding:0; font-size:1px; \"><span class=\"responsive-image\" style=\"padding:0; font-size:1px;\"><img src=\"images/IndusIndBank_Grouping_04.jpg\" width=\"700\" height=\"79\" alt=\"\" border=\"0\" style=\"display:block;\" /></span></td>"
		 * + "   </tr>" + "</table>" + "</td>" + "</tr>" + "</body>";
		 * 
		 * String data = "<body style=\"margin:0; padding:0;\">" +
		 * "	<table width=\"700\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style=\"border:1px solid #cccccc; background:#FFF6CD; \">"
		 * + "" + "    <tr>" +
		 * "      <td align=\"center\" valign=\"top\" style=\"padding: 0;\">" +
		 * "		  <table width=\"700\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class=\"marginnone\">"
		 * + "	" + "	<tr>" +
		 * "    <td align=\"center\" valign=\"bottom\" bgcolor=\"#FFF6CD\" class=\"responsive-image\" style=\"padding:0; font-size:1px;\">"
		 * +
		 * "    <table width=\"100%\"  border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"marginnone\">"
		 * + "	<tr>" +
		 * "		<td  align=\"center\" valign=\"bottom\" bgcolor=\"#FFF6CD\" class=\"responsive-image\" style=\"padding:0; font-size:1px;\">"
		 * +
		 * "		<img src=\"images/IndusIndBank_Grouping_01_01.jpg\" width=\"117\" height=\"59\" border=\"0\" style=\"display:block;\" alt=\"IndusInd Bank\" /></td>"
		 * +
		 * "		<td  align=\"center\" valign=\"bottom\" bgcolor=\"#FFF6CD\" class=\"responsive-image\" style=\"padding:0; font-size:1px;\">"
		 * +
		 * "			<img src=\"images/IndusIndBank_Grouping_01_02.jpg\" width=\"383\" height=\"59\" border=\"0\" style=\"display:block;\" alt=\"IndusInd Bank\" /></td>"
		 * +
		 * "		<td  align=\"center\" valign=\"bottom\" bgcolor=\"#FFF6CD\" class=\"responsive-image\" style=\"padding:0; font-size:1px;\"><a href=\"http://www.indusind.com/\" target=\"_blank\"><img src=\"images/IndusIndBank_Grouping_01_03.jpg\" width=\"200\" height=\"59\" border=\"0\" style=\"display:block;\" alt=\"IndusInd Bank\" /></a></td>"
		 * + "	</tr>" + "</table></td>" + "  </tr>" + " <tr>" +
		 * "    <td align=\"center\" valign=\"bottom\" bgcolor=\"#FFF6CD\" class=\"responsive-image\" style=\"padding:0; font-size:1px;\"><span class=\"responsive-image\" style=\"padding:0; font-size:1px;\"><img src=\"images/IndusIndBank_Grouping_03.jpg\" width=\"700\" height=\"315\" alt=\"\" border=\"0\" style=\"display:block;\" /></span></td>"
		 * + "   </tr>" + "    <tr>" +
		 * "        <td align=\"left\" valign=\"top\" style=\"padding:0 60px 0px 60px; font-family:Arial, Helvetica, sans-serif; font-size:14px; color:#333333; line-height:22px;\" class=\"padding_auto\">"
		 * + "		<p>Dear Customer,<br></p>" +
		 * "Congratulations!  We wish to inform you that your E-Mandate has been successfully created. Please find below the details pertaining to your E-Mandate."
		 * + "</td>" + "      </tr>" + " <tr>" +
		 * "<td align=\"center\" height=\"20\" style=\" \"><spacer type=\"block\" height=\"20\"></td>"
		 * + "</tr>" + "" + "" + "<tr>" +
		 * "         <td align=\"left\" valign=\"top\" style=\"padding: 0 0 0 0;\" class=\"top-space padding_auto\">"
		 * +
		 * "			 <table width=\"50%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"left\"style=\"  margin-top:0px; margin-left:60px\" >"
		 * + "  <tbody>" + "    <tr>" +
		 * "      <td align=\"left\" valign=\"middle\" style=\"padding: 5px 10px; color: #333333; font-size:13px; line-height:20px;   font-family: Arial, 'Times New Roman', sans-serif; border:1px solid #3F3917;  border-top: 1px solid #F7941D; border-left: 1px solid #F7941D;\"  bgcolor=\"#FFEC5F\">Mandate issued to</td>"
		 * +
		 * "      <td align=\"left\" valign=\"middle\" style=\"padding: 5px 10px; color: #333333; font-size:13px; line-height:16px;  font-family: Arial, 'Times New Roman', sans-serif; border:1px solid #3F3917; border-top: 1px solid #F7941D; border-right: 1px solid #F7941D;\" bgcolor=\"#FFEC5F\">"
		 * +name+"</td>" + "     " + "    </tr>" + "    <tr>" +
		 * "      <td bgcolor=\"#FFEC5F\" align=\"left\" valign=\"middle\" style=\"padding: 5px 1px 3px 10px; color: #333333; font-size:13px; line-height:20px;  font-family: Arial, 'Times New Roman', sans-serif; border:1px solid #3F3917; border-left: 1px solid #F7941D; \" width=\"35% \">Start date</td>"
		 * +
		 * "		<td bgcolor=\"#FFEC5F\" align=\"left\" valign=\"middle\" style=\"padding: 5px 10px; color: #333333; font-size:13px; line-height:16px;  font-family: Arial, 'Times New Roman', sans-serif; border:1px solid #3F3917; border-right: 1px solid #F7941D;\" width=\"45%\">"
		 * +startDate+"</td>" + "    </tr>" + "	   <tr>" +
		 * "      <td bgcolor=\"#FFEC5F\" align=\"left\" valign=\"middle\" style=\"padding: 5px 1px 3px 10px; color: #333333; font-size:13px; line-height:20px;  font-family: Arial, 'Times New Roman', sans-serif; border:1px solid #3F3917; border-left: 1px solid #F7941D; \" width=\"35%\">End date"
		 * + "</td>" +
		 * "		<td bgcolor=\"#FFEC5F\" align=\"left\" valign=\"middle\" style=\"padding: 5px 10px; color: #333333; font-size:13px; line-height:16px;  font-family: Arial, 'Times New Roman', sans-serif; border:1px solid #3F3917; border-right: 1px solid #F7941D;\" width=\"45%\">"
		 * +endDate+"</td>" + "    </tr>" + "	" + "	<tr>" +
		 * "      <td bgcolor=\"#FFEC5F\" align=\"left\" valign=\"middle\" style=\"padding: 5px 1px 3px 10px; color: #333333; font-size:13px; line-height:20px;  font-family: Arial, 'Times New Roman', sans-serif; border:1px solid #3F3917; border-left: 1px solid #F7941D; \" width=\"35%\">Frequency "
		 * + "</td>" +
		 * "		<td bgcolor=\"#FFEC5F\" align=\"left\" valign=\"middle\" style=\"padding: 5px 10px; color: #333333; font-size:13px; line-height:16px;  font-family: Arial, 'Times New Roman', sans-serif; border:1px solid #3F3917; border-right: 1px solid #F7941D;\" width=\"45%\">"
		 * +frequency+"" + "</td>" + "    </tr>" + "	<tr>" +
		 * "      <td bgcolor=\"#FFEC5F\" align=\"left\" valign=\"middle\" style=\"padding: 5px 1px 3px 10px; color: #333333; font-size:13px; line-height:20px;  font-family: Arial, 'Times New Roman', sans-serif; border:1px solid #3F3917; border-left: 1px solid #F7941D; \" width=\"35%\">Amount in figures (INR)"
		 * + "</td>" +
		 * "		<td bgcolor=\"#FFEC5F\" align=\"left\" valign=\"middle\" style=\"padding: 5px 10px; color: #333333; font-size:13px; line-height:16px;  font-family: Arial, 'Times New Roman', sans-serif; border:1px solid #3F3917; border-right: 1px solid #F7941D;\" width=\"45%\">"
		 * + "Rs." + maximumAmount +"" + "    </tr>" + "	<tr>" +
		 * "      <td bgcolor=\"#FFEC5F\" align=\"left\" valign=\"middle\" style=\"padding: 5px 1px 3px 10px; color: #333333; font-size:13px; line-height:20px;  font-family: Arial, 'Times New Roman', sans-serif; border:1px solid #3F3917; border-left: 1px solid #F7941D; \" width=\"35%\">Amount in Words "
		 * + "</td>" +
		 * "		<td bgcolor=\"#FFEC5F\" align=\"left\" valign=\"middle\" style=\"padding: 5px 10px; color: #333333; font-size:13px; line-height:16px;  font-family: Arial, 'Times New Roman', sans-serif; border:1px solid #3F3917; border-right: 1px solid #F7941D;\" width=\"45%\">"
		 * + "Rupees " + numbertoword.convert(Integer.parseInt(maximumAmount)) + "</td>"
		 * + "    </tr>" + "	<tr>" +
		 * "      <td bgcolor=\"#FFEC5F\" align=\"left\" valign=\"middle\" style=\"padding: 5px 1px 3px 10px; color: #333333; font-size:13px; line-height:20px;  font-family: Arial, 'Times New Roman', sans-serif; border:1px solid #3F3917; border-left: 1px solid #F7941D; border-bottom: 1px solid #F7941D;\" width=\"35%\">Purpose of mandate"
		 * + "</td>" +
		 * "		<td bgcolor=\"#FFEC5F\" align=\"left\" valign=\"middle\" style=\"padding: 5px 10px; color: #333333; font-size:13px; line-height:16px;  font-family: Arial, 'Times New Roman', sans-serif; border:1px solid #3F3917; border-right: 1px solid #F7941D; border-bottom: 1px solid #F7941D;\" width=\"45%\">Others</td>"
		 * + "    </tr>" + "  </tbody>" + "</table>" + "</td>" + "       </tr>" +
		 * "	   <tr>" +
		 * "<td align=\"center\" height=\"20\" style=\" \"><spacer type=\"block\" height=\"20\"></td>"
		 * + "</tr>" + "  " + "  <tr>" +
		 * "    <td bgcolor=\"#FFF6CD\" colspan=\"2\" align=\"left\" valign=\"top\" bgcolor=\"#ffffff\" style=\"color:#333333; padding:0 0 0 0;  font-family:Arial, 'Times New Roman', sans-serif; font-size:12px; color:#333333; line-height:16px; text-align:justify;\" class=\"padding_auto\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"
		 * + "     " + "      <tr>" +
		 * "        <td align=\"left\" valign=\"top\" style=\"padding:0 52px 0px 60px; font-family:Arial, Helvetica, sans-serif; font-size:14px; color:#333333; line-height:22px;\" class=\"padding_auto\">"
		 * + "		<p>Your reference ID for E-Mandate registration is "+
		 * digireferenceid +".<br></p>" +
		 * "		<p>To View and Manage your E-Mandate, <u><a href=\"http://www.indusind.com\" target=\"_blank\"><font style=\"color: #333333; text-decoration: none;\">click here</a></font></u>.<br></p>"
		 * + "		<p><b>Now you can start using your funds to</b><br></p>" +
		 * "		<ul style=\"margin:0px 15px;padding:0px;\">" +
		 * "<li style=\"margin: 0px 0px 4px;font-size:14px;color: #333333;\">Earn attractive Interest Rates by setting up a Fixed Deposit / Recurring Deposit</li>"
		 * +
		 * "<li style=\"margin: 0px 0px 15px;font-size:14px;color: #333333;\">Set up SIP and invest in Mutual Funds through IndusSmart- our state-of-the-art online Mutual Fund platform."
		 * + "</li>"+ "</ul>" +
		 * "<p>We thank you for choosing IndusInd Bank and look forward to serving you again soon.<br></p>"
		 * + "<p><b>Assuring you of our best services always.</b><br></p>" +
		 * "Warm regards,<br>" + "Team IndusInd Bank" + "</td>" + " </tr>" + " <tr>" +
		 * "<td align=\"center\" height=\"20\" style=\" \"><spacer type=\"block\" height=\"20\"></td>"
		 * + "</tr>" + "  <tr>" +
		 * "        <td align=\"center\" valign=\"top\" style=\"padding:0 52px 0px 60px; font-family:Arial, Helvetica, sans-serif; font-size:14px; color:#333333; line-height:22px;\" class=\"padding_auto\">"
		 * +
		 * "In case of any issues/ query related to E-mandate registration you may <br id=\"hide\">"
		 * +
		 * "write to <a href=\"mailto:corporatecare@indusind.com\" style=\"color:#333333; text decoration:none;\"><font style=\"color: #333333; text-decoration: none;\">reachus@indusind.com</a></font> or call 1860 500 5004"
		 * + "</td>" + "</tr>" + "</table></td>" + "</tr>" + "<tr>" +
		 * "    <td align=\"center\" valign=\"bottom\" bgcolor=\"#FFF6CD\" class=\"responsive-image\" style=\"padding:0; font-size:1px; \"><span class=\"responsive-image\" style=\"padding:0; font-size:1px;\"><img src=\"images/IndusIndBank_Grouping_04.jpg\" width=\"700\" height=\"79\" alt=\"\" border=\"0\" style=\"display:block;\" /></span></td>"
		 * + "   </tr>" + "</table>" + "</td>" + "</tr>" + "</body>";
		 */

		String data = "<tr>"
				+ "        <td align=\"left\" valign=\"top\" style=\"padding:0 60px 0px 60px; font-family:Arial, Helvetica, sans-serif; font-size:14px; color:#333333; line-height:22px;\" class=\"padding_auto\">"
				+ "		<p>Dear Customer,<br></p>"
				+ "Congratulations!  We wish to inform you that your E-Mandate has been successfully created. Please find below the details pertaining to your E-Mandate."
				+ "</td>" + "      </tr>" + " <tr>"
				+ "<td align=\"center\" height=\"20\" style=\" \"><spacer type=\"block\" height=\"20\"></td>" + "</tr>"
				+ "" + "" + "<tr>"
				+ "         <td align=\"left\" valign=\"top\" style=\"padding: 0 0 0 0;\" class=\"top-space padding_auto\">"
				+ "			 <table width=\"50%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"left\"style=\"  margin-top:0px; margin-left:60px\" >"
				+ "  <tbody>" + "    <tr>"
				+ "      <td align=\"left\" valign=\"middle\" style=\"padding: 5px 10px; color: #333333; font-size:13px; line-height:20px;   font-family: Arial, 'Times New Roman', sans-serif; border:1px solid #3F3917;  border-top: 1px solid #F7941D; border-left: 1px solid #F7941D;\"  bgcolor=\"#FFEC5F\">Mandate issued to</td>"
				+ "      <td align=\"left\" valign=\"middle\" style=\"padding: 5px 10px; color: #333333; font-size:13px; line-height:16px;  font-family: Arial, 'Times New Roman', sans-serif; border:1px solid #3F3917; border-top: 1px solid #F7941D; border-right: 1px solid #F7941D;\" bgcolor=\"#FFEC5F\">"
				+ name + "</td>" + "     " + "    </tr>" + "    <tr>"
				+ "      <td bgcolor=\"#FFEC5F\" align=\"left\" valign=\"middle\" style=\"padding: 5px 1px 3px 10px; color: #333333; font-size:13px; line-height:20px;  font-family: Arial, 'Times New Roman', sans-serif; border:1px solid #3F3917; border-left: 1px solid #F7941D; \" width=\"35% \">Start date</td>"
				+ "		<td bgcolor=\"#FFEC5F\" align=\"left\" valign=\"middle\" style=\"padding: 5px 10px; color: #333333; font-size:13px; line-height:16px;  font-family: Arial, 'Times New Roman', sans-serif; border:1px solid #3F3917; border-right: 1px solid #F7941D;\" width=\"45%\">"
				+ startDate + "</td>" + "    </tr>" + "	   <tr>"
				+ "      <td bgcolor=\"#FFEC5F\" align=\"left\" valign=\"middle\" style=\"padding: 5px 1px 3px 10px; color: #333333; font-size:13px; line-height:20px;  font-family: Arial, 'Times New Roman', sans-serif; border:1px solid #3F3917; border-left: 1px solid #F7941D; \" width=\"35%\">End date"
				+ "</td>"
				+ "		<td bgcolor=\"#FFEC5F\" align=\"left\" valign=\"middle\" style=\"padding: 5px 10px; color: #333333; font-size:13px; line-height:16px;  font-family: Arial, 'Times New Roman', sans-serif; border:1px solid #3F3917; border-right: 1px solid #F7941D;\" width=\"45%\">"
				+ endDate + "</td>" + "    </tr>" + "	" + "	<tr>"
				+ "      <td bgcolor=\"#FFEC5F\" align=\"left\" valign=\"middle\" style=\"padding: 5px 1px 3px 10px; color: #333333; font-size:13px; line-height:20px;  font-family: Arial, 'Times New Roman', sans-serif; border:1px solid #3F3917; border-left: 1px solid #F7941D; \" width=\"35%\">Frequency "
				+ "</td>"
				+ "		<td bgcolor=\"#FFEC5F\" align=\"left\" valign=\"middle\" style=\"padding: 5px 10px; color: #333333; font-size:13px; line-height:16px;  font-family: Arial, 'Times New Roman', sans-serif; border:1px solid #3F3917; border-right: 1px solid #F7941D;\" width=\"45%\">"
				+ frequency + "" + "</td>" + "    </tr>" + "	<tr>"
				+ "      <td bgcolor=\"#FFEC5F\" align=\"left\" valign=\"middle\" style=\"padding: 5px 1px 3px 10px; color: #333333; font-size:13px; line-height:20px;  font-family: Arial, 'Times New Roman', sans-serif; border:1px solid #3F3917; border-left: 1px solid #F7941D; \" width=\"35%\">Amount in figures (INR)"
				+ "</td>"
				+ "		<td bgcolor=\"#FFEC5F\" align=\"left\" valign=\"middle\" style=\"padding: 5px 10px; color: #333333; font-size:13px; line-height:16px;  font-family: Arial, 'Times New Roman', sans-serif; border:1px solid #3F3917; border-right: 1px solid #F7941D;\" width=\"45%\">"
				+ "Rs." + maximumAmount + "" + "    </tr>" + "	<tr>"
				+ "      <td bgcolor=\"#FFEC5F\" align=\"left\" valign=\"middle\" style=\"padding: 5px 1px 3px 10px; color: #333333; font-size:13px; line-height:20px;  font-family: Arial, 'Times New Roman', sans-serif; border:1px solid #3F3917; border-left: 1px solid #F7941D; \" width=\"35%\">Amount in Words "
				+ "</td>"
				+ "		<td bgcolor=\"#FFEC5F\" align=\"left\" valign=\"middle\" style=\"padding: 5px 10px; color: #333333; font-size:13px; line-height:16px;  font-family: Arial, 'Times New Roman', sans-serif; border:1px solid #3F3917; border-right: 1px solid #F7941D;\" width=\"45%\">"
				+ "Rupees " + numbertoword.convert(Integer.parseInt(maximumAmount)) + "</td>" + "    </tr>" + "	<tr>"
				+ "      <td bgcolor=\"#FFEC5F\" align=\"left\" valign=\"middle\" style=\"padding: 5px 1px 3px 10px; color: #333333; font-size:13px; line-height:20px;  font-family: Arial, 'Times New Roman', sans-serif; border:1px solid #3F3917; border-left: 1px solid #F7941D; border-bottom: 1px solid #F7941D;\" width=\"35%\">Purpose of mandate"
				+ "</td>"
				+ "		<td bgcolor=\"#FFEC5F\" align=\"left\" valign=\"middle\" style=\"padding: 5px 10px; color: #333333; font-size:13px; line-height:16px;  font-family: Arial, 'Times New Roman', sans-serif; border:1px solid #3F3917; border-right: 1px solid #F7941D; border-bottom: 1px solid #F7941D;\" width=\"45%\">Others</td>"
				+ "    </tr>" + "  </tbody>" + "</table>" + "</td>" + "       </tr>" + "	   <tr>"
				+ "<td align=\"center\" height=\"20\" style=\" \"><spacer type=\"block\" height=\"20\"></td>" + "</tr>"
				+ "  " + "  <tr>"
				+ "    <td bgcolor=\"#FFF6CD\" colspan=\"2\" align=\"left\" valign=\"top\" bgcolor=\"#ffffff\" style=\"color:#333333; padding:0 0 0 0;  font-family:Arial, 'Times New Roman', sans-serif; font-size:12px; color:#333333; line-height:16px; text-align:justify;\" class=\"padding_auto\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"
				+ "     " + "      <tr>"
				+ "        <td align=\"left\" valign=\"top\" style=\"padding:0 52px 0px 60px; font-family:Arial, Helvetica, sans-serif; font-size:14px; color:#333333; line-height:22px;\" class=\"padding_auto\">"
				+ "		<p>Your reference ID for E-Mandate registration is " + digireferenceid + ".<br></p>"
				+ "		<p>To View and Manage your E-Mandate, <u><a href=\"http://www.indusind.com\" target=\"_blank\"><font style=\"color: #333333; text-decoration: none;\">click here</a></font></u>.<br></p>"
				+ "		<p><b>Now you can start using your funds to</b><br></p>"
				+ "		<ul style=\"margin:0px 15px;padding:0px;\">"
				+ "<li style=\"margin: 0px 0px 4px;font-size:14px;color: #333333;\">Earn attractive Interest Rates by setting up a Fixed Deposit / Recurring Deposit</li>"
				+ "<li style=\"margin: 0px 0px 15px;font-size:14px;color: #333333;\">Set up SIP and invest in Mutual Funds through IndusSmart- our state-of-the-art online Mutual Fund platform."
				+ "</li>" + "</ul>"
				+ "<p>We thank you for choosing IndusInd Bank and look forward to serving you again soon.<br></p>"
				+ "<p><b>Assuring you of our best services always.</b><br></p>" + "Warm regards,<br>"
				+ "Team IndusInd Bank" + "</td>" + " </tr>" + " <tr>"
				+ "<td align=\"center\" height=\"20\" style=\" \"><spacer type=\"block\" height=\"20\"></td>" + "</tr>"
				+ "  <tr>"
				+ "        <td align=\"center\" valign=\"top\" style=\"padding:0 52px 0px 60px; font-family:Arial, Helvetica, sans-serif; font-size:14px; color:#333333; line-height:22px;\" class=\"padding_auto\">"
				+ "In case of any issues/ query related to E-mandate registration you may <br id=\"hide\">"
				+ "write to <a href=\"mailto:corporatecare@indusind.com\" style=\"color:#333333; text decoration:none;\"><font style=\"color: #333333; text-decoration: none;\">reachus@indusind.com</a></font> or call 1860 500 5004"
				+ "</td>" + "</tr>" + "</table></td>" + "</tr>";

		return data;
	}

	public String digioSuccessMandate(String refId) {

		return "Congratulations! Your E-Mandate request has been successfully recorded &amp; forwarded to the selected bank. Your request will get processed within 7 working days.";
	}

	public String digioFailedMandate(String refId) {

		return "Dear Customer, Your E-Mandate request has failed. We request you to kindly retry.";
	}

	public String urmSuccessGenerate(String refId, String link) {

		return "Congratulations! Your E-Mandate has been successfully created. Your UMRN No. is " + refId + " Visit "
				+ link + " to view &amp; manage your E-mandates.";
	}

	public String npciFaliure(String refId, String reason) {

		return "Dear Customer, Your E-Mandate request " + refId + "has failed because of " + reason
				+ " We request you to kindly retry.";
	}
	
	public String cancelSuccess(String refId, String urmn, String accdigit ) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateWithoutTime = sdf.format(new Date());
		
		return "Your mandate registered with "+ urmn +" for A/C No ending "+ accdigit +" has been initiated for cancellation on "
				+ dateWithoutTime + " Ref No."+ refId +"- IndusInd Bank";
	}
}
