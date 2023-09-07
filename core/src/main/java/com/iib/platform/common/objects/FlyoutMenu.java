package com.iib.platform.common.objects;

import java.util.List;

/**
 * FlyoutMenu DTO
 *
 * @author TechChefz (TCZ Consulting LLP)
 *
 */
public class FlyoutMenu {

	private String includeQuicklinks;
	private String includeTeasers;
	private List<MenuItem> flyoutMenuList;
	private List<Teaser> teaserList;
	private String teaserStyleClasses;
	private String teaserViewMode;

	public String getIncludeQuicklinks() {
		return includeQuicklinks;
	}

	public void setIncludeQuicklinks(String includeQuicklinks) {
		this.includeQuicklinks = includeQuicklinks;
	}

	public String getIncludeTeasers() {
		return includeTeasers;
	}

	public void setIncludeTeasers(String includeTeasers) {
		this.includeTeasers = includeTeasers;
	}

	public List<MenuItem> getFlyoutMenuList() {
		return flyoutMenuList;
	}

	public void setFlyoutMenuList(List<MenuItem> flyoutMenuList) {
		this.flyoutMenuList = flyoutMenuList;
	}

	public List<Teaser> getTeaserList() {
		return teaserList;
	}

	public void setTeaserList(List<Teaser> teaserList) {
		this.teaserList = teaserList;
	}

	public String getTeaserStyleClasses() {
		return teaserStyleClasses;
	}

	public void setTeaserStyleClasses(String teaserStyleClasses) {
		this.teaserStyleClasses = teaserStyleClasses;
	}

	public String getTeaserViewMode() {
		return teaserViewMode;
	}

	public void setTeaserViewMode(String teaserViewMode) {
		this.teaserViewMode = teaserViewMode;
	}

}