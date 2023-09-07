package com.iib.platform.servlets;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import java.util.HashMap;

import java.util.Map;
import java.util.UUID;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.json.JSONObject;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;

import com.iib.platform.services.config.DigioCreateMandateServletConfig;
import com.iib.platform.services.DatabaseConnectionService;
import com.iib.platform.services.HttpAPIService;
import com.iib.platform.core.smsemail.EmailSmsApiService;

/**
 * DigioCreateMandate Servlet
 *
 * @author Ayasya Digital Solutions LLP
 *
 */
@Component(immediate = true, service = { Servlet.class }, enabled = true, property = {
		Constants.SERVICE_DESCRIPTION + "=" + "DigioCreateMandate Servlet",
		"sling.servlet.methods=" + HttpConstants.METHOD_POST, "sling.servlet.paths=" + "/bin/digioCreateMandate" })
@Designate(ocd = DigioCreateMandateServletConfig.class)
public class DigioCreateMandateServlet extends SlingAllMethodsServlet {

	private static final long serialVersionUID = 1L;

	private static final String UNAUTORIZE = "unauthorized access";
	private static final String PROPERTY = "property";

	private static Logger log = LoggerFactory.getLogger(DigioCreateMandateServlet.class);

	transient JsonParser jsonParser = new JsonParser();

	@Reference
	transient HttpAPIService httpAPIService;

	@Reference
	transient DatabaseConnectionService databaseConService;

	@Reference
	transient EmailSmsApiService emailSmsApiService;

	private String endPointUrl;

	private String clientSecretKey;
	private String clientSecretValue;
	private String clientIdKey;
	private String clientIdValue;
	private String envType;
	private String talismaCall;

	private String callRelatedTo;
	private String callType;
	private String callSubType;
	private String media;
	private String interactionState;
	private String team;
	private String subject;
	private String message;
	private String smsFlag;
	private String emailFlag;

	/** API Properties */
	private String customerNameF1;
	private String destinationBankF2;
	private String customerAccNoF3;
	private String confirmAccNoF4;
	private String destinationBankIdF5;
	private String maxAmountF6;
	private String transferFreqF7;
	private String startDateF8;
	private String endDateF9;
	private String totalAmountF10;
	private String referralCodeF12;
	private String creditAccountF14;
	private String mobileF15;

	/** Digio API Properties */
	private String digioUrl;
	private String managementCategory;
	private String instrumentType;
	private String isRecurring;
	private String enachType;
	private String authorization;
	private String corporateconfigid;
	private String authmode;

	private String interactionId;

	@Activate
	public void activate(DigioCreateMandateServletConfig config) {
		log.info("Activated DigioCreateMandateServlet");

		this.endPointUrl = config.getEndPointUrl();
		this.clientSecretKey = config.getClientSecretKey();
		this.clientSecretValue = config.getClientSecretValue();
		this.clientIdKey = config.getClientID();
		this.clientIdValue = config.getClientIDValue();
		this.envType = config.getEnvType();
		this.talismaCall = config.getApplyTalsimaCall();

		config.accountNo();
		config.cifId();
		this.callRelatedTo = config.callRelatedTo();
		this.callType = config.callType();
		this.callSubType = config.callSubType();
		this.media = config.media();
		this.interactionState = config.interactionState();
		this.team = config.team();
		this.subject = config.subject();
		config.status();
		this.message = config.message();
		this.smsFlag = config.smsFlag();
		this.emailFlag = config.emailFlag();

		this.customerNameF1 = config.f1();
		this.destinationBankF2 = config.f2();
		this.customerAccNoF3 = config.f3();
		this.confirmAccNoF4 = config.f4();
		this.destinationBankIdF5 = config.f5();
		this.maxAmountF6 = config.f6();
		this.transferFreqF7 = config.f7();
		this.startDateF8 = config.f8();
		this.endDateF9 = config.f9();
		this.totalAmountF10 = config.f10();
		config.f11();
		this.referralCodeF12 = config.f12();
		config.f13();
		this.creditAccountF14 = config.f14();
		this.mobileF15 = config.f15();

		this.digioUrl = config.getDigioUrl();
		config.sponserBankId();
		this.managementCategory = config.managementCategory();
		this.instrumentType = config.instrumentType();
		this.isRecurring = config.isRecurring();
		this.enachType = config.enachType();
		this.authorization = config.authorization();
		this.corporateconfigid = config.corporate_config_id();
		this.authmode = config.auth_mode();
	}

	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		String jsonObject = request.getHeader("X-AUTH-TOKEN");
		String key = request.getHeader("X-AUTH-SESSION");
		String requestParamJson = "";
		Map<String, String> resultParam = new HashMap<>();
		requestParamJson = decrypt(key, key, jsonObject);
		JsonObject requestJson = null;

