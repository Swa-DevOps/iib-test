package com.iib.platform.common.models;

import com.iib.platform.common.objects.WCMComponent;

public interface OverlayModel extends BaseComponentModel {

	public String getSectionHeading();

	public String getOpacity();

	public String getCloseIconClass();

	public WCMComponent getWcmComponent();

}
