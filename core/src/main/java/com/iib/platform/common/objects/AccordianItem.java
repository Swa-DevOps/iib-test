package com.iib.platform.common.objects;

import com.iib.platform.common.utils.LinkUtil;

/**
 * AccordianItem DTO
 *
 * @author TechChefz (TCZ Consulting LLP)
 *
 */
public class AccordianItem {

	private String title;
	private String styleClass;
	private String expanded;
	private String bodyType;
	private String bodyText;
	private String parsysId;
	private String image;
	private String description;
	private String imageUrl;

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

	public String getImageUrl() {
		return LinkUtil.getFormattedURL(imageUrl);
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getStyleClass() {
		return styleClass;
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	public String getExpanded() {
		return expanded;
	}

	public void setExpanded(String expanded) {
		this.expanded = expanded;
	}

	public String getBodyType() {
		return bodyType;
	}

	public void setBodyType(String bodyType) {
		this.bodyType = bodyType;
	}

	public String getBodyText() {
		return bodyText;
	}

	public void setBodyText(String bodyText) {
		this.bodyText = bodyText;
	}

	public String getParsysId() {
		return parsysId;
	}

	public void setParsysId(String parsysId) {
		this.parsysId = parsysId;
	}

}
