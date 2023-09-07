package com.iib.platform.services.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "Digio Create Mandate Servlet Config for CFD", description = "Configure Digio Create Mandate Servlet for CFD")
public @interface CFDDigioCreateMandateServletConfig {

	@AttributeDefinition(name = "Enviroment Type", description = "UAT OR PROD")
	String getEnvType() default "UAT";

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

	@AttributeDefinition(name = "Need to include Header on UAT or Production", description = "")
	String getIncludeHeader() default "Yes";

	/** ----------------------------------Digio API Properties------------------ */

	@AttributeDefinition(name = "Digio URL", description = "")
	String getDigioUrl() default "https://ext.digio.in:444/v3/client/mandate/create_form";

	@AttributeDefinition(name = "Sponser Bank ID", description = "")
	String sponserBankId() default "INDB0000006";

	@AttributeDefinition(name = "Sponser Bank Name", description = "")
	String sponserBankName() default "INDUSIND BANK LTD";

	@AttributeDefinition(name = "Bank Identifier", description = "")
	String bankIdentifier() default "INDB";

	@AttributeDefinition(name = "Login ID", description = "")
	String loginId() default "INDB00160000010226";

	@AttributeDefinition(name = "Mandate Sequence", description = "")
	String mandateSequence() default "001";

	@AttributeDefinition(name = "Customer Account Type", description = "")
	String customerAcType() default "Savings";

	@AttributeDefinition(name = "Management Category", description = "")
	String managementCategory() default "L001";

	@AttributeDefinition(name = "Service Providier Name", description = "")
	String serviceProviderName() default "INDUSIND BANK LTD";

	@AttributeDefinition(name = "Servcice Provider Utility", description = "")
	String serviceProdividerUtility() default "INDB00160000010226";

	@AttributeDefinition(name = "Instrument Type", description = "")
	String instrumentType() default "debit";

	@AttributeDefinition(name = "Is Recurring", description = "")
	String isRecurring() default "true";

	@AttributeDefinition(name = "E-NACH Type", description = "")
	String enachType() default "CREATE";

	@AttributeDefinition(name = "Corporate Config Id", description = "")
	String corporate_config_id() default "TSE1905171637108354OVURU2GEREU34";

	@AttributeDefinition(name = "Auth Mode", description = "")
	String auth_mode() default "api";

	@AttributeDefinition(name = "Create Mandate Enach Flux API", description = "")
	String getURLCreateMandateFlux() default "https://ibluatapig.indusind.com/app/uat/api/emandateDetails";

	@AttributeDefinition(name = " Fetch Mandate or URM Number", description = "")
	String getURLMandateORURM() default "https://ibluatapig.indusind.com/app/uat/api/mandate";

	@AttributeDefinition(name = "Fetch Transaction Details", description = "")
	String getURLTransactionDetails() default "https://ibluatapig.indusind.com/app/uat/api/eapiEnquiry/transaction";

	@AttributeDefinition(name = "Authorization Type", description = "")
	String authorization() default "Basic QUlYS0hSVzlSVzJNVUxRMUJPVlBRQjdHS1A5Rlc0Nlo6M0xHSjNJWjdBS0lLTTZVMTFWUzI5WkFTVjhWTVA3NVo=";

	/**
	 * ------------------------------------------------------------------------------
	 */

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

	// ---------------------------------------------------------------------//

	@AttributeDefinition(name = "(Customer Name) F1", description = "")
	String f1() default "38138";

	@AttributeDefinition(name = "(Destination Bank Name) F2", description = "")
	String f2() default "38139";

	@AttributeDefinition(name = "(Customer Account No) F3", description = "")
	String f3() default "38140";

	@AttributeDefinition(name = "(Confirm Account No) F4", description = "")
	String f4() default "38141";

	@AttributeDefinition(name = "(IFSC Code) F5", description = "")
	String f5() default "38142";

	@AttributeDefinition(name = "(Maximum Amount) F6", description = "")
	String f6() default "3843";

	@AttributeDefinition(name = "(Transfer Frequency) F7", description = "")
	String f7() default "38144";

	@AttributeDefinition(name = "(Start Date) F8", description = "")
	String f8() default "38145";

	@AttributeDefinition(name = "(End Date) F9", description = "")
	String f9() default "38146";

	@AttributeDefinition(name = "(Total Amount) F10", description = "")
	String f10() default "38147";

	@AttributeDefinition(name = "(Total Installments) F11", description = "")
	String f11() default "38470";

	@AttributeDefinition(name = "(Referral Code) F12", description = "")
	String f12() default "38471";

	@AttributeDefinition(name = "(Aadhar No) F13", description = "")
	String f13() default "38472";

	@AttributeDefinition(name = "(Credit Account No) F14", description = "")
	String f14() default "38473";

	@AttributeDefinition(name = "(Mobile No) F15", description = "")
	String f15() default "38474";

}
