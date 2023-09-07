package com.iib.platform.indussmart.models.impl;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;

import com.iib.platform.common.objects.WCMComponent;
import com.iib.platform.common.utils.ComponentsUtil;
import com.iib.platform.common.utils.LinkUtil;
import com.iib.platform.indussmart.models.HelpMeModel;

/**
 * HelpMe Model Implementation
 *
 * @author TechChefz (TCZ Consulting LLP)
 *
 */

@Model(adaptables = Resource.class, adapters = HelpMeModel.class)
public class HelpMeModelImpl implements HelpMeModel {

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
	@Default(values = "")
	private String labelName;

	@Inject
	@Optional
	@Default(values = "")
	private String labelPhoneNo;

	@Inject
	@Optional
	@Default(values = "")
	private String labelEmailId;

	@Inject
	@Optional
	@Default(values = "")
	private String namePlaceHolder;

	@Inject
	@Optional
	@Default(values = "")
	private String phonePlaceHolder;

	@Inject
	@Optional
	@Default(values = "")
	private String emailPlaceHolder;

	@Inject
	@Optional
	@Default(values = "")
	private String checkBoxText;

	@Inject
	@Optional
	@Default(values = "")
	private String buttonText;

	@Inject
	@Optional
	@Default(values = "")
	private String successUrl;

	@PostConstruct
	protected void init() {

		wcmComponent = ComponentsUtil.getComponentDetails("Help-Me", customHeight, customStyleClass, hideOnMobile,
				hideOnTablet);

	}

	@Override
	public String getSuccessUrl() {
		return LinkUtil.getFormattedURL(successUrl);
	}

	@Override
	public String getPhonePlaceHolder() {
		return phonePlaceHolder;
	}

	@Override
	public String getEmailPlaceHolder() {
		return emailPlaceHolder;
	}

	@Override
	public String getLabelName() {
		return labelName;
	}

	@Override
	public String getLabelPhoneNo() {
		return labelPhoneNo;

	}

	@Override
	public String getLabelEmailId() {
		return labelEmailId;
	}

	@Override
	public String getNamePlaceHolder() {
		return namePlaceHolder;
	}

	@Override
	public String getCheckBoxText() {
		return checkBoxText;
	}

	@Override
	public String getButtonText() {
		return buttonText;
	}

	@Override
	public WCMComponent getComponent() {
		return wcmComponent;
	}
}