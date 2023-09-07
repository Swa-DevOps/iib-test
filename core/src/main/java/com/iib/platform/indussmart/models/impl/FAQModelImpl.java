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
import com.iib.platform.common.objects.MenuItem;
import com.iib.platform.common.objects.WCMComponent;
import com.iib.platform.common.utils.ComponentsUtil;
import com.iib.platform.indussmart.models.FAQModel;

/**
 * Sling Model Implementation of FAQ Model
 *
 * @author TechChefz (TCZ Consulting LLP)
 *
 */
@Model(adaptables = Resource.class, adapters = FAQModel.class)
public class FAQModelImpl implements FAQModel {

	
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
	private String[] tabItems;

	private List<MenuItem> accordionItems;

	@PostConstruct
	public void init() {

		wcmComponent = ComponentsUtil.getComponentDetails("faq", customHeight, customStyleClass, hideOnMobile,
				hideOnTablet);

		Gson gson = new Gson();
		accordionItems = new LinkedList<>();
		int count = 0;

		for (String itemString : tabItems) {
			count++;
			MenuItem singleItem = gson.fromJson(itemString, MenuItem.class);
			accordionItems.add(singleItem);
			singleItem.setParsysId("rich-text-" + count);
		}
	}

	@Override
	public WCMComponent getComponent() {
		return wcmComponent;
	}

	@Override
	public List<MenuItem> getAccordionItems() {
		return accordionItems;
	}
}
