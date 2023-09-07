package com.iib.platform.services.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "Generate OTP Servlet Config", description = "Config Generate OTP Servlet")
public @interface GenerateOTPConfig {

	@AttributeDefinition(name = "Request URL: ", description = "")
	String requestUrl() default "http://bulkpush.mytoday.com/BulkSms/SingleMsgApi";
	
	@AttributeDefinition(name = "By Pass Captcha ", description = "Yes or No")
	String byPassCaptach() default "Yes";
	
	

}
