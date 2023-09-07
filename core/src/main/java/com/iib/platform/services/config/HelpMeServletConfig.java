package com.iib.platform.services.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "Help Me API Servlet Config", description = "Configure Help Me Servlet")
public @interface HelpMeServletConfig {

	@AttributeDefinition(name = "End-Point URL", description = "")
	String getEndPointUrl() default "https://ibluatapig.indusind.com/app/uat/Service12";

	@AttributeDefinition(name = "Client Secret Key", description = "")
	String getClientSecretKey() default "X-IBM-Client-Secret";

	@AttributeDefinition(name = "Client Secret Value", description = "")
	String getClientSecretValue() default "F8fF4nK5bY4aE3dQ7uW1jY7hP7bI1sY5qW0hD6tJ5kH0iX4oO1";

	@AttributeDefinition(name = "Client ID Key", description = "")
	String getClientID() default "X-IBM-Client-Id";

	@AttributeDefinition(name = "Clinet ID Value", description = "")
	String getClientIDValue() default "020939a3-017d-40d0-b011-511c2f52631b";

	@AttributeDefinition(name = "Account No", description = "")
	String accountNo() default "9999inqry1001";

	@AttributeDefinition(name = "CIF ID", description = "")
	String cifId() default "INQRY1";

	@AttributeDefinition(name = "Call Related To", description = "")
	String callRelatedTo() default "Request_IndusSmart";

	@AttributeDefinition(name = "Call Type", description = "")
	String callType() default "Customer_Meeting_Feedback";

	@AttributeDefinition(name = "Call SubType", description = "")
	String callSubType() default "IndusSmart_Feedback";

	@AttributeDefinition(name = "Media", description = "")
	String media() default "Website";

	@AttributeDefinition(name = "Interaction State", description = "")
	String interactionState() default "Resolved";

	@AttributeDefinition(name = "Team", description = "")
	String team() default "Mutual_Fund";

	@AttributeDefinition(name = "Subject", description = "")
	String subject() default "Website Compliments";

	@AttributeDefinition(name = "Status", description = "")
	String status() default "Open";

	@AttributeDefinition(name = "Message", description = "")
	String message() default "Website Compliments";

	@AttributeDefinition(name = "SMS Flag", description = "")
	String smsFlag() default "false";

	@AttributeDefinition(name = "EMAIL Flag", description = "")
	String emailFlag() default "false";

	@AttributeDefinition(name = "Customer Property Value", description = "")
	String customerProperty() default "27287";

	@AttributeDefinition(name = "Mobile Property Value", description = "")
	String mobileProperty() default "27291";

	@AttributeDefinition(name = "Email Property Value", description = "")
	String emailProperty() default "27289";

}