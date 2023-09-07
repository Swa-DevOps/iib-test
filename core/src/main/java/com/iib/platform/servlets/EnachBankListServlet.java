package com.iib.platform.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.json.JSONArray;
import org.json.JSONObject;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iib.platform.services.EmandateBankDataService;

@Component(immediate = true, service = { Servlet.class }, enabled = true, property = {
		Constants.SERVICE_DESCRIPTION + "=" + "Enach Bank List Servlet",
		"sling.servlet.methods=" + HttpConstants.METHOD_POST, "sling.servlet.paths=" + "/bin/bankList" })
public class EnachBankListServlet extends SlingAllMethodsServlet {

	private static final long serialVersionUID = 1L;

	/** Static Logger */
	private static Logger log = LoggerFactory.getLogger(EnachBankListServlet.class);

	@Reference
	transient EmandateBankDataService emandateBankDataService;

	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		JSONArray bankList = emandateBankDataService.getBankList();
		try {
			JSONObject json = new JSONObject();
			json.put("bankList", bankList);
			out.print(json);
		} catch (Exception e) {
			log.error("Exception in Enach Bank List Servlet :: ", e);
		}
		response.flushBuffer();

	}
}
