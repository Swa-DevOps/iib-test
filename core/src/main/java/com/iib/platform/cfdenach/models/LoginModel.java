package com.iib.platform.cfdenach.models;

import org.osgi.annotation.versioning.ConsumerType;

import com.iib.platform.common.models.BaseComponentModel;

/**
 * Login Sling Model
 *
 * @author Ads Niket Goel
 *
 */
@ConsumerType
public interface LoginModel extends BaseComponentModel {

	public String getSrcPath();

	public String getHoverText();

	public String getBackButtonText();

	public String getBackButtonUrl();

	public String getSectionHeading();

	public String getSubHeading();

	public String getLoginTitle();

	public String getNumberPlaceholder();

	public String getOtpPlaceholder();

	public String getProceedButtonText();

	public String getResendOTPText();

	public String getSendOTPSmsText();

	public String getSendOTPCallText();

}