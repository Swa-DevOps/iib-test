package com.iib.platform.common.models.impl;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.google.gson.Gson;
import com.iib.platform.common.models.FlyoutMenuModel;
import com.iib.platform.common.objects.FlyoutMenu;
import com.iib.platform.common.objects.MenuItem;
import com.iib.platform.common.objects.Teaser;
import com.iib.platform.common.objects.WCMComponent;
import com.iib.platform.common.utils.ComponentsUtil;

@Model(adaptables = Resource.class, adapters = FlyoutMenuModel.class)
public class FlyoutMenuModelImpl implements FlyoutMenuModel {

	private static final Logger log = LoggerFactory.getLogger(FlyoutMenuModelImpl.class);

	@Inject
	private ResourceResolver resourceResolver;

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
	private String includeQuicklinks;

	@Inject
	@Optional
	@Default(values = "false")
	private String includeTeasers;

	@Inject
	@Optional
	@Default(values = "manual-list")
	private String quicklinksMode;

	@Inject
	@Optional
	private String flyoutParentPagePath;

	@Inject
	@Optional
	private String iterationLevel;

	@Inject
	@Optional
	@Default(values = "{}")
	private String[] flyoutNavItems;

	@Inject
	@Optional
	@Default(values = "manual-list")
	private String teaserMode;

	@Inject
	@Optional
	private String teaserParentPagePath;

	@Inject
	@Optional
	@Default(values = "{}")
	private String[] flyoutTeaserItems;

	private FlyoutMenu flyoutMenu;

	@Override
	public FlyoutMenu getFlyoutMenu() {
		return flyoutMenu;
	}

	/**
	 * This method will construct the model after doing injections.
	 */
	@PostConstruct
	public void init() {

		wcmComponent = ComponentsUtil.getComponentDetails("flyout-menu", customHeight, customStyleClass, hideOnMobile,
				hideOnTablet);

		flyoutMenu = fetchFlyoutMenu();

	}

	@Override
	public FlyoutMenu getFlyoutMenu(ResourceResolver resourceResolver, String flyoutMenuPagePath) {
		FlyoutMenu flyoutMenu = new FlyoutMenu();

		Resource flyoutPageResource = resourceResolver.getResource(flyoutMenuPagePath);
		if ((flyoutPageResource != null) && (flyoutPageResource instanceof Resource)) {
			Page flyoutPage = flyoutPageResource.adaptTo(Page.class);
			if (flyoutPage != null) {
				Resource flyoutMenuResource = resourceResolver
						.getResource(flyoutPageResource.getPath() + "/jcr:content/flyoutMenu");
				if ((flyoutMenuResource != null) && (flyoutMenuResource instanceof Resource)) {
					ValueMap resourceProperties = flyoutMenuResource.adaptTo(ValueMap.class);

					if (resourceProperties != null) {
						includeQuicklinks = resourceProperties.get("includeQuicklinks", "false");
						includeTeasers = resourceProperties.get("includeTeasers", "false");
						quicklinksMode = resourceProperties.get("quicklinksMode", String.class);
						flyoutParentPagePath = resourceProperties.get("flyoutParentPagePath", String.class);
						iterationLevel = resourceProperties.get("iterationLevel", String.class);
						flyoutNavItems = resourceProperties.get("flyoutNavItems", String[].class);
						teaserMode = resourceProperties.get("teaserMode", String.class);
						teaserParentPagePath = resourceProperties.get("teaserParentPagePath", String.class);
						flyoutTeaserItems = resourceProperties.get("flyoutTeaserItems", String[].class);
					}

					flyoutMenu = fetchFlyoutMenu();
				}
			}
		}

		return flyoutMenu;
	}

