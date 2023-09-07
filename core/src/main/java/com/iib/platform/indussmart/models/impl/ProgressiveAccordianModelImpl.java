package com.iib.platform.indussmart.models.impl;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;

import com.google.gson.Gson;
import com.iib.platform.common.objects.AccordianItem;
import com.iib.platform.common.objects.WCMComponent;
import com.iib.platform.common.utils.ComponentsUtil;
import com.iib.platform.indussmart.models.ProgressiveAccordianModel;

/**
 * Progressive Accordion Model Implementation
 *
 * @author TechChefz (TCZ Consulting LLP)
 *
 */

@Model(adaptables = Resource.class, adapters = ProgressiveAccordianModel.class)
public class ProgressiveAccordianModelImpl implements ProgressiveAccordianModel {

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
	private String[] accordianItems;

	List<AccordianItem> accordianItemList;

	@PostConstruct
	protected void init() {

		wcmComponent = ComponentsUtil.getComponentDetails("progressive-accordian", customHeight, customStyleClass,
				hideOnMobile, hideOnTablet);

		Gson gson = new Gson();
		accordianItemList = new LinkedList<>();
		int count = 0;
		for (String itemString : accordianItems) {
			count++;
			AccordianItem accordianItem = gson.fromJson(itemString, AccordianItem.class);
			accordianItem.setParsysId("pa-inner-par-" + count);

			accordianItemList.add(accordianItem);
		}

	}

	@Override
	public List<AccordianItem> getAccordianItems() {
		return accordianItemList;
	}

	@Override
	public WCMComponent getComponent() {
		return wcmComponent;
	}

}
