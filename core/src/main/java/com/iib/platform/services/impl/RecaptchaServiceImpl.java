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

import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iib.platform.services.config.RecaptchaServiceConfig;
import com.iib.platform.services.HttpUtilService;
import com.iib.platform.services.RecaptchaService;

@Component(
        immediate = true,
        enabled = true,
        service = {RecaptchaService.class},
        property = {
                Constants.SERVICE_DESCRIPTION + "= IIB ENACH PLATFORM Recaptcha Service Implementation"
        })
@Designate(ocd = RecaptchaServiceConfig.class)
public class RecaptchaServiceImpl implements RecaptchaService {
    private static Logger log = LoggerFactory.getLogger(RecaptchaServiceImpl.class);

    private String publicKey;
    private String privateKey;

    @Reference
    HttpUtilService httpUtilService;

    @Activate
    protected void activate(RecaptchaServiceConfig config) {
        log.info("Recaptcha Service has been activated!");
        publicKey = config.public_key();
        privateKey = config.private_key();
    }

    @Deactivate
    public void deactivate() {
        log.info("Recaptcha Service has been deactivated");
    }

    public boolean verifyRecaptchaResponseFromGoogle(String gresponse) {
        String url = "https://google.com/recaptcha/api/siteverify";
        String resultantUrl = url + "?secret=" + privateKey + "&response=" + gresponse;
        String response = httpUtilService.getResponseForRecaptcha(resultantUrl);
        if (response.equalsIgnoreCase("Recaptcha Verified")) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String getPublicKey() {
        return publicKey;
    }

}