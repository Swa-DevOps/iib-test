package com.iib.platform.common.objects;

import java.util.List;
import com.iib.platform.common.utils.LinkUtil;

/**
 * MenuItem DTO
 *
 * @author TechChefz (TCZ Consulting LLP)
 *
 */
public class MenuItem {

	private String text;
	private String value;
	private String url;
	private String imagePath;
	private String imageAltText;
	private String tag;
	private Image image;
	private String iconType;
	private String iconClass;
	private String isCurrent;
	private String mode;
	private String parentPagePath;
	private String flyoutPagePath;
	private String subFlyoutPagePath;
	private FlyoutMenu flyoutMenu;
	private List<MenuItem> subLinks;
	private String openInNewWindow;
	private String linkType;
	private String socialLinkType;
	private String title;
	private String description;
	private String sectionHeading;
	private String ctaText;
	private String ctaUrl;
	private String ctaType;
	private String ctaColor;
	private String summary;
	private String subsectionTitle;
	private String subsectionDescription;
	private String subsectionIconClass;
	private String parsysId;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getParsysId() {
		return parsysId;
	}

	public void setParsysId(String parsysId) {
		this.parsysId = parsysId;
	}

	public String getSubsectionIconClass() {
		return subsectionIconClass;
	}

	public void setSubsectionIconClass(String subsectionIconClass) {
		this.subsectionIconClass = subsectionIconClass;
	}

	public String getSubsectionTitle() {
		return subsectionTitle;
	}

	public void setSubsectionTitle(String subsectionTitle) {
		this.subsectionTitle = subsectionTitle;
	}

	public String getSubsectionDescription() {
		return subsectionDescription;
	}

	public void setSubsectionDescription(String subsectionDescription) {
		this.subsectionDescription = subsectionDescription;
	}

	public String getCtaText() {
		return ctaText;
	}

	public void setCtaText(String ctaText) {
		this.ctaText = ctaText;
	}

	public String getCtaUrl() {
		return LinkUtil.getFormattedURL(ctaUrl);
	}

	public void setCtaUrl(String ctaUrl) {
		this.ctaUrl = ctaUrl;
	}

	public String getCtaType() {
		return ctaType;
	}

	public void setCtaType(String ctaType) {
		this.ctaType = ctaType;
	}

	public String getCtaColor() {
		return ctaColor;
	}

	public void setCtaColor(String ctaColor) {
		this.ctaColor = ctaColor;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getSectionHeading() {
		return sectionHeading;
	}

	public void setSectionHeading(String sectionHeading) {
		this.sectionHeading = sectionHeading;
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

	public String getSocialLinkType() {
		return socialLinkType;
	}

	public void setSocialLinkType(String socialLinkType) {
		this.socialLinkType = socialLinkType;
	}

	public String getLinkType() {
		return linkType;
	}

	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUrl() {
		return LinkUtil.getFormattedURL(url);
	}

	public void setUrl(String url) {
		this.url = url;
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

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getIconClass() {
		return iconClass;
	}

	public void setIconClass(String iconClass) {
		this.iconClass = iconClass;
	}

	public String getIconType() {
		return iconType;
	}

	public void setIconType(String iconType) {
		this.iconType = iconType;
	}

	public String getIsCurrent() {
		return isCurrent;
	}

	public void setIsCurrent(String isCurrent) {
		this.isCurrent = isCurrent;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getParentPagePath() {
		return parentPagePath;
	}

	public void setParentPagePath(String parentPagePath) {
		this.parentPagePath = parentPagePath;
	}

	public String getFlyoutPagePath() {
		return flyoutPagePath;
	}

	public void setFlyoutPagePath(String flyoutPagePath) {
		this.flyoutPagePath = flyoutPagePath;
	}

	public String getSubFlyoutPagePath() {
		return subFlyoutPagePath;
	}

	public void setSubFlyoutPagePath(String subFlyoutPagePath) {
		this.subFlyoutPagePath = subFlyoutPagePath;
	}

	public FlyoutMenu getFlyoutMenu() {
		return flyoutMenu;
	}

	public void setFlyoutMenu(FlyoutMenu flyoutMenu) {
		this.flyoutMenu = flyoutMenu;
	}

	public List<MenuItem> getSubLinks() {
		return subLinks;
	}

	public void setSubLinks(List<MenuItem> subLinks) {
		this.subLinks = subLinks;
	}

	public String getOpenInNewWindow() {
		return openInNewWindow;
	}

	public void setOpenInNewWindow(String openInNewWindow) {
		this.openInNewWindow = openInNewWindow;
	}

}