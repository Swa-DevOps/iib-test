package com.iib.platform.enach.models.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;

import com.google.gson.Gson;
import com.iib.platform.common.objects.MenuItem;
import com.iib.platform.common.utils.LinkUtil;
import com.iib.platform.enach.models.CreateEnachModel;

/**
 * Sling Model Implementation of Create eNACH Model
 *
 * @author TechChefz (TCZ Consulting LLP)
 *
 */
@Model(adaptables = Resource.class, adapters = CreateEnachModel.class)
public class CreateEnachModelImpl implements CreateEnachModel {

	@Inject
	@Optional
	@Default(values = "")
	private String backButtonUrl;

	@Inject
	@Optional
	@Default(values = "")
	private String cancelButtonUrl;

	@Inject
	@Optional
	@Default(values = "{}")
	private String[] transferFrequency;

	private List<MenuItem> listItems;

	@PostConstruct
	public void init() {

		Gson gson = new Gson();
		listItems = new ArrayList<>();

		for (String itemString : transferFrequency) {
			MenuItem eachItem = gson.fromJson(itemString, MenuItem.class);
			listItems.add(eachItem);
		}
	}

	@Override
	public List<MenuItem> getListItems() {
		return listItems;
	}

	@Override
	public String getCancelButtonUrl() {
		return LinkUtil.getFormattedURL(cancelButtonUrl);
	}

	@Override
	public String getBackButtonUrl() {
		return LinkUtil.getFormattedURL(backButtonUrl);
	}

}