package com.iib.platform.indussmart.models.impl;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;

import com.iib.platform.common.objects.WCMComponent;
import com.iib.platform.common.utils.LinkUtil;
import com.iib.platform.indussmart.models.HeaderModel;

/**
 * Header Model Implementation
 *
 * @author TechChefz (TCZ Consulting LLP)
 *
 */

@Model(adaptables = Resource.class, adapters = HeaderModel.class)
public class HeaderModelImpl implements HeaderModel {

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
	@Default(values = "true")
	private String includeLogoIcon;

	@Inject
	@Optional
	@Default(values = "true")
	private String includeLogo;

	@Inject
	@Optional
	private String logoIcon;

	@Inject
	@Optional
	private String LogoIconAltText;

	@Inject
	@Optional
	private String logoIconUrl;

	@Inject
	@Optional
	private String brandLogoImage;

	@Inject
	@Optional
	private String brandLogoAltText;

	@Inject
	@Optional
	private String brandLogoUrl;

	@Inject
	@Optional
	@Default(values = "true")
	private String includeNavigation;

	@Inject
	@Optional
	@Default(values = "true")
	private String includeLogin;

	@Inject
	@Optional
	@Default(values = "true")
	private String includeSearch;

	@Inject
	@Optional
	@Default(values = "true")
	private String includeListOfLinks;

	@PostConstruct
	public void init() {

		// To Do

	}

	public String getIncludeLogoIcon() {
		return includeLogoIcon;
	}

	public String getLogoIconAltText() {
		return LogoIconAltText;
	}

	public String getLogoIconUrl() {
		return LinkUtil.getFormattedURL(logoIconUrl);
	}

	@Override
	public String getIncludeLogo() {
		return includeLogo;
	}

	@Override
	public String getLogoIcon() {
		return logoIcon;
	}

	@Override
	public String getBrandLogoImage() {
		return brandLogoImage;
	}

	@Override
	public String getBrandLogoImageAltText() {
		return brandLogoAltText;
	}

	@Override
	public String getBrandLogoUrl() {
		return LinkUtil.getFormattedURL(brandLogoUrl);
	}

	@Override
	public String getIncludeNavigation() {
		return includeNavigation;
	}

	@Override
	public String getIncludeLogin() {
		return includeLogin;
	}

	@Override
	public String getIncludeSearch() {
		return includeSearch;
	}

	@Override
	public WCMComponent getComponent() {
		return wcmComponent;
	}

	@Override
	public String getIncludeListOfLinks() {
		return includeListOfLinks;
	}

}
