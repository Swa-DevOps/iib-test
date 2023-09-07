package com.iib.platform.services.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "BillDesk Servlet Config", description = "Configure BillDesk Servlet")
public @interface BillDeskServletConfig {

	@AttributeDefinition(name = "Redirect URL: ", description = "redirection url for response")
	String getRedirectionUrl() default "http://10.60.57.12:4503/bin/billDeskResponse";

	@AttributeDefinition(name = "Checksum Key: ", description = "")
	String checkSumKey() default "VwL1awdopRKr";

	@AttributeDefinition(name = "Bill Desk URL: ", description = "")
	String billDeskUrl() default "https:/www.billdesk.in/billpay/EMandateController?action=EmandateIndusBankReg&msg=";

	@AttributeDefinition(name = "Bill Desk Mode of Registarion: ", description = " Default should be BO")
	String modeofRegistration() default "BO";
	
	@AttributeDefinition(name = "Redirect URL for CFDPL: ", description = "redirection url for response")
	String getRedirectionUrlCFD() default "http://10.60.57.12:4503/bin/cfdpl/billDeskResponse";

}