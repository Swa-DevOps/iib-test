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
import com.iib.platform.common.models.ListOfTeasersModel;
import com.iib.platform.common.objects.Teaser;
import com.iib.platform.common.objects.WCMComponent;
import com.iib.platform.common.utils.ComponentsUtil;

/**
 * Sling Model Implementation of List Of Teasers Model
 *
 * @author TechChefz (TCZ Consulting LLP)
 *
 */
@Model(adaptables = Resource.class, adapters = ListOfTeasersModel.class)
public class ListOfTeasersModelImpl implements ListOfTeasersModel {

	Logger logger = LoggerFactory.getLogger(ListOfTeasersModelImpl.class);

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
	@Default(values = "{}")
	private String[] teasers;

	private List<Teaser> teaserItems;

	@PostConstruct
	protected void init() {

		wcmComponent = ComponentsUtil.getComponentDetails("list-of-teasers", customHeight, customStyleClass,
				hideOnMobile, hideOnTablet);

		Gson gson = new Gson();
		teaserItems = new LinkedList<>();

		for (String itemString : teasers) {
			Teaser teaser = gson.fromJson(itemString, Teaser.class);
			teaserItems.add(teaser);
		}
	}

	@Override
	public WCMComponent getComponent() {
		return wcmComponent;
	}

	@Override
	public String getSectionHeading() {
		return sectionHeading;
	}

	@Override
	public List<Teaser> getTeaserItems() {
		return teaserItems;
	}
}