		if ((null == jsonObject) || (null == key) || (key.length() != 16))
			out.println(UNAUTORIZE);

		try {
			requestJson = (JsonObject) jsonParser.parse(requestParamJson);

		} catch (Exception e) {
			log.error("Exception", e);
		}

		String destinationBank = fetchValue(requestJson, "destinationBank");
		String destinationBankId = fetchValue(requestJson, "destinationBankId");
		String customerAccountNo = fetchValue(requestJson, "customerAccountNo");
		String maximumamount = fetchValue(requestJson, "maximum_amount");
		String transferFrequency = fetchValue(requestJson, "transferFrequency");
		String mobileNo = fetchValue(requestJson, "mobileNo");
		String startDate = fetchValue(requestJson, "startDate");
		String endDate = fetchValue(requestJson, "endDate");
		String customername = fetchValue(requestJson, "customerName");
		String customerRefNumber = fetchValue(requestJson, "accountNo");
		String confirmAccountNo = fetchValue(requestJson, "confirmAccountNo");
		String totalAmount = fetchValue(requestJson, "totalAmount");
		String totalInstallments = fetchValue(requestJson, "totalInstallments");
		String referralCode = fetchValue(requestJson, "referralCode");
		String aadharNo = fetchValue(requestJson, "aadharNo");
		String customerEmail = fetchValue(requestJson, "customerEmail");
		String cifid = fetchValue(requestJson, "cifid");
		String tnc =  fetchValue(requestJson, "tnc");

		/**
		 * Labels
		 */
		String customerNameLabel = fetchValue(requestJson, "customerNameLabel");
		String bankNameLabel = fetchValue(requestJson, "bankNameLabel");
		String accountNumberLabel = fetchValue(requestJson, "accountNumberLabel");
		String confirmAccountLabel = fetchValue(requestJson, "confirmAccountLabel");
		String ifscLabel = fetchValue(requestJson, "ifscLabel");
		String maximumAmountLabel = fetchValue(requestJson, "maximumAmountLabel");
		String transferFreqLabel = fetchValue(requestJson, "transferFreqLabel");
		String startDateLabel = fetchValue(requestJson, "startDateLabel");
		String endDateLabel = fetchValue(requestJson, "endDateLabel");
		String totalAmountLabel = fetchValue(requestJson, "totalAmountLabel");
		String referralCodeLabel = fetchValue(requestJson, "referralCodeLabel");
		String creditAccountLabel = fetchValue(requestJson, "creditAccountLabel");

