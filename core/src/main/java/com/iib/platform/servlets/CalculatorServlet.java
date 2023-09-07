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
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iib.platform.services.CalculatorService;

/**
 * Implementation of Calculator Servlet
 *
 * @author Niket
 *
 */
@Component(service = { Servlet.class }, name = "Calculator Servlet", immediate = true, property = {
		Constants.SERVICE_DESCRIPTION + "=Calculator Servlet", "sling.servlet.methods=" + HttpConstants.METHOD_POST,
		"sling.servlet.paths=" + "/bin/calculatorCheck" })
public class CalculatorServlet extends SlingAllMethodsServlet {

	private static final long serialVersionUID = 1L;

	private static Logger log = LoggerFactory.getLogger(CalculatorServlet.class);

	private static final String CALL_TYPE = "callType";
	@Reference
	transient CalculatorService calculatorService;

	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		try {
			// SIP Calculator
			if (request.getParameter(CALL_TYPE).equalsIgnoreCase("tab2")) {
				Double monthlyInvst = Double.parseDouble(request.getParameter("principal"));
				Integer principalyears = Integer.parseInt(request.getParameter("principalyears"));
				Double lumpsum = Double.parseDouble(request.getParameter("lumpsum"));
				Double principalrate = Double.parseDouble(request.getParameter("principalrate"));
				Double predictionRate = Double.parseDouble(request.getParameter("predictionRate"));
				Integer investedYears = Integer.parseInt(request.getParameter("investedYears"));

				Double totalInvst = calculatorService.calTotalInvst(monthlyInvst, principalyears);
				String totalInvstAsString = String.format("%.0f", totalInvst);

				Double sip = calculatorService.calSiplumpsum(monthlyInvst, lumpsum, principalrate, principalyears);
				String sipAsString = String.format("%.0f", sip);

				Double sipPrediction = calculatorService.calPreSip(monthlyInvst, lumpsum, principalrate, principalyears,
						investedYears, predictionRate);
				String sipPredictionAsString = String.format("%.0f", sipPrediction);

				JSONObject json = new JSONObject();
				json.put("totalInvstAsString", totalInvstAsString);
				json.put("oneTimeAsString", sipAsString);
				json.put("sipPredictionAsString", sipPredictionAsString);
				out.println(json);
				out.flush();
			}

			// Goal Calculator
			else if (request.getParameter(CALL_TYPE).equalsIgnoreCase("tab1")) {
				Double costofgoals = Double.parseDouble(request.getParameter("costofgoals"));
				Double rate = Double.parseDouble(request.getParameter("rate"));
				int years = Integer.parseInt(request.getParameter("years"));
				Integer delayMonths = Integer.parseInt(request.getParameter("delayYears"));

				Double futureValue = calculatorService.calFv(costofgoals, years);

				Double oneTime = calculatorService.calOneTime(futureValue, rate, years);
				String oneTimeAsString = String.format("%.0f", oneTime);

				Double systematic = calculatorService.calSipFv(futureValue, rate, years);
				String systematicAsString = String.format("%.0f", systematic);

				Double predictionValue = calculatorService.calPreGoalSipFv(costofgoals, rate, years, delayMonths);
				String predictionValueAsString = String.format("%.0f", predictionValue);

				JSONObject json = new JSONObject();
				json.put("systematicAsString", systematicAsString);
				json.put("oneTimeAsString", oneTimeAsString);
				json.put("predictionValueAsString", predictionValueAsString);
				out.println(json);
				out.flush();
			}

			// Retirement Calculator
			else if (request.getParameter(CALL_TYPE).equalsIgnoreCase("tab3")) {
				Integer currentAge = Integer.parseInt(request.getParameter("currentAge"));
				Double monthlyExpense = Double.parseDouble(request.getParameter("monthlyExpense"));
				Integer retirementAge = Integer.parseInt(request.getParameter("retirementAge"));
				Integer yearsAfterRetirement = Integer.parseInt(request.getParameter("yearsAfterRetirement"));
				Double investmentReturn = Double.parseDouble(request.getParameter("growthRate"));
				Integer predictionAge = Integer.parseInt(request.getParameter("predictionAge"));

				Double totalRetirement = calculatorService.calRetirement(currentAge, monthlyExpense, retirementAge,
						yearsAfterRetirement);
				String totalRetirementAsString = String.format("%.0f", totalRetirement);

				Double retirementSip = calculatorService.calRetSipFv(totalRetirement, investmentReturn, currentAge,
						retirementAge);
				String retirementSipAsString = String.format("%.0f", retirementSip);

				Double retirementOneTime = calculatorService.calRetOneTime(totalRetirement, investmentReturn,
						currentAge, retirementAge);
				String retirementOneTimeAsString = String.format("%.0f", retirementOneTime);

				Double predictionRet = calculatorService.calPreRetSipFv(currentAge, retirementAge, totalRetirement,
						predictionAge, investmentReturn);
				String predictionRetAsString = String.format("%.0f", predictionRet);

				JSONObject json = new JSONObject();
				json.put("totalRetirement", totalRetirementAsString);
				json.put("retirementSip", retirementSipAsString);
				json.put("retirementOneTime", retirementOneTimeAsString);
				json.put("predictionRet", predictionRetAsString);
				out.println(json);
				out.flush();
			}
		} catch (Exception e) {
			out.println(e);
			log.error("Calculator Servlet Exception:: {} ", e.getMessage());
		}

	}
}