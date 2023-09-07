package com.iib.platform.services.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "BillDesk Response Servlet Config", description = "Configure BillDesk Servlet")
public @interface BillDeskResponseServletConfig {

	@AttributeDefinition(name = "Sorry Page Url: ", description = "")
	String getSorryPageUrl() default "http://10.60.57.12:4503/content/enach-pl/home/sorry.html";

	@AttributeDefinition(name = "Thank You Page Url: ", description = "")
	String getThankYouPageUrl() default "http://10.60.57.12:4503/content/enach-pl/home/thank-you.html";

}