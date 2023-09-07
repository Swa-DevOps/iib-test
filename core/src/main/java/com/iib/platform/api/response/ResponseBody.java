package com.iib.platform.api.response;

import java.util.Map;

import org.json.JSONArray;

public class ResponseBody {

	private String responseContentJSON;
	private String responseContentXML;
	private Map<String, String> responseContentParameterMap;
	private JSONArray responseJsonArray;

	public String getResponseContentJSON() {
		return responseContentJSON;
	}

	public void setResponseContentJSON(String responseContentJSON) {
		this.responseContentJSON = responseContentJSON;
	}

	public String getResponseContentXML() {
		return responseContentXML;
	}

	public void setResponseContentXML(String responseContentXML) {
		this.responseContentXML = responseContentXML;
	}

	public Map<String, String> getResponseContentParameterMap() {
		return responseContentParameterMap;
	}

	public void setResponseContentParameterMap(Map<String, String> responseContentParameterMap) {
		this.responseContentParameterMap = responseContentParameterMap;
	}

	public JSONArray getResponseJsonArray() {
		return responseJsonArray;
	}

	public void setResponseJsonArray(JSONArray responseJsonArray) {
		this.responseJsonArray = responseJsonArray;
	}

}