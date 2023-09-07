package com.iib.platform.services.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

/**
 * OCD of SOAP API Service
 */

@ObjectClassDefinition(name = "Eanch Application-Digio EMAIL SMS API Service Config", description = "Configure EAMIL and SMS Triiger API Service")
public @interface EmailAPIServiceConfig {

	@AttributeDefinition(name = "Client Secret Key", description = "")
	String getClientSecretKey() default "X-IBM-Client-Secret";

	@AttributeDefinition(name = "Client Secret Value", description = "")
	String getClientSecretValue() default "F8fF4nK5bY4aE3dQ7uW1jY7hP7bI1sY5qW0hD6tJ5kH0iX4oO1";

	@AttributeDefinition(name = "Client ID Key", description = "")
	String getClientID() default "X-IBM-Client-Id";

	@AttributeDefinition(name = "Clinet ID Value", description = "")
	String getClientIDValue() default "020939a3-017d-40d0-b011-511c2f52631b";

	@AttributeDefinition(name = "Header Need to Include Flag", description = "For Production it should be fasle")
	String getHeaderFlag() default "true";

	@AttributeDefinition(name = "Email Service URL", description = "")
	String getEmailServiceUrl() default "https://ibluatapig.indusind.com/app/uat/api/SendEmail";

	@AttributeDefinition(name = "SMS Service URL", description = "")
	String getSmsServiceUrl() default "https://ibluatapig.indusind.com/app/uat/api/SendSms";

	@AttributeDefinition(name = "Email Service Channel ID", description = "")
	String getEmailServiceChannelId() default "ENACHUAT";

	@AttributeDefinition(name = "SMS Service Channel ID", description = "")
	String getSmsServiceChannelId() default "ENACHUAT";

	@AttributeDefinition(name = "Email Service Key", description = "")
	String getEmailServiceKey() default "ENACH#20190618";

	@AttributeDefinition(name = "Sms Service Key", description = "")
	String getSmsServiceKey() default "ENACH#20190618";

	@AttributeDefinition(name = "CANCEL SMS Service Channel ID", description = "")
	String getSmsECancelServiceChannelId() default "CANMANDTRAN";

	@AttributeDefinition(name = "CANCEL SMS Service Key", description = "")
	String getSmsEcancelServiceKey() default "CANMANDTRAN#20210330";
}