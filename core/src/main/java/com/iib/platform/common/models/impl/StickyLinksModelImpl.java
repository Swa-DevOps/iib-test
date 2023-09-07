package com.iib.platform.common.models.impl;

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
import com.iib.platform.common.models.StickyLinksModel;
import com.iib.platform.common.objects.MenuItem;
import com.iib.platform.common.objects.WCMComponent;
import com.iib.platform.common.utils.ComponentsUtil;
import com.iib.platform.common.utils.LinkUtil;

@Model(adaptables = Resource.class, adapters = StickyLinksModel.class)
public class StickyLinksModelImpl implements StickyLinksModel {

	Logger logger = LoggerFactory.getLogger(StickyLinksModelImpl.class);

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
	@Default(values = "false")
	private String hideByDefault;

	@Inject
	@Optional
	private String buttonText;

	@Inject
	@Default(values = "{}")
	private String[] listofLinks;

	@Inject
	@Optional
	private String viewAllText;

	@Inject
	@Optional
	private String viewAllLink;

	private List<MenuItem> links;

	@PostConstruct
	protected void init() {

		wcmComponent = ComponentsUtil.getComponentDetails("sticky-links", customHeight, customStyleClass, hideOnMobile,
				hideOnTablet);

		Gson gson = new Gson();

		links = new LinkedList<>();

		for (String linkString : listofLinks) {
			MenuItem menuItem = gson.fromJson(linkString, MenuItem.class);
			links.add(menuItem);
		}
	}

	@Override
	public String getHideByDefault() {
		return hideByDefault;
	}

	@Override
	public String getButtonText() {
		return buttonText;
	}

	@Override
	public List<MenuItem> getLinks() {
		return links;
	}

	@Override
	public String getViewAllText() {
		return viewAllText;
	}

	@Override
	public String getViewAllLink() {
		return LinkUtil.getFormattedURL(viewAllLink);
	}

	@Override
	public WCMComponent getComponent() {
		return wcmComponent;
	}

}