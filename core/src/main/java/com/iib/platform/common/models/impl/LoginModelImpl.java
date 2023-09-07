package com.iib.platform.common.models.impl;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import com.iib.platform.common.models.LoginModel;
import com.iib.platform.common.objects.WCMComponent;

/**
 * Sling Model Implementation of Login Model
 *
 * @author TechChefz (TCZ Consulting LLP)
 *
 */

@Model(adaptables = Resource.class, adapters = LoginModel.class)
public class LoginModelImpl implements LoginModel {

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
	private String loginUrl;

	@Inject
	@Optional
	private String openInNewWindow;

	@Inject
	@Optional
	private String loginButtonText;

	@PostConstruct
	public void init() {

	}

	@Override
	public String getLoginUrl() {
		return loginUrl;
	}

	@Override
	public String getOpenInNewWindow() {
		return openInNewWindow;
	}

	@Override
	public String getLoginButtonText() {
		return loginButtonText;
	}

	@Override
	public WCMComponent getComponent() {
		return wcmComponent;
	}

}