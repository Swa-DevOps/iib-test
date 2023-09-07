package com.iib.platform.common.objects;

/**
 * Breakpoint DTO
 *
 * @author TechChefz (TCZ Consulting LLP)
 *
 */
public class Breakpoint {

	private String label;
	private String minWidth;
	private String maxWidth;
	private String mediaQuery;
	private ImageRendition imageRendition;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getMinWidth() {
		return minWidth;
	}

	public void setMinWidth(String minWidth) {
		this.minWidth = minWidth;
	}

	public String getMaxWidth() {
		return maxWidth;
	}

	public void setMaxWidth(String maxWidth) {
		this.maxWidth = maxWidth;
	}

	public String getMediaQuery() {
		return mediaQuery;
	}

	public void setMediaQuery(String mediaQuery) {
		this.mediaQuery = mediaQuery;
	}

	public ImageRendition getImageRendition() {
		return imageRendition;
	}

	public void setImageRendition(ImageRendition imageRendition) {
		this.imageRendition = imageRendition;
	}
}