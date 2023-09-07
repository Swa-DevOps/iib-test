package com.iib.platform.common.objects;

/**
 * WCM Component DTO
 *
 * @author TechChefz (TCZ Consulting LLP)
 *
 */
public class WCMComponent {

	private String name;
	private String path;
	private String id;
	private String title;
	private String description;
	private String tenant;
	private String parentComponent;
	private String parentComponentPath;
	private String randomNumber;
	private String customHeight;
	private String customStyleClass;
	private String hideOnMobile;
	private String hideOnTablet;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getTenant() {
		return tenant;
	}

	public void setTenant(String tenant) {
		this.tenant = tenant;
	}

	public String getParentComponent() {
		return parentComponent;
	}

	public void setParentComponent(String parentComponent) {
		this.parentComponent = parentComponent;
	}

	public String getParentComponentPath() {
		return parentComponentPath;
	}

	public void setParentComponentPath(String parentComponentPath) {
		this.parentComponentPath = parentComponentPath;
	}

	public String getRandomNumber() {
		return randomNumber;
	}

	public void setRandomNumber(String randomNumber) {
		this.randomNumber = randomNumber;
	}

	public String getCustomHeight() {
		return customHeight;
	}

	public void setCustomHeight(String customHeight) {
		this.customHeight = customHeight;
	}

	public String getCustomStyleClass() {
		return customStyleClass;
	}

	public void setCustomStyleClass(String customStyleClass) {
		this.customStyleClass = customStyleClass;
	}

	public String getHideOnMobile() {
		return hideOnMobile;
	}

	public void setHideOnMobile(String hideOnMobile) {
		this.hideOnMobile = hideOnMobile;
	}

	public String getHideOnTablet() {
		return hideOnTablet;
	}

	public void setHideOnTablet(String hideOnTablet) {
		this.hideOnTablet = hideOnTablet;
	}

}
