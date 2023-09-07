package com.iib.platform.indussmart.models;

import java.util.List;

import org.osgi.annotation.versioning.ConsumerType;

import com.iib.platform.common.models.BaseComponentModel;
import com.iib.platform.common.objects.CTA;

/**
 * Equity Funds Model
 *
 * @author TechChefz (TCZ Consulting LLP)
 *
 */
@ConsumerType
public interface EquityFundDetailModel extends BaseComponentModel {

	public String getTagHeading();

	public String getHeading();

	public String getDescription();

	public List<CTA> getCtaItemsList();
}
