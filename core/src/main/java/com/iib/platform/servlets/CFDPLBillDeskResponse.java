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
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.granite.crypto.CryptoSupport;
import com.iib.platform.common.utils.EncryptDecrypt;
import com.iib.platform.services.DatabaseConnectionService;
import com.iib.platform.services.config.CFDPLBillDeskResponseServletConfig;

/**
 * BillDeskResponse Servlet
 *
 * @author ADS (Niket Goel)
 *
 */
@Component(service = { Servlet.class }, name = "CFDPL BillDeskResponse", property = {
		Constants.SERVICE_DESCRIPTION + "=" + "CFDPL BillDeskResponse Servlet",
		"sling.servlet.methods=" + HttpConstants.METHOD_GET, "sling.servlet.paths=" + "/bin/cfdpl/billDeskResponse" })
@Designate(ocd = CFDPLBillDeskResponseServletConfig.class)
public class CFDPLBillDeskResponse extends SlingAllMethodsServlet {

	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(CFDPLBillDeskResponse.class);

	private String sorryUrl;
	private String thankyouUrl;
	private String defaultResponsePage = "true";

	@Reference
	transient DatabaseConnectionService databaseConService;
	
	@Reference
    private transient CryptoSupport cryptoSupport;
	
	@Activate
	protected void activate(CFDPLBillDeskResponseServletConfig config) {
		log.info("CFDPL BillDesk Response has been activated!");

		sorryUrl = config.getSorryPageUrl();
		thankyouUrl = config.getThankYouPageUrl();
		defaultResponsePage=config.getDefaultResponsePage();
	}

	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		JSONObject success = new JSONObject();
		PrintWriter out = response.getWriter();
		try {
			String msg = "";
			if ((request.getParameter("msg") != null)) {
				msg = request.getParameter("msg");

			String temp = "message : " + msg;
			log.info("Response we get from the Bill desk :: {}",temp);
			String[] test = msg.split("\\|");
			String rAuthStatus = test[14];

			
			if (rAuthStatus.equals("0300")) {
				success.put("MerchantID",test[0]);
				success.put("CustomerID",test[1]);
				success.put("TxnReferenceNo",test[2]);
				success.put("BankReferenceNo",test[3]);
				success.put("TxnAmount",test[4]);
				success.put("BankID",test[5]);
				success.put("BankMerchantID",test[6]);
				success.put("TxnType",test[7]);
				success.put("CurrencyName",test[8]);
				success.put("ItemCode",test[9]);
				success.put("SecurityType",test[10]);
				success.put("SecurityID",test[11]);
				success.put("SecurityPassword",test[12]);
				success.put("TxnDate",test[13]);
				success.put("AuthStatus",test[14]);
				success.put("SettlementType",test[15]);
				success.put("AdditionalInfo1",test[16]);
				success.put("AdditionalInfo2",test[17]);
				success.put("AdditionalInfo3",test[18]);
				success.put("AdditionalInfo4",test[19]);
				success.put("AdditionalInfo5",test[20]);
				success.put("AdditionalInfo6",test[21]);
				success.put("AdditionalInfo7",test[22]);
				success.put("ErrorStatus",test[23]);
				success.put("ErrorDescription",test[24]);
				databaseConService.updateCFDplsession(test[21],cryptoSupport.protect(success.toString()),"Complete");
				
				if(defaultResponsePage.equalsIgnoreCase("true")) {
				
					String key = EncryptDecrypt.makeid();
					String vector = EncryptDecrypt.makeid();
					String encryptString = test[10] + "|" + test[7] + "|" + test[11] + "|" + test[12] + "|" + "NA" + "|" + test[4]
								+ "|" + test[6] + "|" + "NA" + "|" + test[7] + "|Loan Installment Payment|" + test[0] + "|"
								+ "INDB0000009" + "|" + test[3] + "|" + test[1];
						encryptString = EncryptDecrypt.encrypt(key, vector, encryptString);
						response.sendRedirect(thankyouUrl + "?session=" + (key + vector + encryptString));
					
				}else {	
				response.sendRedirect(thankyouUrl+"?"+test[21]);
				}
			} else {
				
				if(defaultResponsePage.equalsIgnoreCase("true")) {
					
					String key = EncryptDecrypt.makeid();
					String vector = EncryptDecrypt.makeid();
					String encryptString = test[24] + "|" + test[1];
					encryptString = EncryptDecrypt.encrypt(key, vector, encryptString);
					response.sendRedirect(sorryUrl + "?session=" + (key + vector + encryptString));
					
				}else {	
				success.put("ErrorStatus",test[23]);
				success.put("ErrorDescription",test[24]);
				databaseConService.updateCFDplsession(test[21],cryptoSupport.protect(success.toString()),"Failed");
				
				}
				response.sendRedirect(sorryUrl+"?"+test[21]+"&"+test[24]);
			}
			}else
			{
				response.sendRedirect(sorryUrl);
			}
		} catch (Exception e) {
			log.error("{}", e);
			out.println(e.getMessage());
			response.sendRedirect(sorryUrl);
		}

	}
}
