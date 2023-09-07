package com.iib.platform.indussmart.models.impl;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iib.platform.indussmart.models.FooterModel;

@Model(adaptables = Resource.class, adapters = FooterModel.class)
public class FooterModelImpl implements FooterModel {

	/** Logger. **/
	private static final Logger log = LoggerFactory.getLogger(FooterModelImpl.class);

	@Inject
	@Optional
	@Default(values = "true")
	private String includeLogo;

	@Inject
	@Optional
	@Default(values = "")
	private String brandLogoImage;

	@Inject
	@Optional
	@Default(values = "")
	private String brandLogoAltText;

	@Inject
	@Optional
	@Default(values = "")
	private String brandLogoUrl;

	@Inject
	@Optional
	@Default(values = "")
	private String footerText;

	@Inject
	@Optional
	@Default(values = "true")
	private String includeLinks;

	@Inject
	@Optional
	@Default(values = "true")
	private String includeSocialLinks;

	@PostConstruct
	public void init() {
		// To do
	}

	@Override
	public String getIncludeLogo() {
		return includeLogo;
	}

	@Override
	public String getBrandLogoImage() {
		return brandLogoImage;
	}

	@Override
	public String getBrandLogoAltText() {
		return brandLogoAltText;
	}

	@Override
	public String getBrandLogoUrl() {
		return brandLogoUrl;
	}

	@Override
	public String getFooterText() {
		return footerText;
	}

	@Override
	public String getIncludeLinks() {
		return includeLinks;
	}

	@Override
	public String getIncludeSocialLinks() {
		return includeSocialLinks;
	}

}
