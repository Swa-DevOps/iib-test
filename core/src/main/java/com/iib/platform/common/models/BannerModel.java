package com.iib.platform.common.models;

import com.iib.platform.common.objects.Banner;

/**
 * Banner Model
 *
 * @author TechChefz (TCZ Consulting LLP)
 *
 */
public interface BannerModel extends BaseComponentModel {

	public Banner getBanner();

	public String getViewType();

}
