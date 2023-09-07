package com.iib.platform.services.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

/**
 * OCD of SOAP API Service
 *
 * @author Ayasya Digital
 *
 */

@ObjectClassDefinition(name = "Enach Create Flux Mandate Service Config", description = "Configure Enach Create Flux Mandate Service")
public @interface CreateFluxMandateConfig {

	@AttributeDefinition(name = "Client Secret Key", description = "")
	String getClientSecretKey() default "X-IBM-Client-Secret";

	@AttributeDefinition(name = "Client Secret Value", description = "")
	String getClientSecretValue() default "F8fF4nK5bY4aE3dQ7uW1jY7hP7bI1sY5qW0hD6tJ5kH0iX4oO1";

	@AttributeDefinition(name = "Client ID Key", description = "")
	String getClientID() default "X-IBM-Client-Id";

	@AttributeDefinition(name = "Clinet ID Value", description = "")
	String getClientIDValue() default "020939a3-017d-40d0-b011-511c2f52631b";

	@AttributeDefinition(name = "Create Mandate Enach Flux API", description = "")
	String getURLCreateMandateFlux() default "https://ibluatapig.indusind.com/app/uat/api/emandateDetails";

	@AttributeDefinition(name = "Need to include Header on UAT or Production", description = "")
	String getIncludeHeader() default "Yes";
	
	@AttributeDefinition(name = "Need to trigger EMAIL for CFD", description = "")
	String getEmailTriggerCFD() default "No";

}