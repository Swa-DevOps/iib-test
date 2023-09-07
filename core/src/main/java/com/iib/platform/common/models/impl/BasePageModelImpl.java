package com.iib.platform.common.models.impl;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.Self;
import com.iib.platform.common.models.BasePageModel;

/**
 * Base Page Model Implementation
 *
 * @author ADS (Niket goel)
 *
 */

@Model(adaptables = Resource.class, adapters = BasePageModel.class)
public class BasePageModelImpl implements BasePageModel {

	/** Resource Injection. **/
	@Self
	@Inject
	protected Resource resource;

	@Inject
	ResourceResolver resourceResolver;

	@Inject
	@Optional
	@Named("jcr:title")
	private String title;

	@Inject
	@Optional
	private String hideInNav;

	@Inject
	@Optional
	private String pageTitle;

	@Inject
	@Optional
	private String subTitle;

	@Inject
	@Optional
	@Named("jcr:description")
	private String description;

	@Inject
	@Optional
	@Named("cq:template")
	private String template;

	@Inject
	@Optional
	@Named("jcr:language")
	private String language;

	@Inject
	@Optional
	@Named("cq:designPath")
	private String designPath;

	@Inject
	@Optional
	private String customKeywords;

	@Inject
	@Optional
	private String customHeadScript;

	@Inject
	@Optional
	private String customMetaTags;

	private String teaserImage;
	private String favIcon;
	private String siteName;
	private String keywords;

	@PostConstruct
	public void init() {
		// TO Do if required
	}

	@Override
	public String getSiteName() {
		return siteName;
	}

	@Override
	public String getLanguage() {
		return language;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public String getKeywords() {
		return keywords;
	}

	@Override
	public String getDesignPath() {
		return designPath;
	}

	@Override
	public String getCustomHeadScript() {
		return customHeadScript;
	}

	@Override
	public String getCustomMetaTags() {
		return customMetaTags;
	}

	@Override
	public String getTeaserImage() {
		return teaserImage;
	}

	@Override
	public String getFavIcon() {
		return favIcon;
	}

}