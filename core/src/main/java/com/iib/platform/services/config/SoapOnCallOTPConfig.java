package com.iib.platform.services.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

/**
 * OCD of SOAP API Service
 *
 * @author Ayasya Digital
 *
 */

@ObjectClassDefinition(name = "Enach SOAP ON Call OTP Service Config", description = "Configure SOAP ON Call OTP Service")
public @interface SoapOnCallOTPConfig {

	@AttributeDefinition(name = "Client Secret Key", description = "")
	String getClientSecretKey() default "X-IBM-Client-Secret";

	@AttributeDefinition(name = "Client Secret Value", description = "")
	String getClientSecretValue() default "F8fF4nK5bY4aE3dQ7uW1jY7hP7bI1sY5qW0hD6tJ5kH0iX4oO1";

	@AttributeDefinition(name = "Client ID Key", description = "")
	String getClientID() default "X-IBM-Client-Id";

	@AttributeDefinition(name = "Clinet ID Value", description = "")
	String getClientIDValue() default "020939a3-017d-40d0-b011-511c2f52631b";

	@AttributeDefinition(name = "ON Call OTP for ENACH Service", description = "")
	String getOnCallAPIUrl() default "https://ibluatapig.indusind.com/app/uat/IndusInd_WEB_IndusIndwsvoiceCall";

}