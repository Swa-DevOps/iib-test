package com.iib.platform.api.request;

import java.util.Map;

public class RequestBody {

	private String requestBodyType;
	private String requestContentJSON;
	private String requestContentXML;
	private Map<String, String> requestContentParameterMap;
	private Object obj;

	public void setRequestContent(Object requestContent) {
		this.obj = requestContent;
	}

	public Object getRequestContent() {
		return obj;
	}

	public String getRequestBodyType() {
		return requestBodyType;
	}

	public void setRequestBodyType(String requestBodyType) {
		this.requestBodyType = requestBodyType;
	}

	public String getRequestContentJSON() {
		return requestContentJSON;
	}

	public void setRequestContentJSON(String requestContentJSON) {
		this.requestContentJSON = requestContentJSON;
	}

	public String getRequestContentXML() {
		return requestContentXML;
	}

	public void setRequestContentXML(String requestContentXML) {
		this.requestContentXML = requestContentXML;
	}

	public Map<String, String> getRequestContentParameterMap() {
		return requestContentParameterMap;
	}

	public void setRequestContentParameterMap(Map<String, String> requestContentParameterMap) {
		this.requestContentParameterMap = requestContentParameterMap;
	}

}
