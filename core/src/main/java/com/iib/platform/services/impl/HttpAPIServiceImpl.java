package com.iib.platform.services.impl;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

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
import javax.xml.soap.SOAPException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.framework.Constants;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.component.annotations.Deactivate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iib.platform.api.request.RequestObject;
import com.iib.platform.api.response.ResponseBody;
import com.iib.platform.api.response.ResponseObject;
import com.iib.platform.services.HttpAPIService;

import com.iib.platform.services.config.HttpAPIServiceConfig;

/**
 * HTTP API Service Implementation
 *
 * @author ADS (Niket Goe)
 *
 */
@Component(immediate = true, service = { HttpAPIService.class }, enabled = true, property = {
		Constants.SERVICE_DESCRIPTION + "=" + "HTTP API Service Implementation" })

@Designate(ocd = HttpAPIServiceConfig.class)

public class HttpAPIServiceImpl implements HttpAPIService {

	private static final Logger log = LoggerFactory.getLogger(HttpAPIServiceImpl.class);
	private static final String ERROR = "ERROR";
	private static final String DEFAULT_ERROR_MSG = "Internal Server Error";
	
	RequestObject requestObject;

	ResponseObject responseObject;

	HttpClient client;

	String url;
	String newurl;
	String cancelurl;
	String clientidname;
	String clientidvalue;
	String secretidname;
	String secretidvalue;
	String includeHeader;
	String feedid;
	String username;
	String password;
	String domainForCRM;

	@Activate
	protected final void activate(HttpAPIServiceConfig config) {
		log.info("Activated HttpAPIServiceImpl");

		url = config.getURLMandateORURM();
		clientidname = config.getClientID();
		clientidvalue = config.getClientIDValue();
		secretidname = config.getClientSecretKey();
		secretidvalue = config.getClientSecretValue();
		includeHeader = config.getIncludeHeader();
		feedid = config.getFeedid();
		username = config.getUsername();
		password = config.getPassword();
		newurl = config.getnewURLMandate();
		cancelurl=config.getnewURLCancelMandate();
		domainForCRM = config.getdomainForCRM();
	}

	@Deactivate
	protected void deactivate(ComponentContext ctx) throws Exception {
		log.info("Deactivated HttpAPIServiceImpl");
	}

