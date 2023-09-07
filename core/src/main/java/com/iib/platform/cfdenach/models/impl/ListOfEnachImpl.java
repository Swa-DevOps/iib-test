package com.iib.platform.cfdenach.models.impl;

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
import com.iib.platform.common.objects.CfdEnach;
import com.iib.platform.common.objects.WCMComponent;
import com.iib.platform.cfdenach.models.ListOfEnachModel;

/**
 * Sling Model Implementation of List Of E-Nach Model
 *
 * @author ADS (niket goel)
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

	private List<CfdEnach> enachItems;

	@PostConstruct
	protected void init() {

		Gson gson = new Gson();

		enachItems = new LinkedList<>();

		for (String itemString : enach) {
			CfdEnach ench = gson.fromJson(itemString, CfdEnach.class);
			enachItems.add(ench);
		}
	}

	@Override
	public List<CfdEnach> getEnachItems() {
		return enachItems;
	}

	@Override
	public WCMComponent getComponent() {
		return wcmComponent;
	}
}
