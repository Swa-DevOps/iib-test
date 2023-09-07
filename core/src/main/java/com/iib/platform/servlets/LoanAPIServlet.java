package com.iib.platform.servlets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;

import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.json.JSONObject;
import org.osgi.framework.Constants;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;

import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.CDATASection;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.iib.platform.api.request.RequestObject;
import com.iib.platform.api.response.ResponseBody;
import com.iib.platform.api.response.ResponseObject;
import com.iib.platform.services.EmandateBankDataService;
import com.iib.platform.services.HttpAPIService;
import com.iib.platform.services.StoreOTPService;
import com.iib.platform.services.EncryptDecryptService;
import com.iib.platform.services.config.LoanAPIServletConfig;
import com.iib.platform.services.DatabaseConnectionService;

/**
 * Loan API Servlet
 *
 * @author Niket goel
 *
 */
@Component(immediate = true, service = {
		Servlet.class }, configurationPolicy = ConfigurationPolicy.REQUIRE, enabled = true, property = {
				Constants.SERVICE_DESCRIPTION + "=" + "Loan API Servlet",
				"sling.servlet.methods=" + HttpConstants.METHOD_POST, "sling.servlet.paths=" + "/bin/loanApi" })
@Designate(ocd = LoanAPIServletConfig.class)
public class LoanAPIServlet extends SlingAllMethodsServlet {

	private static final long serialVersionUID = 1L;

	private static final String TEXT_XML_CHARSET_UTF_8 = "text/xml; charset=UTF-8";

	private static final String CONTENT_TYPE = "Content-type";

	private static final String BANK_CODE = "Bank_code";

	private static final String EMI_START_DATE = "EMI_start_date";

	/** Static Logger */
	private static Logger log = LoggerFactory.getLogger(LoanAPIServlet.class);

	transient JsonParser jsonParser = new JsonParser();

	@Reference
	transient EmandateBankDataService emandateBankDataService;

	@Reference
	transient StoreOTPService storeOTPService;

	@Reference
	transient DatabaseConnectionService dconnService;

	@Reference
	transient EncryptDecryptService encryptDecryptService;

	@Reference
	transient HttpAPIService httpAPIService;

	/** Private Static Fields */
	private String endPoint;
	private String clientSecretKey;
	private String clientSecretValue;
	private String clientIdKey;
	private String clientIdValue;
	private String requestUrl;
	private String emiMessage;
	private String emiAmount;

	@Activate
	protected void activate(LoanAPIServletConfig config) {
		log.info("Loan API Service has been activated!");
		this.endPoint = config.getEndPointUrl();
		this.clientSecretKey = config.getClientSecretKey();
		this.clientSecretValue = config.getClientSecretValue();
		this.clientIdKey = config.getClientID();
		this.clientIdValue = config.getClientIDValue();
		this.requestUrl = config.requestUrl();
		this.emiMessage = config.loanEMIamountcheckMessage();
		this.emiAmount = config.loanEMIamountcheck();
	}

	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		String loanNumber = "";
		// Security Implementation

		String mKey = request.getHeader("X-AUTH-SESSION");
		String jsonObject = request.getHeader("X-AUTH-TOKEN");
		String encryptKey = jsonObject.substring(jsonObject.indexOf(':') + 1);
		jsonObject = jsonObject.substring(0, (jsonObject.indexOf(':')));
		String requestParamJson = "";
		requestParamJson = encryptDecryptService.decrypt(encryptKey, encryptKey, jsonObject);
		JsonObject requestJson = null;

		try {
			requestJson = (JsonObject) jsonParser.parse(requestParamJson);
			loanNumber = requestJson.get("loanNumber").getAsString();

		} catch (Exception e) {
			log.error("Exception ", e);
		}

		String validate = dconnService.getSetEnachPLLoginSession(loanNumber, mKey, "");

