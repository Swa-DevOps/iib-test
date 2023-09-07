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
import com.iib.platform.common.objects.CTA;
import com.iib.platform.common.objects.WCMComponent;
import com.iib.platform.common.utils.ComponentsUtil;
import com.iib.platform.indussmart.models.EquityFundDetailModel;

/**
 * Sling Model Implementation of Equity Fund Detail Model
 *
 * @author TechChefz (TCZ Consulting LLP)
 *
 */
@Model(adaptables = Resource.class, adapters = EquityFundDetailModel.class)
public class EquityFundDetailModelImpl implements EquityFundDetailModel {


	@Inject
	@Optional
	private String tagHeading;

	@Inject
	@Optional
	private String heading;

	@Inject
	@Optional
	private String description;

	@Inject
	@Optional
	@Default(values = "{}")
	private String[] ctaItems;

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

	private List<CTA> ctaItemsList;

	private WCMComponent wcmComponent;

	@PostConstruct
	protected void init() {

		wcmComponent = ComponentsUtil.getComponentDetails("equityDetail", customHeight, customStyleClass, hideOnMobile,
				hideOnTablet);

		Gson gson = new Gson();
		ctaItemsList = new LinkedList<>();

		for (String linkString : ctaItems) {
			CTA item = gson.fromJson(linkString, CTA.class);
			ctaItemsList.add(item);

		}
	}

	@Override
	public String getTagHeading() {
		return tagHeading;
	}

	@Override
	public String getHeading() {
		return heading;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public List<CTA> getCtaItemsList() {
		return ctaItemsList;
	}

	@Override
	public WCMComponent getComponent() {
		return wcmComponent;
	}
}
