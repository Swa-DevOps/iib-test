package com.iib.platform.services.impl;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

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
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
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

import com.iib.platform.api.request.RequestObject;
import com.iib.platform.api.response.ResponseObject;
import com.iib.platform.services.SoapAPIService;
import com.iib.platform.services.config.SoapAPIServiceConfig;

/**
 * SOAP API Service Implementation
 *
 * @author Niket Goel
 *
 */
@Component(immediate = true, service = { SoapAPIService.class }, enabled = true, property = {
		Constants.SERVICE_DESCRIPTION + "=" + "SOAP API Service" })
@Designate(ocd = SoapAPIServiceConfig.class)
public class SoapAPIServiceImpl implements SoapAPIService {

	private static Logger log = LoggerFactory.getLogger(SoapAPIServiceImpl.class);
	private static final String ERROR = "error";

	ResponseObject responseObject;
	RequestObject requestObject;

	private String clientSecretKey;
	private String clientSecretValue;
	private String clientIdKey;
	private String clientIdValue;
	private String fluxURL;
	private String enachCFDURL;
	private String enachCFDDealURL;
	private int testingType;

	@Activate
	protected void activate(SoapAPIServiceConfig config) {
		log.info("SOAP API Service has been activated ENACH");

		clientSecretKey = config.getClientSecretKey();
		clientSecretValue = config.getClientSecretValue();
		clientIdKey = config.getClientID();
		clientIdValue = config.getClientIDValue();
		fluxURL = config.getFluxAPIUrl();
		enachCFDURL=config.getenachCFDURL();
		enachCFDDealURL=config.getenachCFDDealURL();
		testingType=config.getenachCFDtestingType();
	}

	@Override
	public ResponseObject getHelpMeStatus(RequestObject requestObject, String customerName, String mobileNumber,
			String emailId) {
		responseObject = new ResponseObject();

		return null;
	}
	
	