		if (databaseConService.getCreateEnachSession(key, fetchValue(requestJson, "mobileNo"),
				fetchValue(requestJson, "customerName"), fetchValue(requestJson, "customerEmail")))
			out.println(UNAUTORIZE);
		else {

			try {
				/**
				 * Help Me API Integration
				 * 
				 */
				
				if(talismaCall.equalsIgnoreCase("YES")) {
				final String PREFERRED_PREFIX = "soap";
				MessageFactory messageFactory = MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL);
				SOAPMessage soapMessage = messageFactory.createMessage();
				String prefix = "tem";
				String uri = "http://tempuri.org/";
				SOAPEnvelope envelope = soapMessage.getSOAPPart().getEnvelope();
				SOAPHeader soapHeader = soapMessage.getSOAPHeader();
				envelope.removeNamespaceDeclaration(envelope.getPrefix());
				envelope.setPrefix(PREFERRED_PREFIX);
				envelope.addNamespaceDeclaration(prefix, uri);
				soapHeader.setPrefix(PREFERRED_PREFIX);
				SOAPBody soapBody = soapMessage.getSOAPBody();
				soapBody.setPrefix(PREFERRED_PREFIX);

				DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = documentFactory.newDocumentBuilder();
				Document doc = docBuilder.newDocument();
				Element rootElement = doc.createElement("Talisma");

				Element talismaPropertiesDetails = doc.createElement("TalismaPropertiesDetails");
				rootElement.appendChild(talismaPropertiesDetails);

				Text custNameNode = doc.createTextNode(customerNameLabel + ":" + customername);
				Element property1 = doc.createElement(PROPERTY);
				String idKey1 = "ID";
				String idValue1 = customerNameF1;
				property1.setAttribute(idKey1, idValue1);
				property1.appendChild(custNameNode);
				talismaPropertiesDetails.appendChild(property1);

				Text bankNameNode = doc.createTextNode(bankNameLabel + ":" + destinationBank);
				Element property2 = doc.createElement(PROPERTY);
				String idKey2 = "ID";
				String idKeyValue2 = destinationBankF2;
				property2.setAttribute(idKey2, idKeyValue2);
				property2.appendChild(bankNameNode);
				talismaPropertiesDetails.appendChild(property2);

				Text customerAccountNode = doc.createTextNode(accountNumberLabel + ":" + customerAccountNo);
				Element property3 = doc.createElement(PROPERTY);
				String idKey3 = "ID";
				String idKeyValue3 = customerAccNoF3;
				property3.setAttribute(idKey3, idKeyValue3);
				property3.appendChild(customerAccountNode);
				talismaPropertiesDetails.appendChild(property3);

				Text confirmAccountNode = doc.createTextNode(confirmAccountLabel + ":" + confirmAccountNo);
				Element property4 = doc.createElement(PROPERTY);
				String idKey4 = "ID";
				String idKeyValue4 = confirmAccNoF4;
				property4.setAttribute(idKey4, idKeyValue4);
				property4.appendChild(confirmAccountNode);
				talismaPropertiesDetails.appendChild(property4);

				Text ifscNode = doc.createTextNode(ifscLabel + ":" + destinationBankId);
				Element property5 = doc.createElement(PROPERTY);
				String idKey5 = "ID";
				String idKeyValue5 = destinationBankIdF5;
				property5.setAttribute(idKey5, idKeyValue5);
				property5.appendChild(ifscNode);
				talismaPropertiesDetails.appendChild(property5);

				Text maximumAmountNode = doc.createTextNode(maximumAmountLabel + ":" + maximumamount);
				Element property6 = doc.createElement(PROPERTY);
				String idKey6 = "ID";
				String idKeyValue6 = maxAmountF6;
				property6.setAttribute(idKey6, idKeyValue6);
				property6.appendChild(maximumAmountNode);
				talismaPropertiesDetails.appendChild(property6);

				Text transferFreqNode = doc.createTextNode(transferFreqLabel + ":" + transferFrequency);
				Element property7 = doc.createElement(PROPERTY);
				String idKey7 = "ID";
				String idKeyValue7 = transferFreqF7;
				property7.setAttribute(idKey7, idKeyValue7);
				property7.appendChild(transferFreqNode);
				talismaPropertiesDetails.appendChild(property7);

				Text startDateNode = doc.createTextNode(startDateLabel + ":" + startDate);
				Element property8 = doc.createElement(PROPERTY);
				String idKey8 = "ID";
				String idKeyValue8 = startDateF8;
				property8.setAttribute(idKey8, idKeyValue8);
				property8.appendChild(startDateNode);
				talismaPropertiesDetails.appendChild(property8);

				Text endDateNode = doc.createTextNode(endDateLabel + ":" + endDate);
				Element property9 = doc.createElement(PROPERTY);
				String idKey9 = "ID";
				String idKeyValue9 = endDateF9;
				property9.setAttribute(idKey9, idKeyValue9);
				property9.appendChild(endDateNode);
				talismaPropertiesDetails.appendChild(property9);

				Text totalAmountNode = doc.createTextNode(totalAmountLabel + ":" + totalAmount);
				Element property10 = doc.createElement(PROPERTY);
				String idKey10 = "ID";
				String idKeyValue10 = totalAmountF10;
				property10.setAttribute(idKey10, idKeyValue10);
				property10.appendChild(totalAmountNode);
				talismaPropertiesDetails.appendChild(property10);

				Text referralCodeNode = doc.createTextNode(referralCodeLabel + ":" + referralCode);
				Element property12 = doc.createElement(PROPERTY);
				String idKey12 = "ID";
				String idKeyValue12 = referralCodeF12;
				property12.setAttribute(idKey12, idKeyValue12);
				property12.appendChild(referralCodeNode);
				talismaPropertiesDetails.appendChild(property12);

				Text creditAccountNode = doc.createTextNode(creditAccountLabel + ":" + customerRefNumber);
				Element property14 = doc.createElement(PROPERTY);
				String idKey14 = "ID";
				String idKeyValue14 = creditAccountF14;
				property14.setAttribute(idKey14, idKeyValue14);
				property14.appendChild(creditAccountNode);
				talismaPropertiesDetails.appendChild(property14);

				Text mobileNode = doc.createTextNode("MobileNumber :" + mobileNo);
				Element property15 = doc.createElement(PROPERTY);
				String idKey15 = "ID";
				String idKeyValue15 = mobileF15;
				property15.setAttribute(idKey15, idKeyValue15);
				property15.appendChild(mobileNode);
				talismaPropertiesDetails.appendChild(property15);

				doc.appendChild(rootElement);

				DOMSource domSource = new DOMSource(doc);
				StringWriter writer = new StringWriter();
				StreamResult result = new StreamResult(writer);
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				transformer.transform(domSource, result);
				String innerXml = writer.toString();

				SOAPBodyElement createServiceRequest = soapBody
						.addBodyElement(envelope.createQName("CreateServiceRequest", prefix));

				SOAPElement accountNo = createServiceRequest.addChildElement(envelope.createQName("AccountNo", prefix));
				accountNo.addTextNode(customerRefNumber);

				SOAPElement tsrcifid = createServiceRequest.addChildElement(envelope.createQName("CIF_ID", prefix));
				tsrcifid.addTextNode(cifid);

				SOAPElement tsrcallRelatedTo = createServiceRequest
						.addChildElement(envelope.createQName("CallRelatedTo", prefix));
				tsrcallRelatedTo.addTextNode(this.callRelatedTo);

				SOAPElement tsrcallType = createServiceRequest
						.addChildElement(envelope.createQName("CallType", prefix));
				tsrcallType.addTextNode(this.callType);

				SOAPElement tsrcallSubType = createServiceRequest
						.addChildElement(envelope.createQName("CallSubType", prefix));
				tsrcallSubType.addTextNode(this.callSubType);

				SOAPElement tsrmedia = createServiceRequest.addChildElement(envelope.createQName("Media", prefix));
				tsrmedia.addTextNode(this.media);

				SOAPElement tsrinteractionState = createServiceRequest
						.addChildElement(envelope.createQName("InteractionState", prefix));
				tsrinteractionState.addTextNode(this.interactionState);

				SOAPElement tsrteam = createServiceRequest.addChildElement(envelope.createQName("Team", prefix));
				tsrteam.addTextNode(this.team);

				SOAPElement tsrsubject = createServiceRequest.addChildElement(envelope.createQName("Subject", prefix));
				tsrsubject.addTextNode(this.subject);

				SOAPElement tsrmessage = createServiceRequest.addChildElement(envelope.createQName("Message", prefix));
				tsrmessage.addTextNode(this.message);

				SOAPElement tsrsmsflag = createServiceRequest.addChildElement(envelope.createQName("SMS_Flag", prefix));
				tsrsmsflag.addTextNode(this.smsFlag);

				SOAPElement tsremailflag = createServiceRequest
						.addChildElement(envelope.createQName("Email_Flag", prefix));
				tsremailflag.addTextNode(this.emailFlag);

				SOAPElement xmlstr = createServiceRequest.addChildElement(envelope.createQName("XML_Str", prefix));
				CDATASection cdata = xmlstr.getOwnerDocument().createCDATASection(innerXml);
				xmlstr.appendChild(cdata);

				if (StringUtils.isNotBlank(clientIdValue)) {
					soapMessage.getMimeHeaders().addHeader(clientSecretKey, clientSecretValue);
					soapMessage.getMimeHeaders().addHeader(clientIdKey, clientIdValue);
				} else {
					log.info("Blank Headers");
				}
				soapMessage.saveChanges();

				printSoapRequest(soapMessage);

				SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
				SOAPConnection soapConnection = soapConnectionFactory.createConnection();
				URL endUrl = new URL(endPointUrl);
				SOAPMessage soapResponse = soapConnection.call(soapMessage, endUrl);

				String responseXml = "";
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				soapResponse.writeTo(stream);
				responseXml = stream.toString();

				log.debug("Talisma Response Body:- {}", responseXml);

				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder documentBuilder = factory.newDocumentBuilder();
				Document document = documentBuilder.parse(new InputSource(new StringReader(responseXml)));

				
				NodeList nodeList = document.getElementsByTagName("CreateServiceRequestResponse");
				for (int i = 0; i < nodeList.getLength(); i++) {
					Node node = nodeList.item(i);
					if (node.getNodeType() == Node.ELEMENT_NODE) {
						Element element = (Element) node;
						NodeList childNode = element.getChildNodes();
						for (int j = 0; j < childNode.getLength(); j++) {
							Node eachChild = childNode.item(j);
							Element eachElement = (Element) eachChild;
							if (eachElement.getTagName().equalsIgnoreCase("CreateServiceRequestResult")) {
								resultParam.put("status", eachElement.getTextContent());
							} else if (eachElement.getTagName().equalsIgnoreCase("InteractionID")) {
								resultParam.put("interactionId", eachElement.getTextContent());
							}
						}
					}
				}

				
				
			}else
			{
				resultParam.put("status", "success");
				resultParam.put("interactionId", "NA");
			}
				
				
				
				this.interactionId = resultParam.get("interactionId");
				log.debug("{} :: Interaction ID", interactionId);

				/**
				 * Help ME API End
				 */

				JSONObject postData = new JSONObject();

				JSONObject content = new JSONObject();
				content.put("first_collection_date", startDate);
				content.put("final_collection_date", endDate);
				content.put("destination_bank_id", destinationBankId);//
				content.put("destination_bank_name", destinationBank); //
				content.put("management_category", managementCategory); //
				content.put("customer_account_number", customerAccountNo); //
				content.put("instrument_type", instrumentType);
				content.put("customer_name", customername);
				content.put("collection_amount", maximumamount);
				content.put("is_recurring", isRecurring);//
				content.put("frequency", transferFrequency); //
				if (envType.equalsIgnoreCase("PROD"))
					postData.put("customer_identifier", mobileNo);// Added Deafult for Time Being as requested by Ankur
				if (envType.equalsIgnoreCase("UAT"))
					postData.put("customer_identifier", "ankur.chaturvedi@indusind.com");
				postData.put("auth_mode", authmode);
				postData.put("mandate_type", enachType);
				postData.put("corporate_config_id", corporateconfigid);
				postData.put("mandate_data", content);
				log.debug("Digio Create Request ::{} ", postData);

				HttpClient httpclient = HttpClientBuilder.create().build();
				HttpPost httpPostRequest = new HttpPost(digioUrl);

				StringEntity se = new StringEntity(postData.toString());

				httpPostRequest.setEntity(se);
				httpPostRequest.setHeader("Authorization", authorization);
				httpPostRequest.setHeader("Content-Type", "application/json");
				HttpResponse resp = (HttpResponse) httpclient.execute(httpPostRequest);

				log.debug("Digio Create Response :: {} ", resp);

				HttpEntity entity = resp.getEntity();

				log.info("Digio Create Response 2 :: {} ", entity);

				// Read the content stream
				InputStream instream = entity.getContent();
				BufferedReader br = new BufferedReader(new InputStreamReader(instream));

				StringBuilder sb = new StringBuilder();
				String line;
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}

