package com.iib.platform.common.objects;

import java.util.List;

/**
 * Banner DTO
 *
 * @author TechChefz (TCZ Consulting LLP)
 *
 */
public class Banner {

	private String backgroundType;
	private String backgroundImagePath;
	private String backgroundImageAltText;
	private Image backgroundImage;
	private String backgroundMobileImagePath;
	private Image backgroundMobileImage;
	private String backgroundVideoPath;
	private Video backgroundVideo;
	private String videoIconImage;
	private String openInNewWindow;
	private String video;
	private String summary;
	private String title;
	private String heading;
	private String headingType;
	private String subHeading;
	private String description;
	private String imagePath;
	private Image image;
	private String contentAlign;
	private String contentHideOnMobile;
	private List<CTA> ctaItems;
	private String customStyleClass;
	private String scrollToNext;
	private String scrollToPrevious;

	public String getOpenInNewWindow() {
		return openInNewWindow;
	}

	public void setOpenInNewWindow(String openInNewWindow) {
		this.openInNewWindow = openInNewWindow;
	}

	public String getVideoIconImage() {
		return videoIconImage;
	}

	public void setVideoIconImage(String videoIconImage) {
		this.videoIconImage = videoIconImage;
	}

	public String getBackgroundType() {
		return backgroundType;
	}

	public void setBackgroundType(String backgroundType) {
		this.backgroundType = backgroundType;
	}

	public String getBackgroundImagePath() {
		return backgroundImagePath;
	}

	public void setBackgroundImagePath(String backgroundImagePath) {
		this.backgroundImagePath = backgroundImagePath;
	}

	public String getBackgroundImageAltText() {
		return backgroundImageAltText;
	}

	public void setBackgroundImageAltText(String backgroundImageAltText) {
		this.backgroundImageAltText = backgroundImageAltText;
	}

	public Image getBackgroundImage() {
		return backgroundImage;
	}

	public void setBackgroundImage(Image backgroundImage) {
		this.backgroundImage = backgroundImage;
	}

	public String getBackgroundMobileImagePath() {
		return backgroundMobileImagePath;
	}

	public void setBackgroundMobileImagePath(String backgroundMobileImagePath) {
		this.backgroundMobileImagePath = backgroundMobileImagePath;
	}

	public Image getBackgroundMobileImage() {
		return backgroundMobileImage;
	}

	public void setBackgroundMobileImage(Image backgroundMobileImage) {
		this.backgroundMobileImage = backgroundMobileImage;
	}

	public String getBackgroundVideoPath() {
		return backgroundVideoPath;
	}

	public void setBackgroundVideoPath(String backgroundVideoPath) {
		this.backgroundVideoPath = backgroundVideoPath;
	}

	public Video getBackgroundVideo() {
		return backgroundVideo;
	}

	public void setBackgroundVideo(Video backgroundVideo) {
		this.backgroundVideo = backgroundVideo;
	}

	public String getHeading() {
		return heading;
	}

	public void setHeading(String heading) {
		this.heading = heading;
	}

	public String getHeadingType() {
		return headingType;
	}

	public void setHeadingType(String headingType) {
		this.headingType = headingType;
	}

	public String getSubHeading() {
		return subHeading;
	}

	public void setSubHeading(String subHeading) {
		this.subHeading = subHeading;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public String getContentAlign() {
		return contentAlign;
	}

	public void setContentAlign(String contentAlign) {
		this.contentAlign = contentAlign;
	}

	public String getContentHideOnMobile() {
		return contentHideOnMobile;
	}

	public void setContentHideOnMobile(String contentHideOnMobile) {
		this.contentHideOnMobile = contentHideOnMobile;
	}

	public List<CTA> getCtaItems() {
		return ctaItems;
	}

	public void setCtaItems(List<CTA> ctaItems) {
		this.ctaItems = ctaItems;
	}

	public String getCustomStyleClass() {
		return customStyleClass;
	}

	public void setCustomStyleClass(String customStyleClass) {
		this.customStyleClass = customStyleClass;
	}

	public String getScrollToNext() {
		return scrollToNext;
	}

	public void setScrollToNext(String scrollToNext) {
		this.scrollToNext = scrollToNext;
	}

	public String getScrollToPrevious() {
		return scrollToPrevious;
	}

	public void setScrollToPrevious(String scrollToPrevious) {
		this.scrollToPrevious = scrollToPrevious;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}
}