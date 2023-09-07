/*
 *
 *  * Copyright (c) 2019, Indusind and/or its affiliates. All rights reserved.
 *  *
 *  * Redistribution and use in source and binary forms, with or without
 *  * modification, are permitted provided that the following conditions
 *  * are met:
 *  *
 *  *   - Redistributions of source code must retain the above copyright
 *  *     notice, this list of conditions and the following disclaimer.
 *  *
 *  *   - Redistributions in binary form must reproduce the above copyright
 *  *     notice, this list of conditions and the following disclaimer in the
 *  *     documentation and/or other materials provided with the distribution.
 *  *
 *  *   - Neither the name of Indusind or the names of its
 *  *     contributors may be used to endorse or promote products derived
 *  *     from this software without specific prior written permission.
 *
 */

package com.iib.platform.services.impl;


import java.io.IOException;
import java.util.Objects;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iib.platform.exceptions.RecaptchaException;
import com.iib.platform.services.HttpUtilService;


/**
 * HTTP Service
 * 
 * @author Mayank Yadav
 *
 */
@Component(immediate = true, service = HttpUtilService.class, property = {
		Constants.SERVICE_DESCRIPTION + "= Filter incoming requests and redirect" })
public class HttpUtilServiceImpl implements HttpUtilService {

	private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtilServiceImpl.class);

	@Activate
	public void activate() {
		LOGGER.info("HttpUtilServiceImpl Activated!");
	}

	@Override
	public String getResponseForRecaptcha(String url) {
		String response = StringUtils.EMPTY;
		String returnData= StringUtils.EMPTY;
		try (CloseableHttpClient httpclient = HttpClients.createDefault()){
			//CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(url);
			HttpResponse httpResponse = httpclient.execute(httpGet);
			response = handleResponse(httpResponse);
			if(response != null) {
				JSONObject obj = new JSONObject(response);
				String responseStatus = obj.get("success").toString();
				if(responseStatus.equalsIgnoreCase("true")) {
					returnData= "Recaptcha Verified";	
				}else {
					throw new RecaptchaException("Recaptcha not verified");
				}
				
			}else {
				return "Something went wrong";
			}

		} catch (RecaptchaException err) {
			LOGGER.info("Error" + err.getMessage());
			return err.getMessage();
		} catch (final Exception e) {
			LOGGER.info("Errpr on get() method :: {} ", e);
			return "Something Went Wrong";
		}
		return returnData;
	}

	/**
	 * Method handleResponse(...) method to Handle the Http Response.
	 * 
	 * @param response
	 * 
	 * @return {@link String}
	 * 
	 * @throws IOException
	 */
	private String handleResponse(final HttpResponse response) throws IOException {
		int status = response.getStatusLine().getStatusCode();
		if (status >= 200 && status < 300) {
			HttpEntity entity = response.getEntity();
			if (Objects.isNull(entity)) {
				return null;
			} else {
				return EntityUtils.toString(entity);
			}
		} else {
			return "Status: " + status;
		}
	}

	@Override
	public String post() {
		String response = StringUtils.EMPTY;
		try {

		} catch (final Exception e) {
		}
		return response;
	}
}
