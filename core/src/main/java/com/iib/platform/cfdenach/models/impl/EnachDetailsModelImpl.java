package com.iib.platform.cfdenach.models.impl;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;

import com.iib.platform.common.utils.LinkUtil;
import com.iib.platform.cfdenach.models.EnachDetailsModel;

/**
 * Sling Model Implementation of E-NACH Details Model
 *
 * @author TechChefz (TCZ Consulting LLP)
 *
 */
@Model(adaptables = Resource.class, adapters = EnachDetailsModel.class)
public class EnachDetailsModelImpl implements EnachDetailsModel {

	@Inject
	@Optional
	@Default(values = "")
	private String backButtonUrl;

	@PostConstruct
	public void init() {
		// to Do
	}

	@Override
	public String getBackButtonUrl() {
		return LinkUtil.getFormattedURL(backButtonUrl);
	}

}
