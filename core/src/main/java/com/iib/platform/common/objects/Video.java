package com.iib.platform.common.objects;

import java.util.Map;
import org.apache.sling.api.resource.Resource;

/**
 * Video DTO
 *
 * @author ADS
 *
 */
public class Video extends Asset {

	private String isExternalVideo;
	private String videoType;
	private String autoPlay;
	private String loop;
	private Map<String, String> videoRenditions;
	private Map<String, String> thumbnailRenditions;
	private String thumbnailImagePath;
	private String thumbnailImageAltText;

	public Video() {

	}

	public Video(Resource resource) {
		if ( (resource instanceof Resource)) {
			//to do
		}
	}

	public String getIsExternalVideo() {
		return isExternalVideo;
	}

	public void setIsExternalVideo(String isExternalVideo) {
		this.isExternalVideo = isExternalVideo;
	}

	public String getVideoType() {
		return videoType;
	}

	public void setVideoType(String videoType) {
		this.videoType = videoType;
	}

	public String getAutoPlay() {
		return autoPlay;
	}

	public void setLoop(String loop) {
		this.loop = loop;
	}

	public String getLoop() {
		return loop;
	}

	public void setAutoPlay(String autoPlay) {
		this.autoPlay = autoPlay;
	}

	public Map<String, String> getVideoRenditions() {
		return videoRenditions;
	}

	public void setVideoRenditions(Map<String, String> videoRenditions) {
		this.videoRenditions = videoRenditions;
	}

	public Map<String, String> getThumbnailRenditions() {
		return thumbnailRenditions;
	}

	public void setThumbnailRenditions(Map<String, String> thumbnailRenditions) {
		this.thumbnailRenditions = thumbnailRenditions;
	}

	public String getThumbnailImagePath() {
		return thumbnailImagePath;
	}

	public void setThumbnailImagePath(String thumbnailImagePath) {
		this.thumbnailImagePath = thumbnailImagePath;
	}

	public String getThumbnailImageAltText() {
		return thumbnailImageAltText;
	}

	public void setThumbnailImageAltText(String thumbnailImageAltText) {
		this.thumbnailImageAltText = thumbnailImageAltText;
	}

}