package com.iib.platform.common.objects;

import java.util.Calendar;

import org.apache.sling.api.resource.ValueMap;

import com.iib.platform.common.utils.LinkUtil;

/**
 * Page Object DTO
 *
 * @author TechChefz (TCZ Consulting LLP)
 *
 */
public class PageObject {

	private String pageName;
	private String pageId;
	private String pagePath;
	private String pageUrl;
	private String title;
	private String description;
	private String summary;
	private String entity;
	private Calendar pageModifiedTime;
	private Calendar pageCreationTime;
	private Calendar pagePublishTime;
	private String modifiedBy;
	private String thumbnailImagePath;
	private Image thumbnailImage;
	private String thumbnailImageAltText;
	private String tags;
	private String socialTags;
	private String keywords;
	private String hideInNav;
	private String navTitle;
	private String customStyleClass;
	private String customScript;
	private String template;
	private String resourceType;
	private String imagePath;
	private String imageAlt;
	private String author;
	private String formattedDate;
	private long imageWidth;
	private long imageHeight;
	private String readingTime;
	private String tagImagePath;

	// Default Constructor
	public PageObject() {
	}

	// Parameterized Constuctor - Populate properties from ValueMap
	public PageObject(ValueMap properties, String path) {

		if (properties != null) {
			pageName = properties.get("jcr:title", properties.get("title", ""));
			pageId = properties.get("pageId", "");
			pagePath = path;
			pageUrl = LinkUtil.getFormattedURL(pagePath);
			title = properties.get("jcr:title", properties.get("title", ""));
			description = properties.get("jcr:description", properties.get("description", ""));
			summary = properties.get("subtitle", description);
			entity = properties.get("entity", "");
			pageModifiedTime = properties.get("cq:lastModified", Calendar.class);
			pageCreationTime = properties.get("jcr:created", Calendar.class);
			pagePublishTime = properties.get("cq:lastModified", Calendar.class);
			modifiedBy = properties.get("jcr:modifiedBy", properties.get("jcr:createdBy", ""));
			thumbnailImagePath = properties.get("thumbnailImagePath", properties.get("imagePath", ""));
			thumbnailImageAltText = properties.get("thumbnailImageAltText", properties.get("imageAlt", ""));

			thumbnailImage = new Image();
			thumbnailImage.setSrcPath(thumbnailImagePath);
			thumbnailImage.setAltText(thumbnailImageAltText);

			hideInNav = properties.get("hideInNav", "");
			navTitle = properties.get("navTitle", "");

			template = properties.get("cq:template", "");
			resourceType = properties.get("sling:resourceType", "");

			readingTime = properties.get("readingTime", "");
		}
	}

	public String getTagImagePath() {
		return tagImagePath;
	}

	public void setTagImagePath(String tagImagePath) {
		this.tagImagePath = tagImagePath;
	}

	public long getImageHeight() {
		return imageHeight;
	}

	public void setImageHeight(long imageHeight) {
		this.imageHeight = imageHeight;
	}

	public long getImageWidth() {
		return imageWidth;
	}

	public void setImageWidth(long imageWidth) {
		this.imageWidth = imageWidth;
	}

	public String getFormattedDate() {
		return formattedDate;
	}

	public void setFormattedDate(String formattedDate) {
		this.formattedDate = formattedDate;
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public String getPagePath() {
		return pagePath;
	}

	public void setPagePath(String pagePath) {
		this.pagePath = pagePath;
	}

	public String getPageUrl() {
		return LinkUtil.getFormattedURL(pageUrl);
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
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

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public Calendar getPageModifiedTime() {
		return pageModifiedTime;
	}

	public void setPageModifiedTime(Calendar pageModifiedTime) {
		this.pageModifiedTime = pageModifiedTime;
	}

	public Calendar getPageCreationTime() {
		return pageCreationTime;
	}

	public void setPageCreationTime(Calendar pageCreationTime) {
		this.pageCreationTime = pageCreationTime;
	}

	public Calendar getPagePublishTime() {
		return pagePublishTime;
	}

	public void setPagePublishTime(Calendar pagePublishTime) {
		this.pagePublishTime = pagePublishTime;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getThumbnailImagePath() {
		return thumbnailImagePath;
	}

	public void setThumbnailImagePath(String thumbnailImagePath) {
		this.thumbnailImagePath = thumbnailImagePath;
	}

	public Image getThumbnailImage() {
		return thumbnailImage;
	}

	public void setThumbnailImage(Image thumbnailImage) {
		this.thumbnailImage = thumbnailImage;
	}

	public String getThumbnailImageAltText() {
		return thumbnailImageAltText;
	}

	public void setThumbnailImageAltText(String thumbnailImageAltText) {
		this.thumbnailImageAltText = thumbnailImageAltText;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getSocialTags() {
		return socialTags;
	}

	public void setSocialTags(String socialTags) {
		this.socialTags = socialTags;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getHideInNav() {
		return hideInNav;
	}

	public void setHideInNav(String hideInNav) {
		this.hideInNav = hideInNav;
	}

	public String getNavTitle() {
		return navTitle;
	}

	public void setNavTitle(String navTitle) {
		this.navTitle = navTitle;
	}

	public String getCustomStyleClass() {
		return customStyleClass;
	}

	public void setCustomStyleClass(String customStyleClass) {
		this.customStyleClass = customStyleClass;
	}

	public String getCustomScript() {
		return customScript;
	}

	public void setCustomScript(String customScript) {
		this.customScript = customScript;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getImageAlt() {
		return imageAlt;
	}

	public void setImageAlt(String imageAlt) {
		this.imageAlt = imageAlt;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getReadingTime() {
		return readingTime;
	}

	public void setReadingTime(String readingTime) {
		this.readingTime = readingTime;
	}

}