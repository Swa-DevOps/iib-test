package com.iib.platform.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.json.JSONObject;
import org.osgi.framework.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;

import com.iib.platform.api.request.RequestObject;
import com.iib.platform.api.response.ResponseBody;
import com.iib.platform.api.response.ResponseObject;
import com.iib.platform.services.HttpAPIService;
import com.iib.platform.services.config.SoapOnCallOTPConfig;

@Component(service = { Servlet.class }, name = "SOAP Generate OTP Servlet", property = {
		Constants.SERVICE_DESCRIPTION + "=" + "SOAP Generate OTP Servlet",
		"sling.servlet.methods=" + HttpConstants.METHOD_GET, "sling.servlet.paths=" + "/bin/soapCallOTP" })

@Designate(ocd = SoapOnCallOTPConfig.class)
public class SOAPGenerateOTPServlet extends SlingAllMethodsServlet {

	private static final long serialVersionUID = 1L;

	private static Logger log = LoggerFactory.getLogger(SOAPGenerateOTPServlet.class);

	@Reference
	transient HttpAPIService httpAPIService;

	private String endPointUrl;

	private String clientSecretKey;
	private String clientSecretValue;
	private String clientIdKey;
	private String clientIdValue;

	@Activate
	public void activate(SoapOnCallOTPConfig config) {
		log.info("Activated DigioCreateMandateServlet");

		this.endPointUrl = config.getOnCallAPIUrl();
		this.clientSecretKey = config.getClientSecretKey();
		this.clientSecretValue = config.getClientSecretValue();
		this.clientIdKey = config.getClientID();
		this.clientIdValue = config.getClientIDValue();
	}

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		String mobile = request.getParameter("mobile");
		try {
			String requestUrl = "";

			if (this.clientSecretKey.equalsIgnoreCase(""))
				requestUrl = endPointUrl;
			else
				requestUrl = endPointUrl + "?" + clientSecretKey + "=" + clientSecretValue + "&" + clientIdKey + "="
						+ clientIdValue;

			log.info("OTP on Call URL {}", requestUrl);

			RequestObject requestObject = new RequestObject();
			requestObject.setRequestUrl(requestUrl);
			ResponseObject responseObject = httpAPIService.getSOAPResponse(requestObject, mobile);
			ResponseBody body = responseObject.getResponseBody();
			String json = body.getResponseContentXML();

			log.info("OTP on Call Response for ENACH {}", json);

			JSONObject jsonObj = new JSONObject();
			jsonObj.put("status", json);
			out.println(jsonObj);
			out.flush();

		} catch (Exception e) {
			out.println("Servlet Exception :: " + e.getMessage());
		}

	}

}
