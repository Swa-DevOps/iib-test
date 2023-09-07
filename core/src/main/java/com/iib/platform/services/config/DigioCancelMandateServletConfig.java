package com.iib.platform.services.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "Digio Cancel Mandate Servlet Config", description = "")
public @interface DigioCancelMandateServletConfig {

	@AttributeDefinition(name = "Sponser Bank ID:", description = "")
	String getSponserBankId() default "INDB0000006";

	@AttributeDefinition(name = "url", description = "")
	String geturl() default "https://ext.digio.in:444/v2/client/enach/mandate/create_form";

	@AttributeDefinition(name = "Authorization Type: ", description = "")
	String authorization() default "Basic QUlJMUY2OTI0Q05UTDRTWklQNFhaU01QWkNKWlpWSUQ6UzlVMlBYTkFRMjZBQlA1OFYySzE2WEdDN0s2TkhBWUg=";

}
