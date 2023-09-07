package com.iib.platform.common.models.impl;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;

import com.google.gson.Gson;
import com.iib.platform.common.models.CTAModel;
import com.iib.platform.common.objects.CTA;
import com.iib.platform.common.objects.WCMComponent;
import com.iib.platform.common.utils.ComponentsUtil;

/**
 * Sling Model Implementation of CTA Model Impl
 *
 * @author ADS (Niket Goel)
 *
 */
@Model(adaptables = Resource.class, adapters = CTA.class)
public class CTAModelImpl implements CTAModel {

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

	private WCMComponent wcmComponent;

	@Inject
	@Optional
	private String align;

	@Inject
	@Optional
	@Default(values = "{}")
	private String[] ctaItems;

	private List<CTA> ctaList;

	@PostConstruct
	public void init() {

		wcmComponent = ComponentsUtil.getComponentDetails("cta", customHeight, customStyleClass, hideOnMobile,
				hideOnTablet);

		Gson gson = new Gson();
		ctaList = new LinkedList<>();

		for (String itemString : ctaItems) {
			CTA ctaItem = gson.fromJson(itemString, CTA.class);
			if (ctaItem != null) {
				ctaList.add(ctaItem);
			}
		}
	}

	@Override
	public String getAlign() {
		return align;
	}

	@Override
	public List<CTA> getCtaList() {
		return ctaList;
	}

	@Override
	public WCMComponent getComponent() {
		return wcmComponent;
	}

}