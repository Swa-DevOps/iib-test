package com.iib.platform.indussmart.models;

import java.util.List;

import org.osgi.annotation.versioning.ConsumerType;

import com.iib.platform.common.models.BaseComponentModel;
import com.iib.platform.common.objects.Calculator;

/**
 * Calculator Model
 *
 * @author ADS (niket goel)
 *
 */

@ConsumerType
public interface CalculatorModel extends BaseComponentModel {

	public List<Calculator> getYearNoItems();

	public List<Calculator> getRateItems();

	public List<Calculator> getDelayYearItems();

	public List<Calculator> getSipYearNoItems();

	public List<Calculator> getSipRateItems();

	public List<Calculator> getSipInvestedYearItems();

	public List<Calculator> getPredictionRateItems();

	public List<Calculator> getCurrentAgeItems();

	public List<Calculator> getPredictionAgeItems();

	public List<Calculator> getRetRateItems();

	public List<Calculator> getRetirementAgeItems();

	public List<Calculator> getYearsAfterRetirementItems();

	public String getGoalInvestUrl();

	public String getSipInvestUrl();

	public String getRetirementInvestUrl();

	public String getOpenInNewWindowGoal();

	public String getOpenInNewWindowSip();

	public String getOpenInNewWindowGoalRet();

}
