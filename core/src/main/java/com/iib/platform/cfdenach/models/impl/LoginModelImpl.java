package com.iib.platform.cfdenach.models.impl;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;

import com.iib.platform.common.objects.WCMComponent;
import com.iib.platform.common.utils.ComponentsUtil;
import com.iib.platform.cfdenach.models.LoginModel;

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
	private String srcPath;

	@Inject
	@Optional
	private String hoverText;

	@Inject
	@Optional
	private String backButtonText;

	@Inject
	@Optional
	private String backButtonUrl;

	@Inject
	@Optional
	private String sectionHeading;

	@Inject
	@Optional
	private String subHeading;

	@Inject
	@Optional
	private String loginTitle;

	@Inject
	@Optional
	private String numberPlaceholder;

	@Inject
	@Optional
	private String otpPlaceholder;

	@Inject
	@Optional
	private String proceedButtonText;

	@Inject
	@Optional
	private String resendOTPText;

	@Inject
	@Optional
	private String sendOTPSmsText;

	@Inject
	@Optional
	private String sendOTPCallText;

	@PostConstruct
	public void init() {

		wcmComponent = ComponentsUtil.getComponentDetails("EnachLogin", customHeight, customStyleClass, hideOnMobile,
				hideOnTablet);

	}

	@Override
	public String getSrcPath() {
		return srcPath;
	}

	@Override
	public String getHoverText() {
		return hoverText;
	}

	@Override
	public String getBackButtonText() {
		return backButtonText;
	}

	@Override
	public String getBackButtonUrl() {
		return backButtonUrl;
	}

	@Override
	public String getSectionHeading() {
		return sectionHeading;
	}

	@Override
	public String getSubHeading() {
		return subHeading;
	}

	@Override
	public String getLoginTitle() {
		return loginTitle;
	}

	@Override
	public String getNumberPlaceholder() {
		return numberPlaceholder;
	}

	@Override
	public String getOtpPlaceholder() {
		return otpPlaceholder;
	}

	@Override
	public String getProceedButtonText() {
		return proceedButtonText;
	}

	@Override
	public String getResendOTPText() {
		return resendOTPText;
	}

	@Override
	public String getSendOTPSmsText() {
		return sendOTPSmsText;
	}

	@Override
	public String getSendOTPCallText() {
		return sendOTPCallText;
	}

	@Override
	public WCMComponent getComponent() {
		return wcmComponent;
	}

}
