package com.iib.platform.common.models;

import org.osgi.annotation.versioning.ConsumerType;

/**
 * Base Page Model
 *
 * @author TechChefz (TCZ Consulting LLP)
 *
 */
@ConsumerType
public interface BasePageModel {

	public String getSiteName();

	public String getLanguage();

	public String getTitle();

	public String getDescription();

	public String getKeywords();

	public String getDesignPath();

	public String getCustomHeadScript();

	public String getCustomMetaTags();

	public String getTeaserImage();

	public String getFavIcon();

}
