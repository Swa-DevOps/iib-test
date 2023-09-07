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
import com.iib.platform.common.models.ListOfLinksModel;
import com.iib.platform.common.objects.MenuItem;
import com.iib.platform.common.objects.WCMComponent;
import com.iib.platform.common.utils.ComponentsUtil;

/**
 * Sling Model Implementation of List Of Links Model
 *
 * @author TechChefz (TCZ Consulting LLP)
 *
 */
@Model(adaptables = Resource.class, adapters = ListOfLinksModel.class)
public class ListOfLinksModelImpl implements ListOfLinksModel {

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
	@Default(values = "{}")
	private String[] listofLinks;

	private List<MenuItem> listItems;

	private WCMComponent wcmComponent;

	@PostConstruct
	public void init() {

		wcmComponent = ComponentsUtil.getComponentDetails("listoflinks", customHeight, customStyleClass, hideOnMobile,
				hideOnTablet);

		listItems = new LinkedList<>();

		Gson gson = new Gson();
		for (String itemString : listofLinks) {
			MenuItem menuItem = gson.fromJson(itemString, MenuItem.class);
			listItems.add(menuItem);
		}
	}

	@Override
	public List<MenuItem> getListItems() {
		return listItems;
	}

	@Override
	public WCMComponent getComponent() {
		return wcmComponent;
	}

}