	private FlyoutMenu fetchFlyoutMenu() {

		FlyoutMenu flyoutMenu = new FlyoutMenu();

		try {
			List<MenuItem> flyoutMenuList = new LinkedList<>();
			List<Teaser> flyoutTeaserList = new LinkedList<>();

			if (StringUtils.isNotEmpty(includeQuicklinks) && includeQuicklinks.equalsIgnoreCase("true")) {
				flyoutMenu.setIncludeQuicklinks("true");

				if (StringUtils.isNotEmpty(includeQuicklinks)) {
					if (quicklinksMode.equalsIgnoreCase("manual-list")) {
						Gson gson = new Gson();
						for (String itemString : flyoutNavItems) {
							MenuItem flyoutNavItem = gson.fromJson(itemString, MenuItem.class);
							if (StringUtils.isNotEmpty(flyoutNavItem.getSubFlyoutPagePath())) {
								}

							flyoutMenuList.add(flyoutNavItem);
						}
					} else if (quicklinksMode.equalsIgnoreCase("auto-list")) {
						Resource parentPageResource = resourceResolver.getResource(flyoutParentPagePath);

						if ((parentPageResource != null) && (parentPageResource instanceof Resource)) {
							Iterable<Resource> childResources = parentPageResource.getChildren();
							for (Resource childResource : childResources) {
								if (!childResource.getName().equalsIgnoreCase("jcr:content")) {
									Page childPage = childResource.adaptTo(Page.class);
									if (childPage != null) {
										ValueMap pageProperties = childPage.getProperties();

										MenuItem flyoutMenuItem = new MenuItem();
										flyoutMenuItem.setText(
												pageProperties.get("jcr:title", pageProperties.get("title", "")));
										flyoutMenuItem.setUrl(childPage.getPath());

										flyoutMenuList.add(flyoutMenuItem);
									}
								}
							}
						}
					}
				}

				flyoutMenu.setFlyoutMenuList(flyoutMenuList);
			}

			if (StringUtils.isNotEmpty(includeTeasers) && includeTeasers.equalsIgnoreCase("true")) {
				flyoutMenu.setIncludeTeasers("true");

				if (StringUtils.isNotEmpty(teaserMode) && teaserMode.equalsIgnoreCase("manual-list")) {
					for (String itemString : flyoutTeaserItems) {
						Gson gson = new Gson();
						Teaser teaserItem = gson.fromJson(itemString, Teaser.class);
						if (StringUtils.isNotBlank(teaserItem.getTeaserPagePath())) {
							Resource teaserPageResource = resourceResolver.getResource(teaserItem.getTeaserPagePath());

							if ((teaserPageResource != null) && (teaserPageResource instanceof Resource)) {
								Page teaserPage = teaserPageResource.adaptTo(Page.class);
								if (teaserPage != null) {
									ValueMap teaserPageProperties = teaserPage.getProperties();

									String pageTitle = teaserPageProperties.get("jcr:title",
											teaserPageProperties.get("title", ""));
									if (StringUtils.isEmpty(teaserItem.getTitle())) {
										teaserItem.setTitle(pageTitle);
									}

									String pageUrl = teaserPage.getPath();
									if (StringUtils.isEmpty(teaserItem.getCtaLink())) {
										teaserItem.setCtaLink(pageUrl);
									}

									String pageDescription = teaserPageProperties.get("jcr:description",
											teaserPageProperties.get("description", ""));
									if (StringUtils.isEmpty(teaserItem.getDescription())) {
										teaserItem.setDescription(pageDescription);
									}

									String pageImage = teaserPageProperties.get("teaserImage", String.class);
									if (StringUtils.isEmpty(teaserItem.getImagePath())) {
										teaserItem.setImagePath(pageImage);
									}

									String pageCTAText = teaserPageProperties.get("teaserCtaText", String.class);
									if (StringUtils.isEmpty(teaserItem.getCtaText())) {
										teaserItem.setCtaText(pageCTAText);
									}
								}
							}
						}

						flyoutTeaserList.add(teaserItem);
					}
				} else if (teaserMode.equalsIgnoreCase("auto-list")) {
					if (StringUtils.isNotEmpty(teaserParentPagePath)) {
						Resource teaserParentPageResource = resourceResolver.getResource(teaserParentPagePath);
						if ((teaserParentPageResource != null) && (teaserParentPageResource instanceof Resource)) {
							Iterable<Resource> childResources = teaserParentPageResource.getChildren();
							for (Resource childResource : childResources) {
								if (!childResource.getName().equalsIgnoreCase("jcr:content")) {
									Page childPage = childResource.adaptTo(Page.class);
									if (childPage != null) {
										ValueMap pageProperties = childPage.getProperties();

										Teaser teaser = new Teaser();
										teaser.setTitle(
												pageProperties.get("jcr:title", pageProperties.get("title", "")));
										teaser.setDescription(pageProperties.get("jcr:description",
												pageProperties.get("description", "")));
										teaser.setImagePath(pageProperties.get("teaserImage", String.class));
										teaser.setCtaText(pageProperties.get("teaserCtaText", String.class));
										teaser.setCtaLink(childPage.getPath());

										flyoutTeaserList.add(teaser);
									}
								}
							}
						}
					}
				}

				flyoutMenu.setTeaserList(flyoutTeaserList);

				if (flyoutTeaserList.size() == 1) {
					flyoutMenu.setTeaserViewMode("tile");
					flyoutMenu.setTeaserStyleClasses("col-xl-12 col-md-12 col-sm-12");
				} else if (flyoutTeaserList.size() == 2) {
					flyoutMenu.setTeaserViewMode("tile");
					flyoutMenu.setTeaserStyleClasses("col-xl-6 col-md-6 col-sm-12");
				} else if (flyoutTeaserList.size() == 3) {
					flyoutMenu.setTeaserViewMode("tile");
					flyoutMenu.setTeaserStyleClasses("col-xl-4 col-md-4 col-sm-12");
				} else if (flyoutTeaserList.size() > 3) {
					flyoutMenu.setTeaserViewMode("grid");
				}
			}
		} catch (Exception e) {
			log.error("Exception in fetchFlyoutMenu method in FlyoutMenuModelImpl :: " , e);
		}

		return flyoutMenu;
	}

	@Override
	public WCMComponent getComponent() {
		return wcmComponent;
	}

}
