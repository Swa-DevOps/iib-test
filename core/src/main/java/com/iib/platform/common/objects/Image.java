package com.iib.platform.common.objects;

import java.util.Map;
import com.iib.platform.common.utils.LinkUtil;

/**
 * Image DTO
 *
 * @author TechChefz (TCZ Consulting LLP)
 *
 */
public class Image extends Asset {

	private String _id;
	private Map<String, Breakpoint> breakpoints;
	private String altText;
	private String screenReaderText;
	private String hyperlink;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public Map<String, Breakpoint> getBreakpoints() {
		return breakpoints;
	}

	public void setBreakpoints(Map<String, Breakpoint> breakpoints) {
		this.breakpoints = breakpoints;
	}

	public String getAltText() {
		return altText;
	}

	public void setAltText(String altText) {
		this.altText = altText;
	}

	public String getScreenReaderText() {
		return screenReaderText;
	}

	public void setScreenReaderText(String screenReaderText) {
		this.screenReaderText = screenReaderText;
	}

	public String getHyperlink() {
		return LinkUtil.getFormattedURL(hyperlink);
	}

	public void setHyperlink(String hyperlink) {
		this.hyperlink = hyperlink;
	}

}