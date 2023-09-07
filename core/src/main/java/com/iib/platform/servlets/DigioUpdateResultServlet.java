package com.iib.platform.servlets;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iib.platform.services.DatabaseConnectionService;

@Component(service = { Servlet.class }, name = "DigioUpdateResultServlet", enabled = true, property = {
		Constants.SERVICE_DESCRIPTION + "=" + "DigioUpdate Result Servlet",
		"sling.servlet.methods=" + HttpConstants.METHOD_POST, "sling.servlet.paths=" + "/bin/digioUpdateResult" })

public class DigioUpdateResultServlet extends SlingAllMethodsServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1794975739474007150L;
	private static Logger log = LoggerFactory.getLogger(DigioUpdateResultServlet.class);
	private static final String MSG = "message";
	@Reference
	transient DatabaseConnectionService dcs;

	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {

		String requestId = request.getParameter("requestId");
		String eMandateId = request.getParameter("eMandateId");
		String talismaId = request.getParameter("talismaId");
		String message = request.getParameter(MSG);
		try {
			JSONObject obj = new JSONObject();
			boolean result = dcs.updateDigioResult(requestId, eMandateId, talismaId, message);
			if (result) {
				response.getWriter().write(obj.put(MSG, "success").toString());

			} else {
				response.getWriter().write(obj.put(MSG, "fail").toString());
			}
		} catch (JSONException e) {
			log.error("Json Exception Occured {}", e.getMessage());
		}
	}

}
