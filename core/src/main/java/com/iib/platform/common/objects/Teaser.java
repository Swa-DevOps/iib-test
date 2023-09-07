package com.iib.platform.common.objects;

import com.iib.platform.common.utils.LinkUtil;

/**
 * Teaser DTO
 *
 * @author TechChefz (TCZ Consulting LLP)
 *
 */
public class Teaser {

	private String teaserPagePath;
	private String imagePath;
	private String imageAltText;
	private String title;
	private String description;
	private String ctaText;
	private String ctaLink;
	private String ctaOpenInNewWindow;
	private String link;

	public String getLink() {
		return LinkUtil.getFormattedURL(link);
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getTeaserPagePath() {
		return teaserPagePath;
	}

	public void setTeaserPagePath(String teaserPagePath) {
		this.teaserPagePath = teaserPagePath;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getImageAltText() {
		return imageAltText;
	}

	public void setImageAltText(String imageAltText) {
		this.imageAltText = imageAltText;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCtaText() {
		return ctaText;
	}

	public void setCtaText(String ctaText) {
		this.ctaText = ctaText;
	}

	public String getCtaLink() {
		return LinkUtil.getFormattedURL(ctaLink);
	}

	public void setCtaLink(String ctaLink) {
		this.ctaLink = ctaLink;
	}

	public String getCtaOpenInNewWindow() {
		return ctaOpenInNewWindow;
	}

	public void setCtaOpenInNewWindow(String ctaOpenInNewWindow) {
		this.ctaOpenInNewWindow = ctaOpenInNewWindow;
	}

}
