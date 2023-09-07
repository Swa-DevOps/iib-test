package com.iib.platform.services.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "SSO PL BillDesk Response Servlet Config", description = "Configure BillDesk Servlet SSO PL")
public @interface CFDPLBillDeskResponseServletConfig {

	@AttributeDefinition(name = "Sorry Page Url: ", description = "")
	String getSorryPageUrl() default "http://10.60.57.12:4503/content/enach-pl/home/sorry.html";

	@AttributeDefinition(name = "Thank You Page Url: ", description = "")
	String getThankYouPageUrl() default "http://10.60.57.12:4503/content/enach-pl/home/thank-you.html";
	
	@AttributeDefinition(name = "SSO URL OR DEFAULT APP PAGE ", description = "")
	String getDefaultResponsePage() default "true";

}