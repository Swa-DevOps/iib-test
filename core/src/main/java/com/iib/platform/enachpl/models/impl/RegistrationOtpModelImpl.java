package com.iib.platform.enachpl.models.impl;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;

import com.iib.platform.common.utils.LinkUtil;
import com.iib.platform.enachpl.models.RegistrationOtpModel;

@Model(adaptables = Resource.class, adapters = RegistrationOtpModel.class)
public class RegistrationOtpModelImpl implements RegistrationOtpModel {

	@Inject
	@Optional
	@Default(values = "")
	private String cancelUrl;

	@PostConstruct
	public void init() {
		// to do
	}

	@Override
	public String getCancelUrl() {
		return LinkUtil.getFormattedURL(cancelUrl);
	}
}