	@Override
	public ResponseObject getSMSResponse(RequestObject requestObject, String mobileNumber, int otp) {

		responseObject = new ResponseObject();
		try {
			HttpClient httpclient = HttpClientBuilder.create().build();

			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MINUTE, 15);
			Date date = cal.getTime();
			DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
			String formattedDate = dateFormat.format(date);

			String message = " is your OTP to proceed with E-Mandate setup. OTP is valid till " + formattedDate
					+ " do not share OTP with anyone - Induslnd Bank";
			URI uri = new URIBuilder(requestObject.getRequestUrl()).addParameter("feedid", feedid)
					.addParameter("username", username).addParameter("password", password)
					.addParameter("To", mobileNumber).addParameter("text", otp + message).build();

			HttpGet httpGetRequest = new HttpGet(uri);

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

			// Extracting TID from XML Document
			String xmlData = sb.toString();
			String tid = "";
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new InputSource(new StringReader(xmlData)));
			NodeList nodeList = document.getElementsByTagName("MID");
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node eachNode = nodeList.item(i);
				Element element = (Element) eachNode;
				tid = element.getAttribute("TID");
			}

			ResponseBody responseBody = new ResponseBody();
			responseBody.setResponseContentXML(tid);
			responseObject.setResponseBody(responseBody);
		} catch (Exception e) {
			log.info(e.getMessage());
		}

		return responseObject;
	}

	@Override
	public ResponseObject getSMSResponseWithMode(RequestObject requestObject, String mobileNumber, String mode,
			int otp) {

		responseObject = new ResponseObject();
		try {
			HttpClient httpclient = HttpClientBuilder.create().build();

			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MINUTE, 15);
			Date date = cal.getTime();
			DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
			String formattedDate = dateFormat.format(date);

			String message;

			
			/*if (mode.equalsIgnoreCase("create"))
				message = " is your OTP to proceed with E-Mandate setup. OTP is valid till " + formattedDate
						+ ", do not share OTP with anyone.  ";
			else
				message = " is your OTP to proceed with E-Mandate management. This OTP is valid till " + formattedDate
						+ ", do not share OTP with anyone.  "; /* ---Orginal */

	
			if (mode.equalsIgnoreCase("create"))
				message = otp + " is your OTP to proceed with E-Mandate setup. OTP is valid till " + formattedDate
						+ " do not share OTP with anyone - Induslnd Bank";
			else if (mode.equalsIgnoreCase("cancel"))
				message = "Securely enter OTP " + otp + " to to proceed with the cancellation of your Mandate(s)." 
						+ " Please do not share OTP with anyone – IndusInd Bank";
			else if (mode.equalsIgnoreCase("manage"))
				message = "Securely enter OTP " + otp + " to view/cancel valid mandate(s). " 
						+ "Please do not share OTP with anyone – IndusInd Bank";
			else	
				message = otp + " is your OTP to proceed with E-Mandate management. This OTP is valid till " + formattedDate
						+ " do not share OTP with anyone - Induslnd Bank"; /* ---Chnages on 15th March */

			
			log.info(message);

			URI uri = new URIBuilder(requestObject.getRequestUrl()).addParameter("feedid", feedid)
					.addParameter("username", username).addParameter("password", password)
					.addParameter("To", mobileNumber).addParameter("text", message).build();

			log.info(uri.toString());
			
			HttpGet httpGetRequest = new HttpGet(uri);

			HttpResponse resp =  httpclient.execute(httpGetRequest);
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

			log.info("Response from OTP service {}", sb.toString());
			// Extracting TID from XML Document
			String xmlData = sb.toString();
			String tid = "";
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new InputSource(new StringReader(xmlData)));
			NodeList nodeList = document.getElementsByTagName("MID");
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node eachNode = nodeList.item(i);
				Element element = (Element) eachNode;
				tid = element.getAttribute("TID");
			}

			ResponseBody responseBody = new ResponseBody();
			responseBody.setResponseContentXML(tid);
			responseObject.setResponseBody(responseBody);
		} catch (Exception e) {
			log.info(e.getMessage());
		}

		return responseObject;
	}

	@Override
	public ResponseObject getSOAPResponse(RequestObject requestObject, String mobileNumber) {
		responseObject = new ResponseObject();
		try {
			final String PREFERRED_PREFIX = "soapenv";

			MessageFactory messageFactory = MessageFactory.newInstance();
			SOAPMessage soapMessage = messageFactory.createMessage();
			String uri = "http://MDESBDEVPRD.IBL.COM/IndusInd_WEB_IndusInd.ws:voiceCall";
			SOAPEnvelope envelope = soapMessage.getSOAPPart().getEnvelope();
			SOAPHeader soapHeader = soapMessage.getSOAPHeader();
			envelope.removeNamespaceDeclaration(envelope.getPrefix());
			envelope.setPrefix(PREFERRED_PREFIX);
			envelope.addNamespaceDeclaration("ind", uri);
			soapHeader.setPrefix(PREFERRED_PREFIX);
			SOAPBody soapBody = soapMessage.getSOAPBody();
			soapBody.setPrefix(PREFERRED_PREFIX);

			SOAPBodyElement soapBodyElement = soapBody.addBodyElement(envelope.createQName("voiceCall", "ind"));
			SOAPElement voiceCallNode = soapBodyElement.addChildElement(envelope.createName("voiceCallReq"));
			SOAPElement msidn = voiceCallNode.addChildElement(envelope.createName("MSISDN"));
			msidn.addTextNode(mobileNumber);
			SOAPElement otpNode = voiceCallNode.addChildElement(envelope.createName("OTP"));
			otpNode.addTextNode("123456");
			SOAPElement refNode = voiceCallNode.addChildElement(envelope.createName("REF"));
			refNode.addTextNode("654321");
			SOAPElement sourceNode = voiceCallNode.addChildElement(envelope.createName("SourceSys"));
			sourceNode.addTextNode("OAO");
			soapMessage.saveChanges();

			printSoapRequest(soapMessage);

			SOAPConnectionFactory soapConFactory = SOAPConnectionFactory.newInstance();

			SOAPConnection connection = soapConFactory.createConnection();

			URL srurl = new URL(requestObject.getRequestUrl());

			SOAPMessage soapResponse = connection.call(soapMessage, srurl);

			printSoapRequest(soapResponse);

			String soapResp = "";
			ByteArrayOutputStream responseXML = new ByteArrayOutputStream();
			soapResponse.writeTo(responseXML);
			soapResp = responseXML.toString();

			String status = "";
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = factory.newDocumentBuilder();
			Document document = documentBuilder.parse(new InputSource(new StringReader(soapResp)));
			NodeList nodeList = document.getElementsByTagName("voiceCallResp");
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					NodeList childNode = element.getChildNodes();
					for (int j = 0; j < childNode.getLength(); j++) {
						Node eachChild = childNode.item(j);
						Element eachElement = (Element) eachChild;
						if (eachElement.getTagName().equalsIgnoreCase("status")) {
							status = eachElement.getTextContent();
						}
					}
				}
			}

			ResponseBody responseBody = new ResponseBody();
			responseBody.setResponseContentXML(status);
			responseObject.setResponseBody(responseBody);
		}

		catch (Exception e) {
			log.info("Exception Message for HttpAPIServiceIMPL :: ", e);
		}

		return responseObject;
	}

	@Override
	public JSONObject getviaAccountNo(String mobileNumber) {
		JSONObject jsonObj = new JSONObject();
		try {
			HttpClient httpclient = HttpClientBuilder.create().build();
			URI uri = new URIBuilder("https://ibluatapig.indusind.com/app/uat/emandateFlux/status?id=12345&mobileNo="
					+ mobileNumber + "&accountNo=706000016705").build();
			HttpGet httpGetRequest = new HttpGet(uri);

			if (includeHeader.equalsIgnoreCase("Yes")) {
				httpGetRequest.setHeader(secretidname, secretidvalue);
				httpGetRequest.setHeader(clientidname, clientidvalue);
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

			jsonObj.put("accounts", new JSONObject(sb.toString()));

		} catch (Exception e) {
			log.info("Exception in getViaAccountNo :: ", e);
		}
		return jsonObj;
	}

	
	@SuppressWarnings("null")
	@Override
	public JSONObject callCRMNext(Map<String,String> data, String action) {
		JSONObject finalResponse = null;
		try {
			String payload = new ObjectMapper().writeValueAsString(data);
			
			String postParams = "{\"data\":"+ payload+",\"action\":\""+action+"\"}";
			log.error("Values we passs for CRMNEXT {} {} {}",payload,action,postParams);
			URL obj = new URL(domainForCRM+"/bin/crmnext/crmallservices");
			HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
			postConnection.setDoInput(true);
			postConnection.setDoOutput(true);
			postConnection.setRequestMethod("POST");
			postConnection.setRequestProperty("Content-Length", Integer.toString(postParams.length() ));
			postConnection.setUseCaches(false);
			postConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			postConnection.setRequestProperty("X-AUTH-TOKEN","");
			postConnection.setRequestProperty("X-AUTH-SESSION","");
			
			if(domainForCRM.contains("localhost"))
				postConnection.setRequestProperty("Authorization","Basic YWRtaW46YWRtaW4=");
			
			OutputStreamWriter os = new OutputStreamWriter(postConnection.getOutputStream(),  StandardCharsets.UTF_8);
			log.info("Key passed= {}",postParams);
			os.write(postParams);
			os.flush();
			os.close();
			int responseCode = postConnection.getResponseCode();
			log.info("POST Response Code {}", responseCode);
			log.info("POST Response Message {}", postConnection.getResponseMessage());
			if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) { // success
				BufferedReader in = new BufferedReader(new InputStreamReader(postConnection.getInputStream()));
				String inputLine;
				StringBuilder response = new StringBuilder();
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				finalResponse = new JSONObject(response.toString());
				log.info("finalResponse status{}", finalResponse);
			}
			
		}catch (Exception e) {
			log.error("Exception in callCRMNext", e);
			return setResponse(ERROR, DEFAULT_ERROR_MSG);
		}
		return finalResponse;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.iib.platform.services.HttpAPIService#getviaAccountNo(java.lang.String)
	 */
	@Override
	public JSONObject callCancelMandate(String accountNumber) {
		
		JSONObject jsonObj = new JSONObject();
		
		JSONObject umrnrequest = new JSONObject();
		
		try {
			
			JSONObject headerJson = new JSONObject();
			long requestId;
			requestId = System.currentTimeMillis();
			
			headerJson.put("id", requestId);
			JSONArray umrnList = new JSONArray();
			String[] arr = accountNumber.split("[,]", 0);
			for(String myStr: arr) {
				umrnList.put(myStr);
			}
			
			JSONObject umrnListA = new JSONObject();
			umrnListA.put("umrnList", umrnList);
			umrnrequest.put("header", headerJson);	
			umrnrequest.put("request", umrnListA);
			
			HttpClient httpclient = HttpClientBuilder.create().build();
			URI uri = new URIBuilder(cancelurl).build();

			HttpPost httpPostRequest = new HttpPost(uri);

			if (includeHeader.equalsIgnoreCase("Yes")) {
				httpPostRequest.setHeader(secretidname, secretidvalue);
				httpPostRequest.setHeader(clientidname, clientidvalue);
			}

			log.info("Request URL>> {}", cancelurl);
			log.info("Request Json>> {}", umrnrequest);
			
			StringEntity requestEntity = new StringEntity(umrnrequest.toString(), ContentType.APPLICATION_JSON);

			httpPostRequest.setEntity(requestEntity);

			HttpResponse resp = httpclient.execute(httpPostRequest);
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

			log.info("Response Json>> {}", sb.toString());
			
			jsonObj.put("cancelResult", new JSONObject(sb.toString()));

			
		} catch (JSONException e) {
		log.error("Exeception in JSONException ",e);
		} catch (URISyntaxException e) {
			log.error("Exeception in URISyntaxException ",e);	
		} catch (ClientProtocolException e) {
			log.error("Exeception in ClientProtocolException ",e);
		} catch (IOException e) {
			log.error("Exeception in IOException ",e);
		}catch (Exception e) {
			log.error("Exeception in Exception ",e);
		}
		
		
		
		return jsonObj;
		
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.iib.platform.services.HttpAPIService#getviaAccountNo(java.lang.String)
	 */
	@Override
	public JSONObject getManageEnachDetails(String accountNumber) {

		JSONObject jsonObj = new JSONObject();
		try {
			HttpClient httpclient = HttpClientBuilder.create().build();
			URI uri = new URIBuilder(url).build();

			HttpPost httpPostRequest = new HttpPost(uri);

			if (includeHeader.equalsIgnoreCase("Yes")) {
				httpPostRequest.setHeader(secretidname, secretidvalue);
				httpPostRequest.setHeader(clientidname, clientidvalue);
			}

			JSONObject requestJsonWrapper = new JSONObject();
			JSONObject headerJson = new JSONObject(
					"{ \"sourceId\": \"1002\", \"mndAction\": \"ENQUIRY\", \"msgId\": \"\" }");
			JSONObject requestJson = new JSONObject();

			long randomNumber = (long) Math.floor(Math.random() * 9000000000000L) + 1000000000000L;

			headerJson.put("msgId", "USFB" + randomNumber);
			requestJson.put("actNum", accountNumber);
			requestJsonWrapper.put("header", headerJson);
			requestJsonWrapper.put("request", requestJson);
			log.info("Request Json>> {}", requestJsonWrapper);
			StringEntity requestEntity = new StringEntity(requestJsonWrapper.toString(), ContentType.APPLICATION_JSON);

			httpPostRequest.setEntity(requestEntity);

			HttpResponse resp = httpclient.execute(httpPostRequest);
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

			jsonObj.put("accounts", new JSONObject(sb.toString()));

			log.info("Result for Account number :-{} Result we get {} ", accountNumber, sb);

		} catch (Exception e) {
			log.info("Exception in getViaAccountNo :: ", e);
		}
		return jsonObj;
	}

	@Override
	public JSONObject getAccountViaDOB() {
		JSONObject jsonObj = new JSONObject();
		try {
			HttpClient httpclient = HttpClientBuilder.create().build();
			URI uri = new URIBuilder(
					"https://ibluatapig.indusind.com/app/uat/emandateFlux/status?id=12345&mobileNo=9448712684&accountNo=706000016705")
							.build();
			HttpGet httpGetRequest = new HttpGet(uri);

			if (includeHeader.equalsIgnoreCase("Yes")) {
				httpGetRequest.setHeader(secretidname, secretidvalue);
				httpGetRequest.setHeader(clientidname, clientidvalue);
			}

			HttpResponse resp =  httpclient.execute(httpGetRequest);
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

			jsonObj.put("accounts", new JSONObject(sb.toString()));

		} catch (Exception e) {
			log.info("Exception in getAccountsViaDOB :: ", e);
		}
		return jsonObj;
	}

	
	@Override
	public JSONObject getDetailsViaNewAPI(String mobile, String accountno, String requestId) {
		JSONObject jsonObj = new JSONObject();
		try {
			HttpClient httpclient = HttpClientBuilder.create().build();
			
			log.info("URL We heit to get in valyes :: {}", newurl+"?id="+requestId+"&mobileNo="+mobile+"&accountNo="+accountno);
		
			URI uri = new URIBuilder(newurl+"?id="+requestId+"&mobileNo="+mobile+"&accountNo="+accountno).build();
			HttpGet httpGetRequest = new HttpGet(uri);

			if (includeHeader.equalsIgnoreCase("Yes")) {
				httpGetRequest.setHeader(secretidname, secretidvalue);
				httpGetRequest.setHeader(clientidname, clientidvalue);
			}

			HttpResponse resp =  httpclient.execute(httpGetRequest);
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

			jsonObj.put("mandates", new JSONObject(sb.toString()));

		} catch (Exception e) {
			log.info("Exception in getNewMandateViaAccoiunt :: ", e);
		}
		return jsonObj;
	}

	
	@Override
	public ResponseObject callAPI(RequestObject requestObject) {
		return null;
	}

	private void printSoapRequest(SOAPMessage soapMessage) throws SOAPException, IOException {
		/** Print Request */
		String requestXmlString = "";
		ByteArrayOutputStream reqXml = new ByteArrayOutputStream();
		soapMessage.writeTo(reqXml);
		requestXmlString = reqXml.toString();
		Document reqDoc = convertStringToDocument(requestXmlString);
		String xmlOutput = convertDocumentToString(reqDoc, "2");
		log.info("{} :: SOAP API Request ", xmlOutput);
	}

	private static Document convertStringToDocument(String xmlStr) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			return builder.parse(new InputSource(new StringReader(xmlStr)));

		} catch (Exception e) {
			log.error("Exception in convertStringToDocument :: ", e);
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
			log.error("Exception in convertDocumentToString :: ", e);
		}
		return null;
	}
	
	public JSONObject setResponse(String key, Object value) {
		JSONObject jsonObject = null;
		jsonObject = new JSONObject();
		try {
			return jsonObject.put(key, value);
		} catch (JSONException e) {
			log.error("unable to add response item : ", e);
		}
		return jsonObject;

	}

}