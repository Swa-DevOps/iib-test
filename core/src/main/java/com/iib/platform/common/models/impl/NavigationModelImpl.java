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
import com.iib.platform.common.models.NavigationModel;
import com.iib.platform.common.objects.MenuItem;
import com.iib.platform.common.objects.WCMComponent;
import com.iib.platform.common.utils.ComponentsUtil;

/**
 * Sling Model Implementation of Navigation Model
 *
 * @author TechChefz (TCZ Consulting LLP)
 *
 */
@Model(adaptables = Resource.class, adapters = NavigationModel.class)
public class NavigationModelImpl implements NavigationModel {

	
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
	private String hamburgerMenuText;

	@Inject
	@Optional
	@Default(values = "{}")
	private String[] navigationItems;

	private List<MenuItem> listItems;

	@PostConstruct
	public void init() {

		wcmComponent = ComponentsUtil.getComponentDetails("navigation", customHeight, customStyleClass, hideOnMobile,
				hideOnTablet);

		Gson gson = new Gson();
		listItems = new LinkedList<>();

		for (String itemString : navigationItems) {
			MenuItem singleItem = gson.fromJson(itemString, MenuItem.class);
			listItems.add(singleItem);
		}
	}

	@Override
	public WCMComponent getComponent() {
		return wcmComponent;
	}

	@Override
	public List<MenuItem> getListItems() {
		return listItems;
	}

	@Override
	public String getHamburgerMenuText() {
		return hamburgerMenuText;
	}

}
