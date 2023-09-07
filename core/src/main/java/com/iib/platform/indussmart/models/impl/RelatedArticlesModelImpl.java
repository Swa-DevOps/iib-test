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
import com.day.cq.wcm.resource.details.AssetDetails;
import com.google.gson.Gson;
import com.iib.platform.common.objects.PageObject;
import com.iib.platform.common.objects.WCMComponent;
import com.iib.platform.common.utils.ComponentsUtil;
import com.iib.platform.indussmart.models.RelatedArticlesModel;

/**
 * Sling Model Implementation of Related Articles Model
 *
 * @author ADS (Niket Goel)
 *
 */
@Model(adaptables = Resource.class, adapters = RelatedArticlesModel.class)
public class RelatedArticlesModelImpl implements RelatedArticlesModel {

	/** Logger. **/
	private static final Logger log = LoggerFactory.getLogger(RelatedArticlesModelImpl.class);

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
	@Default(values = "")
	private String heading;

	@Inject
	@Optional
	private String shareText;

	@Inject
	@Optional
	@Default(values = "{}")
	private String[] articles;

	@Inject
	@Optional
	private String viewType;

	private List<PageObject> articlesList;
	private WCMComponent wcmComponent;

	@PostConstruct
	public void init() {
		try {
			PageObject pageObj;
			SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
			wcmComponent = ComponentsUtil.getComponentDetails("related-articles", customHeight, customStyleClass,
					hideOnMobile, hideOnTablet);
			articlesList = new LinkedList<>();

			Gson gson = new Gson();
			for (String itemString : articles) {
				pageObj = gson.fromJson(itemString, PageObject.class);
				articlesList.add(pageObj);
				pageObj.setPageUrl(pageObj.getPagePath());

				Resource resource = resourceResolver.getResource(pageObj.getPagePath());
				if (resource instanceof Resource) {
					Page page = resource.adaptTo(Page.class);

					if (page != null) {
						ValueMap pageProperties = page.getProperties();
						String title = pageProperties.get("title", String.class);
						if (title != null)
							pageObj.setTitle(pageProperties.get("jcr:title", title));
						pageObj.setPageCreationTime(pageProperties.get("jcr:created", Calendar.class));
						pageObj.setFormattedDate(formatter.format(pageObj.getPageCreationTime().getTime()));
						pageObj.setTagImagePath(pageProperties.get("tagImagePath", String.class));
						pageObj.setThumbnailImagePath(pageProperties.get("thumbnailImagePath", String.class));

						pageObj.setReadingTime(pageProperties.get("readingTime", String.class));

						Resource imageResource = resourceResolver.getResource(pageObj.getThumbnailImagePath());
						if ((imageResource instanceof Resource)) {
							AssetDetails assetDetails = new AssetDetails(imageResource);
							pageObj.setImageWidth(assetDetails.getWidth());
							pageObj.setImageHeight(assetDetails.getHeight());
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("Exception in init method of RelatedArticlesModelImpl : ", e);
		}
	}

	@Override
	public String getViewType() {
		return viewType;
	}

	@Override
	public String getShareText() {
		return shareText;
	}

	@Override
	public String getHeading() {
		return heading;
	}

	@Override
	public List<PageObject> getArticlesList() {
		return articlesList;
	}

	@Override
	public WCMComponent getComponent() {
		return wcmComponent;
	}

}
