package com.iib.platform.common.models.impl;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;

import com.iib.platform.common.models.BannerModel;
import com.iib.platform.common.objects.Banner;
import com.iib.platform.common.objects.Image;
import com.iib.platform.common.objects.WCMComponent;
import com.iib.platform.common.utils.ComponentsUtil;

/**
 * Sling Model Implementation of Banner Model
 *
 * @author ADS (Niket goel)
 *
 */
@Model(adaptables = Resource.class, adapters = BannerModel.class)
public class BannerModelImpl implements BannerModel {

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
	@Default(values = "image")
	private String backgroundType;

	@Inject
	@Optional
	private String backgroundImage;

	@Inject
	@Optional
	private String viewType;

	@Inject
	@Optional
	private String backgroundMobileImagePath;

	@Inject
	@Optional
	private String backgroundImageAltText;

	@Inject
	@Optional
	private String heading;

	@Inject
	@Optional
	@Default(values = "h2")
	private String headingType;

	@Inject
	@Optional
	private String description;

	@Inject
	@Optional
	private String contentAlign;

	@Inject
	@Optional
	@Default(values = "true")
	private String contentHideOnMobile;

	private Banner banner;

	@PostConstruct
	public void init() {

		wcmComponent = ComponentsUtil.getComponentDetails("banner", customHeight, customStyleClass, hideOnMobile,
				hideOnTablet);

		banner = new Banner();
		banner.setBackgroundType(backgroundType);

		if (backgroundType.equalsIgnoreCase("image")) {
			Image bgImage = new Image();
			bgImage.setSrcPath(backgroundImage);
			bgImage.setAltText(backgroundImageAltText);
			banner.setBackgroundImage(bgImage);
			banner.setBackgroundMobileImagePath(backgroundMobileImagePath);
		}

		banner.setHeading(heading);
		banner.setHeadingType(headingType);
		banner.setDescription(description);
		banner.setContentAlign(contentAlign);
		banner.setContentHideOnMobile(contentHideOnMobile);
	}

	@Override
	public WCMComponent getComponent() {
		return wcmComponent;
	}

	@Override
	public Banner getBanner() {
		return banner;
	}

	@Override
	public String getViewType() {
		return viewType;
	}

}