				JSONObject jsonRes = new JSONObject(sb.toString());

				/*
				 * Call to Add create values in database START
				 */

				String uniqueID = UUID.randomUUID().toString();
				String uniqueRequestId = "digioreq_" + uniqueID;
				String resultStatus = "failure";
				String interactionIdDet = null;
				String responseCode = null;
				String mandateId = null;
				if (resultParam.containsKey("status")) {
					resultStatus = resultParam.get("status");
				}

				if (resultParam.containsKey("interactionId")) {
					interactionIdDet = resultParam.get("interactionId");
				}

				if (jsonRes != null && jsonRes.has("details")) {
					responseCode = jsonRes.getString("details");

					log.debug("values we get responseCode {}", responseCode);
				}

				if (jsonRes != null && jsonRes.has("id")) {
					mandateId = jsonRes.getString("id");

					log.debug("values we get mandateIdmandateId {}", mandateId);
				}

				boolean insertedSuccess = databaseConService.createNachDetails(uniqueRequestId, customername,
						customerEmail, customerRefNumber, confirmAccountNo, totalAmount, referralCode,
						totalInstallments, aadharNo, destinationBank, destinationBankId, customerAccountNo,
						maximumamount, transferFrequency, mobileNo, startDate, endDate, responseCode, mandateId,
						responseCode, key, tnc);
				log.info("Added Successfully in DB create mandate data {}", insertedSuccess);
				jsonRes.put("interactionId", interactionIdDet);
				out.println(jsonRes);

