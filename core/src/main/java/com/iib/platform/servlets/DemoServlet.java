package com.iib.platform.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.iib.platform.services.CalculatorService;

/**
 * Demo Calculator Servlet
 *
 * @author
 *
 */
@Component(service = { Servlet.class }, property = { "sling.servlet.methods=" + HttpConstants.METHOD_GET,
		"sling.servlet.paths=" + "/bin/demoCal" })
public class DemoServlet extends SlingAllMethodsServlet {

	private static final long serialVersionUID = 1L;

	@Reference
	transient CalculatorService calculatorService;

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");

	}

}