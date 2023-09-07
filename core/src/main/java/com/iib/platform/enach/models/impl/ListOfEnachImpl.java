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
import com.iib.platform.enach.models.ListOfEnachModel;

/**
 * Sling Model Implementation of List Of E-Nach Model
 *
 * @author TechChefz (TCZ Consulting LLP)
 *
 */
@Model(adaptables = Resource.class, adapters = ListOfEnachModel.class)
public class ListOfEnachImpl implements ListOfEnachModel {

	Logger logger = LoggerFactory.getLogger(ListOfEnachImpl.class);

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
	@Default(values = "{}")
	private String[] enach;

	private List<Enach> enachItems;

	@PostConstruct
	protected void init() {

		Gson gson = new Gson();

		enachItems = new LinkedList<>();

		for (String itemString : enach) {
			Enach ench = gson.fromJson(itemString, Enach.class);
			enachItems.add(ench);
		}
	}

	@Override
	public List<Enach> getEnachItems() {
		return enachItems;
	}

	@Override
	public WCMComponent getComponent() {
		return wcmComponent;
	}
}
