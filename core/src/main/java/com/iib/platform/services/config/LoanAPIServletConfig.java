package com.iib.platform.services.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "Loan API Servlet Config EnachPL", description = "Configure Loan API Servlet EnachPL")
public @interface LoanAPIServletConfig {

	@AttributeDefinition(name = "End Point URL", description = "")
	String getEndPointUrl() default "https://ibluatapig.indusind.com/app/uat/FIUsbWebServiceService";

	@AttributeDefinition(name = "Client Secret Key", description = "")
	String getClientSecretKey() default "X-IBM-Client-Secret";

	@AttributeDefinition(name = "Client Secret Value", description = "")
	String getClientSecretValue() default "F8fF4nK5bY4aE3dQ7uW1jY7hP7bI1sY5qW0hD6tJ5kH0iX4oO1";

	@AttributeDefinition(name = "Client ID", description = "")
	String getClientID() default "X-IBM-Client-Id";

	@AttributeDefinition(name = "Clinet ID Value", description = "")
	String getClientIDValue() default "020939a3-017d-40d0-b011-511c2f52631b";

	@AttributeDefinition(name = "Request URL for Generate OTP: ", description = "")
	String requestUrl() default "http://bulkpush.mytoday.com/BulkSms/SingleMsgApi";
	
	@AttributeDefinition(name = "LAA Amount Greater then NPCI approved ", description = "")
	String loanEMIamountcheckMessage() default "EMI of LAA account is greater than l LKH";
	
	@AttributeDefinition(name = "EMI Amount Limit ", description = "")
	String loanEMIamountcheck() default "100000";

}