package com.iib.platform.services.impl;

import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iib.platform.services.CalculatorService;
import com.iib.platform.services.config.CalculatorServiceConfig;

/**
 * Calculator Service Implementation
 *
 * @author ADS (NIket Goel)
 *
 */
@Component(immediate = true, configurationPolicy = ConfigurationPolicy.REQUIRE, enabled = true, service = {
		CalculatorService.class }, property = { Constants.SERVICE_DESCRIPTION + "=Calculator Service Implementation" })
@Designate(ocd = CalculatorServiceConfig.class)
public class CalculatorServiceImpl implements CalculatorService {

	/* Logger */
	private static Logger log = LoggerFactory.getLogger(CalculatorServiceImpl.class);

	private double goalInflation;
	private double retirementCorpusReturn;
	private double retirementInflation;

	@Activate
	protected void activate(CalculatorServiceConfig config) {
		log.info("Service has been activated!");
		goalInflation = config.getInflation();
		retirementCorpusReturn = config.getRetirementCorpusReturn();
		retirementInflation = config.getRetirementInflation();
	}

	@Deactivate
	public void deactivate() {
		log.info("Service has been deactivated");
	}

	// Goal Calculations (Calculates Future Value)
	@Override
	public Double calFv(double cog, int yrs) {
		double inf = (goalInflation / 100);
		return (cog * (Math.pow((1 + inf), yrs)));
	}

	// Goal Calculations (Calculates One Time)
	@Override
	public Double calOneTime(double futureValue, double expectedReturn, int yrs) {
		double lumpsum = 0;
		double ret1 = expectedReturn / 100;
		lumpsum = (futureValue) / (Math.pow((1 + ret1), yrs));
		return lumpsum;
	}

	// Goal Calculations (Calculates SIP from Future Value)
	@Override
	public Double calSipFv(double futureValue, double expectedReturn, int yrs) {
		double ret1 = expectedReturn / 100;
		double first = Math.pow((1 + (ret1 / 12)), (yrs * 12)) - 1;
		double second = (ret1 / 12) / (first);
		double third = second * futureValue;
		double fourth = (1 / (1 + (ret1 / 12)));
		return third * fourth;
	}

	// Goal Calculations (Calculates Goal Value from Future Value)
	@Override
	public Double calGoalFv(double futureValue, int yrs) {
		double inf1 = (goalInflation / 100);
		return (futureValue / (Math.pow((1 + inf1), yrs)));
	}

	// Goal Calculations (Calculates Future Value from SIP)
	@Override
	public Double calFvSip(double systematic, double expectedReturn, int yrs) {
		double ret1 = expectedReturn / 100;
		int k = 12;
		return ((systematic * (Math.pow((1 + (ret1 / k)), (yrs * k)) - 1)) / (ret1 / k)) / (1 / (1 + (ret1 / k)));
	}

	// Goal Calculations (Calculates Future Value from One Time)
	@Override
	public Double calFvOneTime(double oneTime, double expectedReturn, int yrs) {
		double ret1 = expectedReturn / 100;
		return (oneTime * (Math.pow((1 + ret1), yrs)));
	}

	/* Systematic Investments Plan Calculations */
	@Override
	public Double calSiplumpsum(double monthlyInvst, double lumpsum, double expectedReturn, int yrs) {
		double ret1 = expectedReturn / 100;
		int yrs1 = yrs * 12;
		double compYrs = (ret1 / 12);
		double sipVaulue = ((((Math.pow((1 + compYrs), yrs1) - 1) / (compYrs)) * (monthlyInvst)) * (1 + compYrs));
		double fv = lumpsum * (Math.pow((1 + compYrs), yrs1));
		return (sipVaulue + fv);
	}

	@Override
	public Double calTotalInvst(double monthlyInvst, int yrs) {
		return (monthlyInvst * yrs * 12);

	}

	/* Retirement Plan Calculations */
	@Override
	public Double calRetirement(int currentAge, double monthlyExpense, int retirementAge, int yearsAfterRetirement) {
		double ret1 = retirementCorpusReturn / 100;
		double inf1 = retirementInflation / 100;
		double yearlyExpense = monthlyExpense * 12;
		int yearsToRetirement = (retirementAge - currentAge);
		double netReturns = (((1 + ret1) / (1 + inf1)) - 1);
		double oneYearFutureValue = (yearlyExpense * (Math.pow((1 + inf1), yearsToRetirement)));
		return (((1 - (Math.pow((1 + netReturns), (-yearsAfterRetirement)))) / netReturns) * (1 + netReturns)
				* oneYearFutureValue);
	}

	@Override
	public Double calRetSipFv(double totalRetirement, double investmentReturn, int currentAge, int retirementAge) {
		double ret1 = investmentReturn / 100;
		int yearsToRetirement = (retirementAge - currentAge);

		double first = Math.pow((1 + (ret1 / 12)), (yearsToRetirement * 12)) - 1;
		double second = (ret1 / 12) / (first);
		double third = second * totalRetirement;
		double fourth = (1 / (1 + (ret1 / 12)));
		return (third * fourth);
	}

	@Override
	public Double calRetOneTime(double totalRetirement, double investmentReturn, int currentAge, int retirementAge) {
		double lumpsum = 0;
		double ret1 = investmentReturn / 100;
		int yearsToRetirement = (retirementAge - currentAge);
		lumpsum = (totalRetirement) / (Math.pow((1 + ret1), yearsToRetirement));
		return lumpsum;
	}

	/* Prediction Calculations */
	// Goal Prediction
	@Override
	public Double calPreGoalSipFv(double costOfGoal, double ret, int yrs, int delayMonths) {
		double inf = (goalInflation / 100);
		double futureValue = costOfGoal * (Math.pow((1 + inf), yrs));
		double ret1 = ret / 100;
		double first = (Math.pow((1 + (ret1 / 12)), (yrs * 12 - delayMonths)) - 1);
		double second = (ret1 / 12) / first;
		double third = second * futureValue;
		double fourth = 1 / (1 + (ret1 / 12));
		double delaySip = third * fourth;
		return (delaySip * (yrs * 12 - delayMonths));
	}

	// SIP Prediction
	@Override
	public Double calPreSip(double monthlyInvst, double lumpsum, double ret, int yrs, int investedYears,
			double predictionRate) {
		double predictionInc = predictionRate / 100;
		double preMonthlyInvst = monthlyInvst + (monthlyInvst * predictionInc);
		double ret2 = ret / 100;
		double futureLumpsum = lumpsum * (Math.pow((1 + ret2 / 12), ((investedYears) * 12)));
		double ret1 = ((ret / 12) / 100);
		int preYrs = investedYears * 12;
		double sip = ((((Math.pow((1 + ret1), preYrs) - 1) / (ret1)) * (preMonthlyInvst)) * (1 + ret1));
		return (sip + futureLumpsum);
	}

	// Retirement Prediction
	@Override
	public Double calPreRetSipFv(int currentAge, int retirementAge, double totalRetirement, int predictionAge,
			double investmentReturn) {
		double ret2 = investmentReturn / 100;
		int yearsToRetirement = (retirementAge - currentAge);
		int preAge = (yearsToRetirement - predictionAge);
		double first2 = Math.pow((1 + (ret2 / 12)), (preAge * 12)) - 1;
		double second2 = (ret2 / 12) / (first2);
		double third2 = second2 * totalRetirement;
		double fourth2 = (1 / (1 + (ret2 / 12)));
		double sip2 = third2 * fourth2;
		return (sip2 * (preAge * 12));
	}
}