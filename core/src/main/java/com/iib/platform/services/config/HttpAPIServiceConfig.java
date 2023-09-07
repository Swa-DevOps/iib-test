package com.iib.platform.services.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

/**
 * OCD of SOAP API Service
 *
 * @author Ayasya Digital
 *
 */

@ObjectClassDefinition(name = "Enach HttpAPIServiceConfig Mandate Service Config", description = "Configure HttpAPIServiceConfig Service")
public @interface HttpAPIServiceConfig {

	@AttributeDefinition(name = "Client Secret Key", description = "")
	String getClientSecretKey() default "X-IBM-Client-Secret";

	@AttributeDefinition(name = "Client Secret Value", description = "")
	String getClientSecretValue() default "F8fF4nK5bY4aE3dQ7uW1jY7hP7bI1sY5qW0hD6tJ5kH0iX4oO1";

	@AttributeDefinition(name = "Client ID Key", description = "")
	String getClientID() default "X-IBM-Client-Id";

	@AttributeDefinition(name = "Clinet ID Value", description = "")
	String getClientIDValue() default "020939a3-017d-40d0-b011-511c2f52631b";

	@AttributeDefinition(name = " Fetch Mandate or URM Number", description = "")
	String getURLMandateORURM() default "https://ibluatapig.indusind.com/app/uat/api/mandate";

	@AttributeDefinition(name = "Need to include Header on UAT or Production", description = "Set No for Production")
	String getIncludeHeader() default "Yes";

	@AttributeDefinition(name = "FeedID for SMS Trigger", description = "FeedID for SMS Trigger")
	String getFeedid() default "369236";

	@AttributeDefinition(name = "Username for SMS Trigger", description = "username for SMS Trigger")
	String getUsername() default "9899088737";

	@AttributeDefinition(name = "pass for SMS Trigger", description = "pass for SMS Trigger")
	String getPassword() default "tgmtg";
	
	@AttributeDefinition(name = " New API to Fetch Mandate or URM Number", description = "")
	String getnewURLMandate() default "http://10.24.201.57:8080/esignMandate/emandateFlux/status";
	
	@AttributeDefinition(name = " New API to Cancel Mandate or URM Number", description = "")
	String getnewURLCancelMandate() default "http://10.24.201.57:8080/mandate-outbound-integration/CancelMandate/cancelRequest";

	@AttributeDefinition(name = " Local Domain for CRM to call", description = "")
	String getdomainForCRM() default "http://localhost:4503";

	
}