package com.iib.platform.common.objects;

import com.iib.platform.common.utils.LinkUtil;

/**
 * CTA DTO
 *
 * @author TechChefz (TCZ Consulting LLP)
 *
 */
public class CTA {

	private String type;
	private String text;
	private String url;
	private String icon;
	private String color;
	private String openInNewWindow;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getOpenInNewWindow() {
		return openInNewWindow;
	}

	public void setOpenInNewWindow(String openInNewWindow) {
		this.openInNewWindow = openInNewWindow;
	}

}