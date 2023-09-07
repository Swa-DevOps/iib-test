package com.iib.platform.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URI;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.json.JSONObject;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iib.platform.common.utils.EncryptDecrypt;
import com.iib.platform.services.config.ReCaptchaServletConfig;

@Component(service = { Servlet.class }, name = "Re-Captcha Servlet", property = {
		Constants.SERVICE_DESCRIPTION + "=" + "Re-Captcha Servlet", "sling.servlet.methods=" + HttpConstants.METHOD_GET,
		"sling.servlet.paths=" + "/bin/reCaptcha" })
@Designate(ocd = ReCaptchaServletConfig.class)
public class ReCaptchaServlet extends SlingAllMethodsServlet {

	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(ReCaptchaServlet.class);

	private String secret;

	@Activate
	public void activate(ReCaptchaServletConfig config) {
		log.info("Activated ReCaptchaServlet");

		secret = config.getSecretKey();
	}

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
		String remoteip = request.getRemoteAddr();

		try {
			HttpClient httpclient = HttpClientBuilder.create().build();
			String rJsonString = gRecaptchaResponse;
			URI uri = new URIBuilder("https://google.com/recaptcha/api/siteverify").addParameter("secret", secret)
					.addParameter("response", rJsonString).addParameter("remoteip", remoteip).build();
			HttpGet httpGetRequest = new HttpGet(uri);

			HttpResponse resp = httpclient.execute(httpGetRequest);
			HttpEntity entity = resp.getEntity();

			InputStream instream = entity.getContent();
			BufferedReader br = new BufferedReader(new InputStreamReader(instream));

			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			JSONObject json = new JSONObject(sb.toString());
			String encryptKey=EncryptDecrypt.makeid();
			EncryptDecrypt.encrypt(encryptKey, encryptKey, json.toString());
			out.println(encryptKey+EncryptDecrypt.encrypt(encryptKey, encryptKey, json.toString()));
		}

		catch (Exception e) {
			log.error("Recaptcah serverle", e);
		}
	}
}
