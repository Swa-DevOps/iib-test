package com.iib.platform.enach.models.impl;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.iib.platform.common.objects.Enach;
import com.iib.platform.common.objects.WCMComponent;
import com.iib.platform.enach.models.BenifitsOfEnachModel;

/**
 * Sling Model Implementation of Benifits of E-Nach Model
 *
 * @author ADS (Niket goel)
 *
 */
@Model(adaptables = Resource.class, adapters = BenifitsOfEnachModel.class)
public class BenifitsOfEnachImpl implements BenifitsOfEnachModel {

	Logger logger = LoggerFactory.getLogger(BenifitsOfEnachImpl.class);

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
	private String sectionHeading;

	@Inject
	@Optional
	private String title;

	@Inject
	@Optional
	private String description;

	@Inject
	@Optional
	@Default(values = "{}")
	private String[] benifits;

	private List<Enach> benifitItems;

	@PostConstruct
	protected void init() {

		Gson gson = new Gson();

		benifitItems = new LinkedList<>();

		for (String itemString : benifits) {
			Enach ench = gson.fromJson(itemString, Enach.class);
			benifitItems.add(ench);
		}
	}

	public String getSectionHeading() {
		return sectionHeading;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public List<Enach> getBenifitItems() {
		return benifitItems;
	}

	@Override
	public WCMComponent getComponent() {
		return wcmComponent;
	}
}