		if ((null == jsonObject) || (null == mKey))
			out.println("1. unauthorized access");
		else if (encryptKey.length() != 16)
			out.println("2. unauthorized access");
		else if (validate.equalsIgnoreCase("YES")) {
			out.println("3. unauthorized access");
		} else {

			try {

				SOAPMessage soapMessage = createSoapRequest(loanNumber);
				
				String requestXmlString = "";
				ByteArrayOutputStream reqXml = new ByteArrayOutputStream();
				if(soapMessage !=null) {
				soapMessage.writeTo(reqXml);
				requestXmlString = reqXml.toString();
				}
				Document reqDoc = convertStringToDocument(requestXmlString);
				String xmlOutput = convertDocumentToString(reqDoc, "2");
				log.debug("{} :: SOAP API Request (Loan After Function API Details)", xmlOutput);

				/** Creating Connection */
				SOAPConnectionFactory soapConFactory = SOAPConnectionFactory.newInstance();
				SOAPConnection connection = soapConFactory.createConnection();
				URL url = new URL(endPoint);
				SOAPMessage soapResponse = connection.call(soapMessage, url);

				/** Print Response */
				String responseXML = "";
				ByteArrayOutputStream resXml = new ByteArrayOutputStream();
				soapResponse.writeTo(resXml);
				responseXML = resXml.toString();
				Document respDoc = convertStringToDocument(responseXML);
				String finalOutpt = convertDocumentToString(respDoc, "2");
				log.info("{} :: LOAN API Response", finalOutpt);

				/** Parsing SOAPBody */
				SOAPBody responseBody = soapResponse.getSOAPBody();
				NodeList executeNodeList = responseBody.getElementsByTagName("executeServiceReturn");
				Element executeElement = (Element) executeNodeList.item(0);
				Node child = executeElement.getFirstChild();
				String charData = "";
				if (child instanceof CharacterData) {
					charData = ((CharacterData) child).getData();
				}

				Document responseDoc = convertStringToDocument(charData);

				if (null == responseDoc) {
					throw new NullPointerException("responseDoc is null");
				}

				NodeList bodyList = responseDoc.getElementsByTagName("executeFinacleScript_CustomData");

				checkResult(bodyList, encryptKey, out, response, loanNumber, mKey);

			}

			catch (Exception e) {
				out.print("Exception Message for Loan API Servlet :: " + e);
			}
		}
	}

	private Map<String, String> getValues(Node node) {
		Map<String, String> values = new HashMap<>();
		Element element = (Element) node;
		NodeList childNodes = element.getChildNodes();
		for (int j = 0; j < childNodes.getLength(); j++) {
			Node eachChild = childNodes.item(j);
			if (!childNodes.item(j).getNodeName().equalsIgnoreCase("#text")) {
				values.put(childNodes.item(j).getNodeName(), eachChild.getTextContent());
			}
		}
		return values;
	}

	private void checkResult(NodeList bodyList, String encryptKey, PrintWriter out, SlingHttpServletResponse response,
			String loanNumber, String mKey) {

		String responseMessage = "";
		String sucFlag = "";
		String bankCode = "";
		Map<String, String> values = new HashMap<>();
		JSONObject obj;
		try {
			for (int index = 0; index < bodyList.getLength(); index++) {
				Node node = bodyList.item(index);
				if (node.getNodeType() == Node.ELEMENT_NODE) {

					values = getValues(node);

					responseMessage = values.get("message");
					sucFlag = values.get("sucFaiFlag");
					bankCode = values.get(BANK_CODE);
					
					boolean verifiedBankCode =  false;
					
					if (!sucFlag.equalsIgnoreCase("F")) 
						verifiedBankCode = emandateBankDataService.getEnachRepaymentBankNameVerify(bankCode);

					if (sucFlag.equalsIgnoreCase("F")) {

						checkResponse(responseMessage, encryptKey, out, response);

					} else if (!verifiedBankCode) {
						JSONObject resJson = new JSONObject();
						resJson.put("msg",
								"We are unable to process your e-NACH mandate registration as the Bank linked to your Personal Loan Account is not participating in E-NACH registration");
						out.println(encryptDecryptService.encrypt(encryptKey, encryptKey, resJson.toString()));
						response.flushBuffer();

					} else if((Integer.parseInt(values.get("EMI_amount")))> Integer.parseInt(emiAmount)) {
						
						JSONObject resJson = new JSONObject();
						resJson.put("msg",
								"Your EMI amount exceeds the maximum limit for e-NACH mandate registration. Please call 1860 500 5004 to register your NACH mandate manually.");
						out.println(encryptDecryptService.encrypt(encryptKey, encryptKey, resJson.toString()));
						response.flushBuffer();
						
					}else {
						String[] tmp1 = values.get(EMI_START_DATE).split("[-]");
						String[] tmp2 = values.get("EMI_END_date").split("[-]");
						List<String> list1 = Arrays.asList(tmp1);
						List<String> list2 = Arrays.asList(tmp2);
						Collections.swap(list1, 0, 2);
						Collections.swap(list2, 0, 2);
						String startDate = String.join("", list1);
						String endDate = String.join("", list2);
						String normalDateFormat = values.get(EMI_START_DATE);
						String phoneNumber = getLastnCharacters(values.get("MobileNo"), 10);

						int otp = (int) (Math.floor(Math.random() * 900000) + 100000);

						storeOTPService.storeOTPWithSession(phoneNumber, otp, "enach-pl", mKey);
						RequestObject requestObject = new RequestObject();
						requestObject.setRequestUrl(requestUrl);
						ResponseObject responseObject = httpAPIService.getSMSResponse(requestObject, phoneNumber, otp);
						ResponseBody resBody = responseObject.getResponseBody();
						String smsTID = resBody.getResponseContentXML();

						obj = new JSONObject();
						obj.put("Name", values.get("Account_name"));
						obj.put("MobileNo", phoneNumber);
						obj.put("AadharNo", "xxxxxxxxxxxx");
						obj.put("Bank_short_name", values.get("Bank_short_name"));
						obj.put(EMI_START_DATE, startDate);
						obj.put("EMI_END_date", endDate);
						obj.put("EMI_amount", values.get("EMI_amount"));
						obj.put("loanNumber", loanNumber);
						obj.put("Other_bank_account_number", values.get("Other_bank_account_number"));
						obj.put("Instructed_amt", values.get("Instructed_amt"));
						obj.put("OriginalDate", normalDateFormat);
						obj.put("EMI_LIMIT", emiAmount);
						obj.put(BANK_CODE, values.get(BANK_CODE));
						boolean status = dconnService.updateMandateDetails(phoneNumber, obj.toString(), "enach-pl");
						String resultforOTP = dconnService.getSetEnachPLLoginSession(phoneNumber, mKey, "YES");
						log.info("Manadate Data to database {} {}", status, resultforOTP);

						JSONObject resJson = new JSONObject();

						resJson.put("msg", "Success");
						resJson.put("TID", smsTID + phoneNumber);
						out.println(encryptDecryptService.encrypt(encryptKey, encryptKey, resJson.toString()));
						response.flushBuffer();
					}
				}
			}
		} catch (Exception e) {
			log.error("Exception", e);
		}
	}

	private SOAPMessage createSoapRequest(String loanNumber) {

		SOAPMessage soapMessage = null;
		try {
			final String PREFERRED_PREFIX = "soapenv";

			MessageFactory messageFactory = MessageFactory.newInstance();
			soapMessage = messageFactory.createMessage();
			String uri = "http://webservice.fiusb.ci.infosys.com";
			SOAPEnvelope envelope = soapMessage.getSOAPPart().getEnvelope();
			SOAPHeader soapHeader = soapMessage.getSOAPHeader();
			envelope.removeNamespaceDeclaration(envelope.getPrefix());
			envelope.setPrefix(PREFERRED_PREFIX);
			envelope.addNamespaceDeclaration("web", uri);
			soapHeader.setPrefix(PREFERRED_PREFIX);
			SOAPBody soapBody = soapMessage.getSOAPBody();
			soapBody.setPrefix(PREFERRED_PREFIX);

			// Creating XML Document
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// Root Elements
			Document doc = docBuilder.newDocument();
			String nameSpaceUri = "http://www.finacle.com/fixml";
			String qualifiedName = "xmlns";
			String nameSpaceUriSecond = "http://www.w3.org/2001/XMLSchema-instance";
			String qualifiedNameSecond = "xmlns:xsi";
			String nameSpaceUriThird = "http://www.finacle.com/fixml SBAcctAdd.xsd";
			String qualifiedNameThird = "xsi:schemaLocation";

			Element rootElement = doc.createElement("FIXML");

			/* Add Name space for FIXML rootElement */
			rootElement.setAttribute(qualifiedName, nameSpaceUri);
			rootElement.setAttribute(qualifiedNameSecond, nameSpaceUriSecond);
			rootElement.setAttribute(qualifiedNameThird, nameSpaceUriThird);

			Element header = doc.createElement("Header");
			rootElement.appendChild(header);

			Element reqHeader = doc.createElement("RequestHeader");
			header.appendChild(reqHeader);

			Element msgKey = doc.createElement("MessageKey");
			reqHeader.appendChild(msgKey);

			long randomNumber = (long) Math.floor(Math.random() * 9000000000000L) + 1000000000000L;
			Text girNumber = doc.createTextNode("Gir_" + randomNumber);

			Element reqUUId = doc.createElement("RequestUUID");
			msgKey.appendChild(reqUUId);
			reqUUId.appendChild(girNumber);

			Element serviceReq = doc.createElement("ServiceRequestId");
			Text serviceReqText = doc.createTextNode("executeFinacleScript");
			serviceReq.appendChild(serviceReqText);
			msgKey.appendChild(serviceReq);

			Element serviceReqVersion = doc.createElement("ServiceRequestVersion");
			Text serviceReqVersionText = doc.createTextNode("10.2");
			serviceReqVersion.appendChild(serviceReqVersionText);
			msgKey.appendChild(serviceReqVersion);

			Element channelId = doc.createElement("ChannelId");
			Text channelIdText = doc.createTextNode("COR");
			channelId.appendChild(channelIdText);
			msgKey.appendChild(channelId);

			Element languageId = doc.createElement("LanguageId");
			msgKey.appendChild(languageId);

			Element reqMessageInfo = doc.createElement("RequestMessageInfo");
			reqHeader.appendChild(reqMessageInfo);

			Element bankId = doc.createElement("BankId");
			reqMessageInfo.appendChild(bankId);

			Element timeZone = doc.createElement("TimeZone");
			reqMessageInfo.appendChild(timeZone);

			Element entityId = doc.createElement("EntityId");
			reqMessageInfo.appendChild(entityId);

			Element entityType = doc.createElement("EntityType");
			reqMessageInfo.appendChild(entityType);

			Element armCorrelationId = doc.createElement("ArmCorrelationId");
			reqMessageInfo.appendChild(armCorrelationId);

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.ENGLISH);
			Date date = new Date();

			Element messageDateTime = doc.createElement("MessageDateTime");
			Text messageDateTimeText = doc.createTextNode(dateFormat.format(date));
			messageDateTime.appendChild(messageDateTimeText);
			reqMessageInfo.appendChild(messageDateTime);

			Element security = doc.createElement("Security");
			reqHeader.appendChild(security);

			Element token = doc.createElement("Token");
			security.appendChild(token);

			Element passwordToken = doc.createElement("PasswordToken");
			token.appendChild(passwordToken);

			Element userId = doc.createElement("UserId");
			passwordToken.appendChild(userId);

			Element password = doc.createElement("Password");
			passwordToken.appendChild(password);

			Element fICertToken = doc.createElement("FICertToken");
			security.appendChild(fICertToken);

			Element realUserLoginSessionId = doc.createElement("RealUserLoginSessionId");
			security.appendChild(realUserLoginSessionId);

			Element realUser = doc.createElement("RealUser");
			security.appendChild(realUser);

			Element realUserPwd = doc.createElement("RealUserPwd");
			security.appendChild(realUserPwd);

			Element sSOTransferToken = doc.createElement("SSOTransferToken");
			security.appendChild(sSOTransferToken);

			/* Body Starts */
			Element body = doc.createElement("Body");
			rootElement.appendChild(body);

			Element executeFinacleScriptRequest = doc.createElement("executeFinacleScriptRequest");
			body.appendChild(executeFinacleScriptRequest);

			Element executeFinacleScriptInputVO = doc.createElement("ExecuteFinacleScriptInputVO");
			executeFinacleScriptRequest.appendChild(executeFinacleScriptInputVO);

			Element executeFinacleScriptInputVOReqId = doc.createElement("requestId");
			Text executeFinacleScriptInputVOReqIdText = doc.createTextNode("IBL0137PLEMANDATE001.scr");
			executeFinacleScriptInputVOReqId.appendChild(executeFinacleScriptInputVOReqIdText);
			executeFinacleScriptInputVO.appendChild(executeFinacleScriptInputVOReqId);

			Element executeFinacleScriptCustomData = doc.createElement("executeFinacleScript_CustomData");
			executeFinacleScriptRequest.appendChild(executeFinacleScriptCustomData);

			Element foracId = doc.createElement("Foracid");
			Text foracIdText = doc.createTextNode(loanNumber);
			foracId.appendChild(foracIdText);
			executeFinacleScriptCustomData.appendChild(foracId);

			doc.appendChild(rootElement);

			/** Converting XML to String */
			DOMSource domSource = new DOMSource(doc);
			StringWriter writer = new StringWriter();
			StreamResult result = new StreamResult(writer);
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.transform(domSource, result);
			String finalResult = writer.toString();

			SOAPBodyElement soapBodyElement = soapBody.addBodyElement(envelope.createQName("executeService", "web"));
			SOAPElement arg0 = soapBodyElement.addChildElement("arg_0_0");
			CDATASection cdata = arg0.getOwnerDocument().createCDATASection(finalResult);
			arg0.appendChild(cdata);

			soapMessage.getMimeHeaders().addHeader(CONTENT_TYPE, TEXT_XML_CHARSET_UTF_8);
			/** Adding MIME Headers */
			if (StringUtils.isNotBlank(clientSecretKey)) {
				soapMessage.getMimeHeaders().addHeader(clientSecretKey, clientSecretValue);
				soapMessage.getMimeHeaders().addHeader(clientIdKey, clientIdValue);
			} else {
				log.info("Blank Headers");
			}
			soapMessage.saveChanges();
			
			String requestXmlString = "";
			ByteArrayOutputStream reqXml = new ByteArrayOutputStream();
			soapMessage.writeTo(reqXml);
			requestXmlString = reqXml.toString();
			Document reqDoc = convertStringToDocument(requestXmlString);
			String xmlOutput = convertDocumentToString(reqDoc, "2");
			log.debug("{} :: SOAP API Request (Loan API Details)", xmlOutput);

		} catch (Exception e) {
			// To do
		}

		return soapMessage;
	}

	private void checkResponse(String responseMessage, String encryptKey, PrintWriter out,
			SlingHttpServletResponse response) {

		try {
			if (responseMessage.equalsIgnoreCase("Account is already registered for NACH")) {
				JSONObject resJson = new JSONObject();
				resJson.put("msg",
						"It seems, you have already registered NACH mandate for your Personal Loan Account.");
				out.println(encryptDecryptService.encrypt(encryptKey, encryptKey, resJson.toString()));
				response.flushBuffer();
			} else if (responseMessage.equalsIgnoreCase("Aadhar No does not exists")) {
				JSONObject resJson = new JSONObject();
				resJson.put("msg",
						"Sorry, we are unable to process your e-NACH mandate registration, as your Aadhaar Number is not linked to your Personal Loan Account. Please call 1860 500 5004, send a copy of your Aadhaar Card to reachus@indusind.com or click on ‘Link your Aadhaar’ on our website, to update your Aadhaar Number in our records.");
				out.println(encryptDecryptService.encrypt(encryptKey, encryptKey, resJson.toString()));
				response.flushBuffer();
			} else if (responseMessage.equalsIgnoreCase("Mandate not available")) {
				JSONObject resJson = new JSONObject();
				resJson.put("msg",
						"Sorry, Mandate is not possible for the account. Please call 1860 500 5004 to register your NACH mandate manually.");
				out.println(encryptDecryptService.encrypt(encryptKey, encryptKey, resJson.toString()));
				response.flushBuffer();
			} else if (responseMessage.equalsIgnoreCase(emiMessage)) { //else if (responseMessage.equalsIgnoreCase("EMI of LAA account is greater than l LKH")) {
				JSONObject resJson = new JSONObject();
				resJson.put("msg",
						"Your EMI amount exceeds the maximum limit for e-NACH mandate registration. Please call 1860 500 5004 to register your NACH mandate manually.");
				out.println(encryptDecryptService.encrypt(encryptKey, encryptKey, resJson.toString()));
				response.flushBuffer();
			} else if (responseMessage.equalsIgnoreCase("Account No does not exists")) {
				JSONObject resJson = new JSONObject();
				resJson.put("msg", "Account No. does not exists!");
				out.println(encryptDecryptService.encrypt(encryptKey, encryptKey, resJson.toString()));
				response.flushBuffer();
			}
		} catch (Exception e) {
			// To do
		}
	}

	public String getLastnCharacters(String inputString, int subStringLength) {
		int length = inputString.length();
		if (length <= subStringLength) {
			return inputString;
		}
		int startIndex = length - subStringLength;
		return inputString.substring(startIndex);
	}

	private static Document convertStringToDocument(String xmlStr) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			return builder.parse(new InputSource(new StringReader(xmlStr)));

		} catch (Exception e) {
			log.error("Exception in convertStringToDocument ", e);
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
			log.error("Exception in convertDocumentToString ", e);
		}
		return null;
	}
}
