package com.iib.platform.servlets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.util.HashMap;
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
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.json.JSONObject;
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

import com.iib.platform.services.SoapAPIService;
import com.iib.platform.services.config.HelpMeServletConfig;

/**
 * HelpMe API Servlet
 *
 * @author TechChefz (TCZ Consulting LLP)
 *
 */
@Component(service = { Servlet.class }, name = "HelpMe API Servlet", enabled = true, property = {
		"sling.servlet.methods" + "=" + HttpConstants.METHOD_POST, "sling.servlet.paths" + "=" + "/bin/helpMeApi" })
@Designate(ocd = HelpMeServletConfig.class)
public class HelpMeAPIServlet extends SlingAllMethodsServlet {

	private static final long serialVersionUID = 1l;
	private static Logger log = LoggerFactory.getLogger(HelpMeAPIServlet.class);

	@Reference
	transient SoapAPIService soapAPIService;

	private String endPointUrl;

	private String clientSecretKey;
	private String clientSecretValue;
	private String clientIdKey;
	private String clientIdValue;

	/** Request Body Parameters */
	private String accountNo;
	private String cifId;
	private String callRelatedTo;
	private String callType;
	private String callSubType;
	private String media;
	private String interactionState;
	private String team;
	private String subject;
	private String status;
	private String message;
	private String smsFlag;
	private String emailFlag;
	private String customerPropertyValue;
	private String mobilePropertyValue;
	private String emailPropertyValue;

	@Activate
	protected void activate(HelpMeServletConfig config) {
		log.info("Activated HelpMeAPIServlet");

		this.endPointUrl = config.getEndPointUrl();
		this.clientSecretKey = config.getClientSecretKey();
		this.clientSecretValue = config.getClientSecretValue();
		this.clientIdKey = config.getClientID();
		this.clientIdValue = config.getClientIDValue();

		this.accountNo = config.accountNo();
		this.cifId = config.cifId();
		this.callRelatedTo = config.callRelatedTo();
		this.callType = config.callType();
		this.callSubType = config.callSubType();
		this.media = config.media();
		this.interactionState = config.interactionState();
		this.team = config.team();
		this.subject = config.subject();
		this.status = config.status();
		this.message = config.message();
		this.smsFlag = config.smsFlag();
		this.emailFlag = config.emailFlag();
		this.customerPropertyValue = config.customerProperty();
		this.mobilePropertyValue = config.mobileProperty();
		this.emailPropertyValue = config.emailProperty();

	}

	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		String customerName = request.getParameter("customerName");
		String phoneNumber = request.getParameter("mobileNumber");
		String emailId = request.getParameter("emailId");

		// Help ME SOAP API Integration
		try {
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

			Text custNameNode = doc.createTextNode(customerName);
			Element property1 = doc.createElement("Property");
			String idKey1 = "ID";
			String idValue1 = customerPropertyValue;
			property1.setAttribute(idKey1, idValue1);
			property1.appendChild(custNameNode);
			talismaPropertiesDetails.appendChild(property1);

			Text mobileNumberNode = doc.createTextNode(phoneNumber);
			Element property2 = doc.createElement("Property");
			String idKey2 = "ID";
			String idKeyValue2 = mobilePropertyValue;
			property2.setAttribute(idKey2, idKeyValue2);
			property2.appendChild(mobileNumberNode);
			talismaPropertiesDetails.appendChild(property2);

			Text emailIdNode = doc.createTextNode(emailId);
			Element property3 = doc.createElement("Property");
			String idKey3 = "ID";
			String idKeyValue3 = emailPropertyValue;
			property3.setAttribute(idKey3, idKeyValue3);
			property3.appendChild(emailIdNode);
			talismaPropertiesDetails.appendChild(property3);

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

			SOAPElement sraccountNo = createServiceRequest.addChildElement(envelope.createQName("AccountNo", prefix));
			sraccountNo.addTextNode(this.accountNo);

			SOAPElement srcifid = createServiceRequest.addChildElement(envelope.createQName("CIF_ID", prefix));
			srcifid.addTextNode(cifId);

			SOAPElement srcallRelatedTo = createServiceRequest
					.addChildElement(envelope.createQName("CallRelatedTo", prefix));
			srcallRelatedTo.addTextNode(this.callRelatedTo);

			SOAPElement srcallType = createServiceRequest.addChildElement(envelope.createQName("CallType", prefix));
			srcallType.addTextNode(this.callType);

			SOAPElement srcallSubType = createServiceRequest.addChildElement(envelope.createQName("CallSubType", prefix));
			srcallSubType.addTextNode(this.callSubType);

			SOAPElement srmedia = createServiceRequest.addChildElement(envelope.createQName("Media", prefix));
			srmedia.addTextNode(this.media);

			SOAPElement srinteractionState = createServiceRequest
					.addChildElement(envelope.createQName("InteractionState", prefix));
			srinteractionState.addTextNode(this.interactionState);

			SOAPElement srteam = createServiceRequest.addChildElement(envelope.createQName("Team", prefix));
			srteam.addTextNode(this.team);

			SOAPElement srsubject = createServiceRequest.addChildElement(envelope.createQName("Subject", prefix));
			srsubject.addTextNode(this.subject);

			SOAPElement srmessage = createServiceRequest.addChildElement(envelope.createQName("Message", prefix));
			srmessage.addTextNode(this.message);

			SOAPElement srstatus = createServiceRequest.addChildElement(envelope.createQName("Status", prefix));
			srstatus.addTextNode(this.status);

			SOAPElement smsflag = createServiceRequest.addChildElement(envelope.createQName("SMS_Flag", prefix));
			smsflag.addTextNode(this.smsFlag);

			SOAPElement emailflag = createServiceRequest.addChildElement(envelope.createQName("Email_Flag", prefix));
			emailflag.addTextNode(this.emailFlag);

			SOAPElement xmlstr = createServiceRequest.addChildElement(envelope.createQName("XML_Str", prefix));
			CDATASection cdata = xmlstr.getOwnerDocument().createCDATASection(innerXml);
			xmlstr.appendChild(cdata);

			if (StringUtils.isNotBlank(clientSecretKey)) {
				soapMessage.getMimeHeaders().addHeader(clientSecretKey, clientSecretValue);
				soapMessage.getMimeHeaders().addHeader(clientIdKey, clientIdValue);
			} else {
				log.info("Blank Headers");
			}
			soapMessage.saveChanges();

			SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
			SOAPConnection soapConnection = soapConnectionFactory.createConnection();
			URL url = new URL(endPointUrl);
			SOAPMessage soapResponse = soapConnection.call(soapMessage, url);

			String responseXml = "";
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			soapResponse.writeTo(stream);
			responseXml = stream.toString();

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = factory.newDocumentBuilder();
			Document document = documentBuilder.parse(new InputSource(new StringReader(responseXml)));

			Map<String, String> resultParam = new HashMap<>();
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
						} else if (eachElement.getTagName() == "InteractionID") {
							resultParam.put("interactionId", eachElement.getTextContent());
						}
					}
				}
			}

			JSONObject jsonObj = new JSONObject();
			jsonObj.put("status", resultParam.get("status"));
			jsonObj.put("interactionId", resultParam.get("interactionId"));
			out.println(jsonObj);
			out.flush();

		} catch (Exception e) {
			log.info("Exception in HelpMe Servlet:: " , e);
		}
	}
}