package com.iib.platform.services.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "Re-CAPTCHA Servlet Configurations", description = "Configure Re-CAPTCHA Servlet")
public @interface ReCaptchaServletConfig {

	@AttributeDefinition(name = "Secret Key", description = "")
	String getSecretKey() default "6LcRUCQUAAAAAOws6TtdsXn_v1KhtHFMZM2soNfm";

}
