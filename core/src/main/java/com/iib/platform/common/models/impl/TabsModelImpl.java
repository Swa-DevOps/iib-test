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
import com.iib.platform.common.models.TabsModel;
import com.iib.platform.common.objects.AccordianItem;
import com.iib.platform.common.objects.WCMComponent;
import com.iib.platform.common.utils.ComponentsUtil;

@Model(adaptables = Resource.class, adapters = TabsModel.class)
public class TabsModelImpl implements TabsModel {
	/** Logger. **/
	
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
	private String[] tabItems;

	private List<AccordianItem> tabItemList;

	@PostConstruct
	public void init() {

		wcmComponent = ComponentsUtil.getComponentDetails("tabs", customHeight, customStyleClass, hideOnMobile,
				hideOnTablet);

		Gson gson = new Gson();
		tabItemList = new LinkedList<>();
		int count = 0;
		for (String itemString : tabItems) {
			AccordianItem tabItem = gson.fromJson(itemString, AccordianItem.class);
			if (tabItem != null) {
				count++;
				tabItem.setParsysId("accordianPar" + count);
				tabItemList.add(tabItem);
			}
		}
	}

	@Override
	public String getSectionHeading() {
		return sectionHeading;
	}

	@Override
	public List<AccordianItem> getTabItemList() {
		return tabItemList;
	}

	@Override
	public WCMComponent getComponent() {
		return wcmComponent;
	}

}