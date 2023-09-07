package com.iib.platform.common.objects;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.day.cq.tagging.Tag;

/**
 * Metadata DTO
 *
 * @author TechChefz (TCZ Consulting LLP)
 *
 */
public class Metadata {

	private List<Tag> tags;
	private Date timestamp;
	private String author;
	private Map<String, String> metadataProperties;

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Map<String, String> getMetadataProperties() {
		return metadataProperties;
	}

	public void setMetadataProperties(Map<String, String> metadataProperties) {
		this.metadataProperties = metadataProperties;
	}

}
