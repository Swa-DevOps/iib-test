package com.iib.platform.common.models;

import com.iib.platform.common.models.BaseComponentModel;

public interface LoginModel extends BaseComponentModel {

	public String getLoginUrl();

	public String getOpenInNewWindow();

	public String getLoginButtonText();

}