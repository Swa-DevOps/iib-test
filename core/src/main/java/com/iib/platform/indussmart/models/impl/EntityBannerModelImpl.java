package com.iib.platform.indussmart.models.impl;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.iib.platform.common.objects.Banner;
import com.iib.platform.common.objects.Image;
import com.iib.platform.common.objects.MenuItem;
import com.iib.platform.common.objects.WCMComponent;
import com.iib.platform.common.utils.ComponentsUtil;
import com.iib.platform.indussmart.models.EntityBannerModel;

/**
 * Sling Model Implementation of Entity Banner Model
 *
 * @author TechChefz (TCZ Consulting LLP)
 *
 */
@Model(adaptables = Resource.class, adapters = EntityBannerModel.class)
public class EntityBannerModelImpl implements EntityBannerModel {

	private static final Logger log = LoggerFactory.getLogger(EntityBannerModelImpl.class);

	@Inject
	@Optional
	private String sectionHeading;

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
	@Default(values = "false")
	private String scrollToNext;

	@Inject
	@Default(values = "{}")
	private String[] tabItems;

	@Inject
	@Default(values = "{}")
	private String[] bannerListItems;

	@Inject
	@Optional
	private String imagePath;

	@Inject
	@Optional
	private String imageAltText;

	@Inject
	@Optional
	private String heading;

	@Inject
	@Optional
	private String description;

	private List<MenuItem> bannerList;

	private List<Banner> bannerVideoItems;

	private Image image;

	@PostConstruct
	protected void init() {

		wcmComponent = ComponentsUtil.getComponentDetails("Entity-banner", customHeight, customStyleClass, hideOnMobile,
				hideOnTablet);
		log.info("inside banner entity");

		try {
			/* Scroll Image */
			image = new Image();
			image.setSrcPath(imagePath);
			image.setAltText(imageAltText);

			Gson gson = new Gson();

			bannerList = new LinkedList<>();
			for (String linkString : tabItems) {
				MenuItem menuItem = gson.fromJson(linkString, MenuItem.class);
				bannerList.add(menuItem);
			}

			bannerVideoItems = new LinkedList<>();
			for (String videoItem : bannerListItems) {
				Banner bannerItems = gson.fromJson(videoItem, Banner.class);
				bannerVideoItems.add(bannerItems);
			}
		} catch (Exception e) {
			log.info("exception" , e);
		}
	}

	@Override
	public String getSectionHeading() {
		return sectionHeading;
	}

	@Override
	public List<MenuItem> getBannerList() {
		return bannerList;
	}

	@Override
	public List<Banner> getBannerVideoItems() {
		return bannerVideoItems;
	}

	@Override
	public WCMComponent getComponent() {
		return wcmComponent;
	}

	@Override
	public String getScrollToNext() {
		return scrollToNext;
	}

	@Override
	public Image getImage() {
		return image;
	}

}