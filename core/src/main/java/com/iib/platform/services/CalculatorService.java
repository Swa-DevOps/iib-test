package com.iib.platform.services;

public interface CalculatorService {

	/* Goal Calculator Methods */
	public Double calFv(double cog, int yrs);

	public Double calSipFv(double futureValue, double expectedReturn, int yrs);

	public Double calOneTime(double futureValue, double expectedReturn, int yrs);

	public Double calGoalFv(double futureValue, int yrs);

	public Double calFvSip(double systematic, double expectedReturn, int yrs);

	public Double calFvOneTime(double oneTime, double expectedReturn, int yrs);

	/* SIP Methods */
	public Double calSiplumpsum(double monthyInvst, double lumpsum, double ret, int yrs);

	public Double calTotalInvst(double monthlyInvst, int yrs);

	/* Retirement Methods */
	public Double calRetirement(int currentAge, double monthlyExpense, int retirementAge, int yearsAfterRetirement);

	public Double calRetSipFv(double retirement, double investmentReturn, int currentAge, int retirementAge);

	public Double calRetOneTime(double totalRetirement, double investmentReturn, int currentAge, int retirementAge);

	/* Prediction Methods */
	public Double calPreGoalSipFv(double costOfGoal, double ret, int yrs, int delayMonths);

	public Double calPreSip(double monthlyInvst, double lumpsum, double ret, int yrs, int investedYears,
			double predictionRate);

	public Double calPreRetSipFv(int currentAge, int retirementAge, double totalRetirement, int predictionAge,
			double investmentReturn);

}