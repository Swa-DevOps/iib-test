package com.iib.platform.indussmart.models;

import org.osgi.annotation.versioning.ConsumerType;

import com.iib.platform.common.models.BaseComponentModel;

@ConsumerType
public interface HelpMeModel extends BaseComponentModel {
	public String getLabelName();

	public String getLabelPhoneNo();

	public String getLabelEmailId();

	public String getNamePlaceHolder();

	public String getPhonePlaceHolder();

	public String getEmailPlaceHolder();

	public String getCheckBoxText();

	public String getButtonText();

	public String getSuccessUrl();
}
