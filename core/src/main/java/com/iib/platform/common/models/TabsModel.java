package com.iib.platform.common.models;

import java.util.List;
import com.iib.platform.common.objects.AccordianItem;
import com.iib.platform.common.objects.WCMComponent;

public interface TabsModel extends BaseComponentModel {

	public String getSectionHeading();

	public List<AccordianItem> getTabItemList();

	public WCMComponent getComponent();

}