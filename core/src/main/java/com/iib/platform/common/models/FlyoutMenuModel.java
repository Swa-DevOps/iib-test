package com.iib.platform.common.models;

import org.apache.sling.api.resource.ResourceResolver;
import com.iib.platform.common.objects.FlyoutMenu;

public interface FlyoutMenuModel extends BaseComponentModel {

	public FlyoutMenu getFlyoutMenu();

	public FlyoutMenu getFlyoutMenu(ResourceResolver resourceResolver, String flyoutMenuPagePath);

}
