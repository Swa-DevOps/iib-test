package com.iib.platform.indussmart.models;

import java.util.List;

import org.osgi.annotation.versioning.ConsumerType;

import com.iib.platform.common.models.BaseComponentModel;
import com.iib.platform.common.objects.PageObject;

/**
 * Article Listing Sling Model
 *
 * @author TechChefz (TCZ Consulting LLP)
 *
 */
@ConsumerType
public interface ArticleListingModel extends BaseComponentModel {

	public int getResultCount();

	public String getShareText();

	public String getLoadMoreText();

	public String getIncludeLoadMore();

	public String getArticlesListingType();

	public String getParentPagePath();

	public List<PageObject> getArticleListItems();

}