	@Override
	public JSONArray getCFDAccountDetails(String mobileNumber) {

		JSONArray jsonArray = new JSONArray();
		try {
		String endPoint = enachCFDURL+"?Mobile_Number="+mobileNumber;	
		
		log.info("URL We hit to get Deal {}",endPoint);
		HttpClient httpclient = HttpClientBuilder.create().build();
		URI uri = new URIBuilder(endPoint).build();
		HttpGet httpGetRequest = new HttpGet(uri);
	
		if (StringUtils.isNotBlank(clientSecretKey)) {
			httpGetRequest.setHeader(clientSecretKey, clientSecretValue);
			httpGetRequest.setHeader(clientIdKey, clientIdValue);
		}
		
		HttpResponse resp = httpclient.execute(httpGetRequest);
		HttpEntity entity = resp.getEntity();

		// Read the content stream
		InputStream instream = entity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader((instream)));
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = br.readLine()) != null) {
			log.info("We get in Deal {}",line );
			sb.append(line);
			sb.append("\n");
		}
		log.info("Result We get in Deal {}",sb );
		
		
		JSONObject obj = new JSONObject();
		if(!(sb.toString().startsWith("{")))
		{
			obj.put(ERROR,sb.toString());
			
		}
		else if(!(new JSONObject(sb.toString()).has("CustomerCodes")))
		{
			log.info("we reached here 1");
			obj.put(ERROR,"Some Technical Problem Occured");
		if(new JSONObject(sb.toString()).has("httpCode") && (new JSONObject(sb.toString()).get("httpCode").toString().equalsIgnoreCase("403"))) {
			
			log.info("we reached here 2");
		
			obj.put(ERROR,"Some Technical Error Occured");
		}
			log.info("we reached here 3");
			
		}
		
		else
		{
			String input="";
			switch(testingType) {
			case 0:
				input=sb.toString();
				break;
			case 1:
				input ="{\"CustomerCodes\":[{\"Deal_No\":\"WAB00943D\",\"Customer_Code\":\"CU2724233\",\"Customer_Name\":\"MAHAMMAD RASID\"},{\"Deal_No\":\"WAB01796C\",\"Customer_Code\":\"CU2724233\",\"Customer_Name\":\"MAHAMMAD RASID\"},{\"Deal_No\":\"WAB01796C\",\"Customer_Code\":\"CU2724234\",\"Customer_Name\":\"MAHAMMfdsdf RASID\"},{\"Deal_No\":\"WAB01796C\",\"Customer_Code\":\"CU2724235\",\"Customer_Name\":\"MAHAMMADsdzfvRASID\"}]}";	
				break;
			case 2:
				input ="{\"CustomerCodes\":[{\"Deal_No\":\"WAB00943D\",\"Customer_Code\":\"CU2724233\",\"Customer_Name\":\"MAHAMMAD RASID\"},{\"Deal_No\":\"WAB01796C\",\"Customer_Code\":\"CU2724233\",\"Customer_Name\":\"MAHAMMAD RASID\"}]}";	
				break;
			case 3:
				input ="{\"CustomerCodes\":[{\"Deal_No\":\"WAB01796C\",\"Customer_Code\":\"CU2724233\",\"Customer_Name\":\"MAHAMMAD RASID\"}]}";			
				break;
			
			default:
				input=sb.toString();
				break;
			}
			
			obj= new JSONObject(input);
			
			JSONArray result = obj.getJSONArray("CustomerCodes");
			
			Set<String> sets = new HashSet<>();
			Set<String> deals = new HashSet<>();
			
			for(int i=0; i<result.length();i++)
			{				
				if(sets.add(result.getJSONObject(i).getString("Customer_Code")+":"+result.getJSONObject(i).getString("Customer_Name")))
					log.info("Added {} ",result.getJSONObject(i).getString("Customer_Code"));	
			}
			
			if(sets.size()>1)
				obj.put("customer", sets);
			else
			{
				for(int i=0; i<result.length();i++)
				{				
					if(deals.add(result.getJSONObject(i).getString("Deal_No")))
						log.info("Added {} ",result.getJSONObject(i).getString("Deal_No"));	
				}
				
				if(deals.size()>1)
					obj.put("deals", deals);
				else
					obj.put("single",getCFDDealDetails(mobileNumber, deals.toString().substring(1, deals.toString().length()-1)));
			}		
		}
		
		jsonArray.put(obj);

		}catch (URISyntaxException  e ) {
			log.error("Exception in URISyntaxException",e);
		} catch (ClientProtocolException e) {
			log.error("Exception in ClientProtocolException",e);
		} catch (IOException e) {
			log.error("Exception in IOException",e);
		} catch (JSONException e) {
			log.error("Exception in JSONException",e);
		}
		
		
		return jsonArray;
	
	}
	
	@Override
	public JSONArray getCFDDealDetails(String mobileNumber,String dealno) {

		JSONArray jsonArray = new JSONArray();
		
		try {
		String endPoint = enachCFDDealURL+"?Deal_No="+dealno.trim();	
		
		
		HttpClient httpclient = HttpClientBuilder.create().build();
		URI uri = new URIBuilder(endPoint).build();
		HttpGet httpGetRequest = new HttpGet(uri);
	
		if (StringUtils.isNotBlank(clientSecretKey)) {
			httpGetRequest.setHeader(clientSecretKey, clientSecretValue);
			httpGetRequest.setHeader(clientIdKey, clientIdValue);
		}
		
		HttpResponse resp = httpclient.execute(httpGetRequest);
		HttpEntity entity = resp.getEntity();

		// Read the content stream
		InputStream instream = entity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader((instream)));
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = br.readLine()) != null) {
			sb.append(line);
			sb.append("\n");
		}
		log.info("Result We get in Deal {}", sb);
		
		
		JSONObject obj = new JSONObject();
		if(!(sb.toString().startsWith("{")))
		{
			obj.put(ERROR,sb.toString());
			
		}
		else if(!(new JSONObject(sb.toString()).has("Customer_Dt")))
		{
		if(new JSONObject(sb.toString()).has("httpcode") && (new JSONObject(sb.toString()).get("httpcode").toString().equalsIgnoreCase("403"))) {
		
			obj.put(ERROR,"Some Technical Error Occured");
		}
			obj.put(ERROR,"Some Technical Problem Occured");
		}
		
		else
		{
			obj= new JSONObject(sb.toString());		
		}
		
		jsonArray.put(obj);

		}catch (URISyntaxException  e ) {
			log.error("Exception in URISyntaxException",e);
		} catch (ClientProtocolException e) {
			log.error("Exception in ClientProtocolException",e);
		} catch (IOException e) {
			log.error("Exception in IOException",e);
		} catch (JSONException e) {
			log.error("Exception in JSONException",e);
		}
		
		
		return jsonArray;
	
	}
	
	
	@Override
	public JSONArray getAccountDetails(String mobileNumber) {

		JSONArray jsonArray = new JSONArray();
		JSONObject obj;
		responseObject = new ResponseObject();
		String endPoint = fluxURL;

		try {
			final String PREFERRED_PREFIX = "soapenv";

			MessageFactory messageFactory = MessageFactory.newInstance();
			SOAPMessage soapMessage = messageFactory.createMessage();

			String prefix = "web";
			String uri = "http://webservice.fiusb.ci.infosys.com";
			SOAPEnvelope envelope = soapMessage.getSOAPPart().getEnvelope();
			SOAPHeader soapHeader = envelope.getHeader();
			envelope.removeNamespaceDeclaration(envelope.getPrefix());
			envelope.setPrefix(PREFERRED_PREFIX);
			envelope.addNamespaceDeclaration(prefix, uri);
			soapHeader.setPrefix(PREFERRED_PREFIX);
			SOAPBody soapBody = soapMessage.getSOAPBody();
			soapBody.setPrefix(PREFERRED_PREFIX);

			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();

			Element rootElement = doc.createElement("FIXML");

			String qualifiedName = "xmlns";
			String nameSpaceUri = "http://www.finacle.com/fixml";
			String qualifiedNameSecond = "xmlns:xsi";
			String nameSpaceUriSecond = "http://www.w3.org/2001/XMLSchema-instance";
			String qualifiedNameThird = "xsi:schemaLocation";
			String nameSpaceUriThird = "http://www.finacle.com/fixml executeFinacleScript.xsd";

			/* Add Name space for FIXML rootElement */
			rootElement.setAttribute(qualifiedName, nameSpaceUri);
			rootElement.setAttribute(qualifiedNameSecond, nameSpaceUriSecond);
			rootElement.setAttribute(qualifiedNameThird, nameSpaceUriThird);

			Element header = doc.createElement("Header");
			rootElement.appendChild(header);

			Element requestHeader = doc.createElement("RequestHeader");
			header.appendChild(requestHeader);

			Element messageKey = doc.createElement("MessageKey");
			requestHeader.appendChild(messageKey);

			long randomNumber = (long) Math.floor(Math.random() * 9000000000000L) + 1000000000000L;
			Text girNumber = doc.createTextNode("Gir_" + randomNumber);

			Element reqUUId = doc.createElement("RequestUUID");
			messageKey.appendChild(reqUUId);
			reqUUId.appendChild(girNumber);

			Element serviceReq = doc.createElement("ServiceRequestId");
			Text serviceReqText = doc.createTextNode("executeFinacleScript");
			serviceReq.appendChild(serviceReqText);
			messageKey.appendChild(serviceReq);

			Element serviceReqVersion = doc.createElement("ServiceRequestVersion");
			Text serviceReqVersionText = doc.createTextNode("10.2");
			serviceReqVersion.appendChild(serviceReqVersionText);
			messageKey.appendChild(serviceReqVersion);

			Element channelId = doc.createElement("ChannelId");
			Text channelIdText = doc.createTextNode("COR");
			channelId.appendChild(channelIdText);
			messageKey.appendChild(channelId);

			Element languageId = doc.createElement("LanguageId");
			messageKey.appendChild(languageId);

			Element reqMessageInfo = doc.createElement("RequestMessageInfo");
			requestHeader.appendChild(reqMessageInfo);

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
			requestHeader.appendChild(security);

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

			/* SoapBody Starts */
			Element body = doc.createElement("Body");
			rootElement.appendChild(body);

			Element executeFinacleScriptRequest = doc.createElement("executeFinacleScriptRequest");
			body.appendChild(executeFinacleScriptRequest);

			Element executeFinacleScriptInputVO = doc.createElement("ExecuteFinacleScriptInputVO");
			executeFinacleScriptRequest.appendChild(executeFinacleScriptInputVO);

			Element executeFinacleScriptInputVOReqId = doc.createElement("requestId");
			Text executeFinacleScriptInputVOReqIdText = doc.createTextNode("IBL0662MOBACC.scr");
			executeFinacleScriptInputVOReqId.appendChild(executeFinacleScriptInputVOReqIdText);
			executeFinacleScriptInputVO.appendChild(executeFinacleScriptInputVOReqId);

			Element executeFinacleScriptCustomData = doc.createElement("executeFinacleScript_CustomData");
			executeFinacleScriptRequest.appendChild(executeFinacleScriptCustomData);

			Element mobNumber = doc.createElement("MOB_Number");
			Text mobileText = doc.createTextNode(mobileNumber);
			mobNumber.appendChild(mobileText);
			executeFinacleScriptCustomData.appendChild(mobNumber);

			Element dateOfBirth = doc.createElement("DOB");
			executeFinacleScriptCustomData.appendChild(dateOfBirth);

			doc.appendChild(rootElement);

			/* Converting XML to String */
			DOMSource domSource = new DOMSource(doc);
			StringWriter writer = new StringWriter();
			StreamResult result = new StreamResult(writer);
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.transform(domSource, result);
			String characterData = writer.toString();

			SOAPBodyElement soapBodyElement = soapBody.addBodyElement(envelope.createQName("executeService", "web"));
			SOAPElement arg0 = soapBodyElement.addChildElement("arg_0_0");
			CDATASection cdata = arg0.getOwnerDocument().createCDATASection(characterData);
			arg0.appendChild(cdata);

			if (StringUtils.isNotBlank(clientSecretKey)) {
				soapMessage.getMimeHeaders().addHeader(clientSecretKey, clientSecretValue);
				soapMessage.getMimeHeaders().addHeader(clientIdKey, clientIdValue);
			} else {
				log.info("Blank Headers");
			}
			soapMessage.saveChanges();

			/** Print Request */
			String requestXmlString = "";
			ByteArrayOutputStream reqXml = new ByteArrayOutputStream();
			soapMessage.writeTo(reqXml);
			requestXmlString = reqXml.toString();
			Document reqDoc = convertStringToDocument(requestXmlString);
			String xmlOutput = convertDocumentToString(reqDoc, "2");
			log.debug("{} :: SOAP API Request (Account Details)", xmlOutput);

			/* Creating Connection */
			SOAPConnectionFactory soapConFactory = SOAPConnectionFactory.newInstance();
			SOAPConnection connection = soapConFactory.createConnection();
			URL url = new URL(endPoint);
			SOAPMessage soapResponse = connection.call(soapMessage, url);

			// Print Response
			String responseXML = "";
			ByteArrayOutputStream resXml = new ByteArrayOutputStream();
			soapResponse.writeTo(resXml);
			responseXML = resXml.toString();
			Document respDoc = convertStringToDocument(responseXML);
			String finalOutpt = convertDocumentToString(respDoc, "2");
			log.debug("{} :: SOAP API Response (Account Details)", finalOutpt);

			/* Parsing SOAPBody */
			SOAPBody responseBody = soapResponse.getSOAPBody();
			NodeList executeNodeList = responseBody.getElementsByTagName("executeServiceReturn");
			Element executeElement = (Element) executeNodeList.item(0);
			Node child = executeElement.getFirstChild();
			String charData = "";

			if (child instanceof CharacterData) {
				charData = ((CharacterData) child).getData();
			}

			Document responseDoc = convertStringToDocument(charData);

			if (responseDoc != null) {
				NodeList accountDetailList = responseDoc.getElementsByTagName("AccountInquiry");

				for (int index = 0; index < accountDetailList.getLength(); index++) {
					Node node = accountDetailList.item(index);
					if (node.getNodeType() == Node.ELEMENT_NODE) {
						Element element = (Element) node;
						NodeList childNodes = element.getChildNodes();
						for (int j = 0; j < childNodes.getLength(); j++) {
							obj = new JSONObject();
							Node eachChild = childNodes.item(j);
							NodeList childrensOfDead = eachChild.getChildNodes();
							for (int k = 0; k < childrensOfDead.getLength(); k++) {
								Node eachChildren = childrensOfDead.item(k);
								obj.put(eachChildren.getNodeName(), eachChildren.getTextContent());
							}
							jsonArray.put(obj);
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("Exception in AccountDetails :: ", e);
		}
		return jsonArray;
	}

	private static Document convertStringToDocument(String xmlStr) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			return builder.parse(new InputSource(new StringReader(xmlStr)));

		} catch (Exception e) {
			log.error("Exception in AccountDetails 1:: ", e);
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
			log.error("Exception in AccountDetails 2:: ", e);
		}
		return null;
	}
}