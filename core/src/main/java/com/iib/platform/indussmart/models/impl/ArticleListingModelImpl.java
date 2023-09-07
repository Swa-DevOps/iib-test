package com.iib.platform.indussmart.models.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

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
import com.iib.platform.common.objects.PageObject;
import com.iib.platform.common.objects.WCMComponent;
import com.iib.platform.common.utils.ComponentsUtil;
import com.iib.platform.indussmart.models.ArticleListingModel;

/**
 * Sling Model Implementation of Article Listing Model
 *
 * @author ADS (Niket)
 *
 */
@Model(adaptables = Resource.class, adapters = ArticleListingModel.class)
public class ArticleListingModelImpl implements ArticleListingModel {

	/* Logger */
	private static Logger log = LoggerFactory.getLogger(ArticleListingModelImpl.class);

	@Inject
	ResourceResolver resourceResolver;

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

	@Inject
	@Optional
	@Default(intValues = 4)
	private int resultCount;

	@Inject
	@Optional
	private String shareText;

	@Inject
	@Optional
	@Default(values = "{}")
	private String[] articles;

	@Inject
	@Optional
	private String loadMoreText;

	@Inject
	@Optional
	@Default(values = "true")
	private String includeLoadMore;

	@Inject
	@Optional
	@Default(values = "manual")
	private String articlesListingType;

	@Inject
	@Optional
	private String parentPagePath;

	private List<PageObject> articleListItems;
	private WCMComponent wcmComponent;
	private PageObject pageObj;

	@PostConstruct
	public void init() {
		if (articlesListingType.equalsIgnoreCase("manual")) {
			try {

				wcmComponent = ComponentsUtil.getComponentDetails("article-listing", customHeight, customStyleClass,
						hideOnMobile, hideOnTablet);
				articleListItems = new LinkedList<>();

				Gson gson = new Gson();
				for (String itemString : articles) {
					pageObj = gson.fromJson(itemString, PageObject.class);
					articleListItems.add(pageObj);
					pageObj.setPageUrl(pageObj.getPagePath());

					Resource resource = resourceResolver.getResource(pageObj.getPagePath());
					if (resource instanceof Resource) {
						Page page = resource.adaptTo(Page.class);
						if (page != null) {
							ValueMap pageProperties = page.getProperties();
							setPageObj(pageProperties);

						}
					}
				}

			} catch (Exception e) {
				log.error("Exception in init method of ArticleListingModelImpl ::", e);
			}
		}

	}

	private void setPageObj(ValueMap pageProperties)

	{
		String title = "";
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("dd MMMM yyyy");

		title = pageProperties.get("title", String.class);
		if (title != null)
			pageObj.setTitle(pageProperties.get("jcr:title", title));

		pageObj.setPageCreationTime(pageProperties.get("jcr:created", Calendar.class));
		pageObj.setFormattedDate(formatter.format(pageObj.getPageCreationTime().getTime()));
		pageObj.setTagImagePath(pageProperties.get("tagImagePath", String.class));
		pageObj.setThumbnailImagePath(pageProperties.get("thumbnailImagePath", String.class));
		pageObj.setReadingTime(pageProperties.get("readingTime", String.class));

	}

	public int getResultCount() {
		return resultCount;
	}

	public String getShareText() {
		return shareText;
	}

	public String getLoadMoreText() {
		return loadMoreText;
	}

	public String getIncludeLoadMore() {
		return includeLoadMore;
	}

	public String getArticlesListingType() {
		return articlesListingType;
	}

	public String getParentPagePath() {
		return parentPagePath;
	}

	public List<PageObject> getArticleListItems() {
		return articleListItems;
	}

	@Override
	public WCMComponent getComponent() {
		return wcmComponent;
	}

}