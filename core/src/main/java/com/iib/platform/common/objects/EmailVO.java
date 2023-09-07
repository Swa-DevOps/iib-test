package com.iib.platform.common.objects;

import java.util.Map;
import javax.activation.DataSource;
import org.apache.commons.mail.EmailAttachment;

public class EmailVO {

	private String htmlContent;
	private String textContent;
	private String fromAddress;
	private String toAddress;
	private String bcc;
	private String subject;
	private String bodyMessage;
	private DataSource dataSource;
	private String fileName;
	private EmailAttachment emailAttachment;
	private String templatePath;
	private String fileEmailPath;
	private String fileEmailName;
	private Map<String, String> emailTokens;
	private String[] multipleToAddresses;

	public String getfileEmailPath() {
		return fileEmailPath;
	}

	public void setfileEmailPath(String fileEmailPath) {
		this.fileEmailPath = fileEmailPath;
	}

	public String getfileEmailName() {
		return fileEmailName;
	}

	public void setfileEmailName(String fileEmailName) {
		this.fileEmailName = fileEmailName;
	}

	public String getBcc() {
		return bcc;
	}

	public void setBcc(String bcc) {
		this.bcc = bcc;
	}

	/**
	 * @return the htmlContent
	 */
	public String getHtmlContent() {
		return htmlContent;
	}

	/**
	 * @param htmlContent the htmlContent to set
	 */
	public void setHtmlContent(String htmlContent) {
		this.htmlContent = htmlContent;
	}

	/**
	 * @return the textContent
	 */
	public String getTextContent() {
		return textContent;
	}

	/**
	 * @param textContent the textContent to set
	 */
	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}

	/**
	 * @return the fromAddress
	 */
	public String getFromAddress() {
		return fromAddress;
	}

	/**
	 * @param fromAddress the fromAddress to set
	 */
	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	/**
	 * @return the toAddress
	 */
	public String getToAddress() {
		return toAddress;
	}

	/**
	 * @param toAddress the toAddress to set
	 */
	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return the bodyMessage
	 */
	public String getBodyMessage() {
		return bodyMessage;
	}

	/**
	 * @param bodyMessage the bodyMessage to set
	 */
	public void setBodyMessage(String bodyMessage) {
		this.bodyMessage = bodyMessage;
	}

	/**
	 * @return the emailAttachment
	 */
	public EmailAttachment getEmailAttachment() {
		return emailAttachment;
	}

	/**
	 * @param emailAttachment the emailAttachment to set
	 */
	public void setEmailAttachment(EmailAttachment emailAttachment) {
		this.emailAttachment = emailAttachment;
	}

	/**
	 * @return the dataSource
	 */
	public DataSource getDataSource() {
		return dataSource;
	}

	/**
	 * @param dataSource the dataSource to set
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the templatePath
	 */
	public String getTemplatePath() {
		return templatePath;
	}

	/**
	 * @param templatePath the templatePath to set
	 */
	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}

	/**
	 * @return the emailTokens
	 */
	public Map<String, String> getEmailTokens() {
		return emailTokens;
	}

	/**
	 * @param emailTokens the emailTokens to set
	 */
	public void setEmailTokens(Map<String, String> emailTokens) {
		this.emailTokens = emailTokens;
	}

	
	public String[] getMultipleToAddresses() {
		return multipleToAddresses;
	}

	public void setMultipleToAddresses(String[] multipleToAddresses) {
		this.multipleToAddresses = multipleToAddresses;
	}
}
