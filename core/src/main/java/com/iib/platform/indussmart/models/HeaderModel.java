package com.iib.platform.indussmart.models;

import com.iib.platform.common.models.BaseComponentModel;

/**
 * Header Model
 *
 * @author TechChefz (TCZ Consulting LLP)
 *
 */

public interface HeaderModel extends BaseComponentModel {

	public String getIncludeLogo();

	public String getIncludeLogoIcon();

	public String getLogoIconUrl();

	public String getBrandLogoImage();

	public String getLogoIcon();

	public String getLogoIconAltText();

	public String getBrandLogoImageAltText();

	public String getBrandLogoUrl();

	public String getIncludeNavigation();

	public String getIncludeLogin();

	public String getIncludeSearch();

	public String getIncludeListOfLinks();

}
