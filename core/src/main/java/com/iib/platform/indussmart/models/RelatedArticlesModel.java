package com.iib.platform.indussmart.models;

import java.util.List;

import com.iib.platform.common.models.BaseComponentModel;
import com.iib.platform.common.objects.PageObject;

/**
 * Related Articles Model
 *
 * @author TechChefz (TCZ Consulting LLP)
 *
 */
public interface RelatedArticlesModel extends BaseComponentModel {

	public String getHeading();

	public List<PageObject> getArticlesList();

	public String getShareText();

	public String getViewType();

}