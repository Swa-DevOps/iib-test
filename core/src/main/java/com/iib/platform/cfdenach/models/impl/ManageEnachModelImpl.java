package com.iib.platform.cfdenach.models.impl;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;

import com.iib.platform.common.utils.LinkUtil;
import com.iib.platform.cfdenach.models.ManageEnachModel;

/**
 * Sling Model Implementation of Manage Enach Model
 *
 * @author Ads Niket Goel
 *
 */
@Model(adaptables = Resource.class, adapters = ManageEnachModel.class)
public class ManageEnachModelImpl implements ManageEnachModel {

	
	@Inject
	@Optional
	@Default(values = "")
	private String backButtonUrl;

	@Inject
	@Optional
	@Default(values = "")
	private String buttonUrl;

	@PostConstruct
	public void init() {

		// to Do
	}

	@Override
	public String getBackButtonUrl() {
		return LinkUtil.getFormattedURL(backButtonUrl);
	}

	@Override
	public String getButtonUrl() {
		return LinkUtil.getFormattedURL(buttonUrl);
	}

}
