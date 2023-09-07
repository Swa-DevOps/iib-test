package com.iib.platform.services.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

/**
 * OCD of SOAP API Service
 *
 * @author Ayasya Digital
 *
 */

@ObjectClassDefinition(name = "Enach SOAP API Service Config", description = "Configure SOAP API ENACH FLUX Service")
public @interface SoapAPIServiceConfig {

	@AttributeDefinition(name = "Client Secret Key", description = "")
	String getClientSecretKey() default "X-IBM-Client-Secret";

	@AttributeDefinition(name = "Client Secret Value", description = "")
	String getClientSecretValue() default "F8fF4nK5bY4aE3dQ7uW1jY7hP7bI1sY5qW0hD6tJ5kH0iX4oO1";

	@AttributeDefinition(name = "Client ID Key", description = "")
	String getClientID() default "X-IBM-Client-Id";

	@AttributeDefinition(name = "Clinet ID Value", description = "")
	String getClientIDValue() default "020939a3-017d-40d0-b011-511c2f52631b";

	@AttributeDefinition(name = "Flux API URL for ENACH Service", description = "")
	String getFluxAPIUrl() default "https://ibluatapig.indusind.com/app/uat/FIUsbWebServiceService";
	
	@AttributeDefinition(name = "Enach CFD API URL for CFD ACC ON MOBILE Service", description = "")
	String getenachCFDURL() default "https://ibluatapig.indusind.com/app/uat/api/GetCustomer_By_Registered_Mobile";
	
	@AttributeDefinition(name = "Enach CFD API URL for CFD ACC ON DEAL Service", description = "")
	String getenachCFDDealURL() default "https://ibluatapig.indusind.com/app/uat/api/Get_Customer_Details_By_Deal";
	
	@AttributeDefinition(name = "Enach CFD API URL for CFD ACC ON DEAL Service Test", description = "0 for Actual Result and 1,2,3 for usecases")
	int getenachCFDtestingType() default 0;

}