package com.iib.platform.indussmart.models.impl;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;

import com.google.gson.Gson;
import com.iib.platform.common.objects.Calculator;
import com.iib.platform.common.objects.WCMComponent;
import com.iib.platform.common.utils.LinkUtil;
import com.iib.platform.indussmart.models.CalculatorModel;

/**
 * Calculator Model Implementation
 *
 * @author ADS (Niket Goel)
 *
 */

@Model(adaptables = Resource.class, adapters = CalculatorModel.class)
public class CalculatorModelImpl implements CalculatorModel {

	@Inject
	@Optional
	private String customHeight;

	@Inject
	@Optional
	private String customStyleClass;

	@Inject
	@Optional
	private String hideOnMobile;

	@Inject
	@Optional
	private String hideOnTablet;

	@Inject
	@Optional
	@Default(values = "")
	private WCMComponent wcmComponent;

	@Inject
	@Optional
	@Default(values = "")
	private String goalInvestUrl;

	@Inject
	@Optional
	@Default(values = "")
	private String sipInvestUrl;

	@Inject
	@Optional
	@Default(values = "")
	private String retirementInvestUrl;

	@Inject
	@Optional
	@Default(values = "false")
	private String openInNewWindowGoal;

	@Inject
	@Optional
	@Default(values = "false")
	private String openInNewWindowSip;

	@Inject
	@Optional
	@Default(values = "false")
	private String openInNewWindowGoalRet;

	@Inject
	@Optional
	@Default(values = "{}")
	private String[] yearNos;

	@Inject
	@Optional
	@Default(values = "{}")
	private String[] rates;

	@Inject
	@Optional
	@Default(values = "{}")
	private String[] delayYears;

	@Inject
	@Optional
	@Default(values = "{}")
	private String[] sipYearNos;

	@Inject
	@Optional
	@Default(values = "{}")
	private String[] sipRates;

	@Inject
	@Optional
	@Default(values = "{}")
	private String[] sipInvestedYears;

	@Inject
	@Optional
	@Default(values = "{}")
	private String[] predictionRate;

	@Inject
	@Optional
	@Default(values = "{}")
	private String[] currentAges;

	@Inject
	@Optional
	@Default(values = "{}")
	private String[] retirementAges;

	@Inject
	@Optional
	@Default(values = "{}")
	private String[] yearsAfterRetirements;

	@Inject
	@Optional
	@Default(values = "{}")
	private String[] retRates;

	@Inject
	@Optional
	@Default(values = "{}")
	private String[] predictionAges;

	private List<Calculator> yearNoItems;
	private List<Calculator> rateItems;
	private List<Calculator> delayYearItems;

	private List<Calculator> sipYearNoItems;
	private List<Calculator> sipRateItems;
	private List<Calculator> sipInvestedYearItems;
	private List<Calculator> predictionRateItems;

	private List<Calculator> currentAgeItems;
	private List<Calculator> retirementAgeItems;
	private List<Calculator> yearsAfterRetirementItems;
	private List<Calculator> retRateItems;
	private List<Calculator> predictionAgeItems;

	@PostConstruct
	public void init() {

		yearNoItems = new LinkedList<>();
		rateItems = new LinkedList<>();
		delayYearItems = new LinkedList<>();
		sipYearNoItems = new LinkedList<>();
		sipRateItems = new LinkedList<>();
		sipInvestedYearItems = new LinkedList<>();
		predictionRateItems = new LinkedList<>();
		currentAgeItems = new LinkedList<>();
		retirementAgeItems = new LinkedList<>();
		yearsAfterRetirementItems = new LinkedList<>();
		retRateItems = new LinkedList<>();
		predictionAgeItems = new LinkedList<>();

		Gson gson = new Gson();

		for (String itemString : yearNos) {
			Calculator yrn = gson.fromJson(itemString, Calculator.class);
			yearNoItems.add(yrn);
		}

		for (String itemString : rates) {
			Calculator ret = gson.fromJson(itemString, Calculator.class);
			rateItems.add(ret);
		}
		for (String itemString : delayYears) {
			Calculator dy = gson.fromJson(itemString, Calculator.class);
			delayYearItems.add(dy);
		}

		/* SIP Years */
		for (String itemString : sipYearNos) {
			Calculator yrnSip = gson.fromJson(itemString, Calculator.class);
			sipYearNoItems.add(yrnSip);
		}

		for (String itemString : sipRates) {
			Calculator sipRet = gson.fromJson(itemString, Calculator.class);
			sipRateItems.add(sipRet);
		}

		for (String itemString : sipInvestedYears) {
			Calculator sipInvested = gson.fromJson(itemString, Calculator.class);
			sipInvestedYearItems.add(sipInvested);
		}

		for (String itemString : predictionRate) {
			Calculator predRate = gson.fromJson(itemString, Calculator.class);
			predictionRateItems.add(predRate);
		}

		/* Retirement Calculations */
		for (String itemString : currentAges) {
			Calculator ca = gson.fromJson(itemString, Calculator.class);
			currentAgeItems.add(ca);
		}

		for (String itemString : retirementAges) {
			Calculator ra = gson.fromJson(itemString, Calculator.class);
			retirementAgeItems.add(ra);
		}

		for (String itemString : yearsAfterRetirements) {
			Calculator yar = gson.fromJson(itemString, Calculator.class);
			yearsAfterRetirementItems.add(yar);
		}

		for (String itemString : retRates) {
			Calculator rr = gson.fromJson(itemString, Calculator.class);
			retRateItems.add(rr);
		}

		for (String itemString : predictionAges) {
			Calculator pa = gson.fromJson(itemString, Calculator.class);
			predictionAgeItems.add(pa);
		}

	}

	@Override
	public List<Calculator> getPredictionRateItems() {
		return predictionRateItems;
	}

	@Override
	public String getOpenInNewWindowGoal() {
		return openInNewWindowGoal;
	}

	@Override
	public String getOpenInNewWindowSip() {
		return openInNewWindowSip;
	}

	@Override
	public String getOpenInNewWindowGoalRet() {
		return openInNewWindowGoalRet;
	}

	@Override
	public String getGoalInvestUrl() {
		return LinkUtil.getFormattedURL(goalInvestUrl);
	}

	@Override
	public String getSipInvestUrl() {
		return LinkUtil.getFormattedURL(sipInvestUrl);
	}

	@Override
	public String getRetirementInvestUrl() {
		return LinkUtil.getFormattedURL(retirementInvestUrl);
	}

	@Override
	public List<Calculator> getRetirementAgeItems() {
		return retirementAgeItems;
	}

	@Override
	public List<Calculator> getRetRateItems() {
		return retRateItems;
	}

	@Override
	public List<Calculator> getPredictionAgeItems() {
		return predictionAgeItems;
	}

	@Override
	public List<Calculator> getYearNoItems() {
		return yearNoItems;
	}

	@Override
	public List<Calculator> getDelayYearItems() {
		return delayYearItems;
	}

	@Override
	public List<Calculator> getSipYearNoItems() {
		return sipYearNoItems;
	}

	@Override
	public List<Calculator> getSipRateItems() {
		return sipRateItems;
	}

	@Override
	public List<Calculator> getRateItems() {
		return rateItems;
	}

	@Override
	public List<Calculator> getSipInvestedYearItems() {
		return sipInvestedYearItems;
	}

	@Override
	public List<Calculator> getCurrentAgeItems() {
		return currentAgeItems;
	}

	@Override
	public List<Calculator> getYearsAfterRetirementItems() {
		return yearsAfterRetirementItems;
	}

	@Override
	public WCMComponent getComponent() {
		return wcmComponent;
	}

}