				log.debug("Final Result we get {}", jsonRes);
			} catch (Exception e) {
				log.error("Exception in DigioCreateMandateServlet 1 :: ", e);
			}
		}

	}

	private void printSoapRequest(SOAPMessage soapMessage) throws SOAPException, IOException {
		/** Print Request */
		String requestXmlString = "";
		ByteArrayOutputStream reqXml = new ByteArrayOutputStream();
		soapMessage.writeTo(reqXml);
		requestXmlString = reqXml.toString();
		Document reqDoc = convertStringToDocument(requestXmlString);
		String xmlOutput = convertDocumentToString(reqDoc, "2");
		log.debug("{} :: SOAP API Request ", xmlOutput);
	}

	private static Document convertStringToDocument(String xmlStr) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			return builder.parse(new InputSource(new StringReader(xmlStr)));

		} catch (Exception e) {
			log.error("Exception in DigioCreateMandateServlet :: ", e);
		}
		return null;
	}

	private static String convertDocumentToString(Document doc, String indent) {
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer;
		try {
			transformer = tf.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", indent);
			StringWriter writer = new StringWriter();
			transformer.transform(new DOMSource(doc), new StreamResult(writer));
			String output = writer.getBuffer().toString();
			return output.replaceAll("&lt;", "<").replaceAll("&gt;", ">");
		} catch (TransformerException e) {
			log.error("Exception in DigioCreateMandateServlet 2 :: ", e);
		}
		return null;
	}

	private String fetchValue(JsonObject json, String variable)

	{
		String value = "";
		if (json.has(variable))
			if (null != json.get(variable))
				value = json.get(variable).getAsString();

		return value;
	}

	public static String encrypt(String key, String initVector, String value) {
		try {
			IvParameterSpec iv = new IvParameterSpec(initVector.getBytes(StandardCharsets.UTF_8));
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

			byte[] encrypted = cipher.doFinal(value.getBytes());

			return Base64.encodeBase64String(encrypted);
		} catch (Exception ex) {
			log.error("Exception in DigioCreateMandateServlet 3 :: ", ex);
		}

		return null;
	}

	public static String decrypt(String key, String initVector, String encrypted) {
		try {

			IvParameterSpec iv = new IvParameterSpec(key.getBytes(StandardCharsets.UTF_8));
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

			byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));

			return new String(original);
		} catch (Exception ex) {
			log.info("Exception in DigioCreateMandateServlet 4 ::{} {} ", ex, initVector);
		}

		return null;
	}

}
