package com.iib.platform.common.models.impl;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;


import com.iib.platform.common.models.TeaserModel;
import com.iib.platform.common.objects.Image;
import com.iib.platform.common.objects.WCMComponent;
import com.iib.platform.common.utils.ComponentsUtil;
import com.iib.platform.common.utils.LinkUtil;

/**
 * Sling Model Implementation of Teaser Model
 *
 * @author TechChefz (TCZ Consulting LLP)
 *
 */

@Model(adaptables = Resource.class, adapters = TeaserModel.class)
public class TeaserModelImpl implements TeaserModel {

	/** Logger. **/
	

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

	WCMComponent wcmComponent;

	@Inject
	@Optional
	private String imagePath;

	@Inject
	@Optional
	private String imageAltText;

	@Inject
	@Optional
	private String title;

	@Inject
	@Optional
	private String description;

	@Inject
	@Optional
	private String ctaText;

	@Inject
	@Optional
	private String ctaOpenInNewWindow;

	@Inject
	@Optional
	private String link;

	private Image image;

	@PostConstruct
	public void init() {

		wcmComponent = ComponentsUtil.getComponentDetails("teaser", customHeight, customStyleClass, hideOnMobile,
				hideOnTablet);

		image = new Image();
		image.setSrcPath(imagePath);
		image.setAltText(imageAltText);

	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public Image getImage() {
		return image;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public String getCtaText() {
		return ctaText;
	}

	@Override
	public String getCtaOpenInNewWindow() {
		return ctaOpenInNewWindow;
	}

	@Override
	public String getLink() {
		return LinkUtil.getFormattedURL(link);
	}

	@Override
	public WCMComponent getComponent() {
		return wcmComponent;
	}

}
