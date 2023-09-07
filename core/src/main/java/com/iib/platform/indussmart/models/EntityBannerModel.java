package com.iib.platform.indussmart.models;

import java.util.List;

import com.iib.platform.common.models.BaseComponentModel;
import com.iib.platform.common.objects.Banner;
import com.iib.platform.common.objects.Image;
import com.iib.platform.common.objects.MenuItem;

public interface EntityBannerModel extends BaseComponentModel {

	public String getSectionHeading();

	public List<MenuItem> getBannerList();

	public Image getImage();

	public String getScrollToNext();

	public List<Banner> getBannerVideoItems();
}
