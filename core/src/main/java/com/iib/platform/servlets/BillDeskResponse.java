package com.iib.platform.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.iib.platform.common.utils.EncryptDecrypt;

import com.iib.platform.services.config.BillDeskResponseServletConfig;

/**
 * BillDeskResponse Servlet
 *
 * @author ADS (Niket Goel)
 *
 */
@Component(service = { Servlet.class }, name = "BillDeskResponse", property = {
		Constants.SERVICE_DESCRIPTION + "=" + "BillDeskResponse Servlet",
		"sling.servlet.methods=" + HttpConstants.METHOD_GET, "sling.servlet.paths=" + "/bin/billDeskResponse" })
@Designate(ocd = BillDeskResponseServletConfig.class)
public class BillDeskResponse extends SlingAllMethodsServlet {

	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(BillDeskResponse.class);

	private String sorryUrl;
	private String thankyouUrl;

	@Activate
	protected void activate(BillDeskResponseServletConfig config) {
		log.info("BillDesk Response has been activated!");

		sorryUrl = config.getSorryPageUrl();
		thankyouUrl = config.getThankYouPageUrl();
	}

	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		try {
			String msg = "";

			String key = EncryptDecrypt.makeid();
			String vector = EncryptDecrypt.makeid();
			if (request.getParameter("msg") != null)
				msg = request.getParameter("msg");

			String temp = "message : " + msg;
			log.info(temp);
			String[] test = msg.split("\\|");
			String rAuthStatus = test[14];
			String txnRefNo = test[2];
			String bankRefNo = test[3];
			String txnAmt = test[17];
			log.info("{} {} {} {}", rAuthStatus, txnAmt, txnRefNo, bankRefNo);
			String encryptString = "";
			if (rAuthStatus.equals("0300")) {
				encryptString = test[10] + "|" + test[7] + "|" + test[11] + "|" + test[12] + "|" + "NA" + "|" + test[4]
						+ "|" + test[6] + "|" + "NA" + "|" + test[7] + "|Loan Installment Payment|" + test[0] + "|"
						+ "INDB0000009" + "|" + test[3] + "|" + test[1];
				encryptString = EncryptDecrypt.encrypt(key, vector, encryptString);
				response.sendRedirect(thankyouUrl + "?session=" + (key + vector + encryptString));
			} else {
				encryptString = test[24] + "|" + test[1];
				encryptString = EncryptDecrypt.encrypt(key, vector, encryptString);
				response.sendRedirect(sorryUrl + "?session=" + (key + vector + encryptString));
			}
		} catch (Exception e) {
			log.error("{}", e);
			out.println(e.getMessage());
		}

	}
}
