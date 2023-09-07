package com.iib.platform.services.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

/**
 * OCD of SOAP API Service
 *
 * @author Ayasya Digital
 *
 */

@ObjectClassDefinition(name = "Enach Mandate Transaction Config", description = "Configure Enach Mandate Transaction Config Service")
public @interface MandateTransactionConfig {

	@AttributeDefinition(name = "Client Secret Key", description = "")
	String getClientSecretKey() default "X-IBM-Client-Secret";

	@AttributeDefinition(name = "Client Secret Value", description = "")
	String getClientSecretValue() default "F8fF4nK5bY4aE3dQ7uW1jY7hP7bI1sY5qW0hD6tJ5kH0iX4oO1";

	@AttributeDefinition(name = "Client ID Key", description = "")
	String getClientID() default "X-IBM-Client-Id";

	@AttributeDefinition(name = "Clinet ID Value", description = "")
	String getClientIDValue() default "020939a3-017d-40d0-b011-511c2f52631b";

	@AttributeDefinition(name = "Fetch Transaction Details", description = "")
	String getURLTransactionDetails() default "https://ibluatapig.indusind.com/app/uat/api/eapiEnquiry/transaction";

	@AttributeDefinition(name = "Need to include Header on UAT or Production", description = "Set No for Production")
	String getIncludeHeader() default "Yes";

}