package com.iib.platform.services.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "E-Mandate Bank Data Service Configuration", description = "Configure E-Mandate Bank Data")
public @interface EmandateBankDataServiceConfig {

	@AttributeDefinition(name = "Enter Bank Names with Bank Code for DIGIO", description = "Enter the Bank Details in format: (Bank Name:Bank Code)")
	String[] getBankCode() default { "ABHYUDAYA CO-OPERATIVE BANK:ABHY", "ANDHRA BANK:ANDB", "AXIS BANK LTD:UTIB",
			"BANK OF INDIA:BKID", "BANK OF MAHARASHTRA:MAHB", "THE BHARAT CO-OPERATIVE BANK LTD:BCBM",
			"CANARA BANK:CNRB", "CENTRAL BANK OF INDIA:CBIN", "CITIBANK N A:CITI", "DEVELOPMENT BANK OF SINGAPORE:DBSS",
			"DCB BANK LTD:DCBL", "DENA BANK:BKDN", "FEDERAL BANK:FDRL", "HDFC BANK LTD:HDFC", "ICICI BANK LTD:ICIC",
			"IDBI BANK:IBKL", "IDFC BANK LIMITED:IDFB", "INDUSIND BANK:INDB", "KOTAK MAHINDRA BANK LTD:KKBK",
			"ORIENTAL BANK OF COMMERCE:ORBC", "PUNJAB NATIONAL BANK:PUNB", "RATNAKAR BANK:RATN",
			"SARASWAT COOPERATIVE BANK LTD:SRCB", "STANDARD CHARTERED BANK:SCBL", "SVC CO-OPERATIVE BANK LTD:SVCB",
			"SYNDICATE BANK:SYNB", "THE AKOLA DISTRICT CENTRAL COOPERATIVE BANK LTD:ADCC",
			"THE COSMOS CO-OPERATIVE BANK LTD:COSB", "THE HONGKONG & SHANGHAI BANKING CORPORATION LTD:HSBC",
			"THE SUTEX CO-OP.BANK LTD:SUTB", "UCO BANK:UCBA", "UNION BANK OF INDIA:UBIN", "Yes Bank Ltd:YESB",
			"BIHAR GRAMIN BANK:BGBX", "CORPORATION BANK:CORP", "THE VARACHHA CO-OP BANK LTD:VARA",
			"KARUR VYSA BANK:KVBL", "The Catholic Syrian Bank:CSBK",
			"KALLAPPANNA AWADE ICHALKARANJI JANATASAHAKARI BANK:KAIJ", "TAMILNAD MERCANTILE BANK LTD:TMBL",
			"BANK OF BARODA:BARB", "The Adinath Co Op Bank Ltd:TACX", "South Indian Bank:SIBL",
			"Equitas Small Finance Bank Ltd:ESFB", "The Adarsh Co Op Urban Bank Ltd:ACUX", "BNP Paribas:BNPA",
			"The Utkal Cooperative Banking Society Ltd:UCBS", "RENDAL SAHAKARI BANK LTD RENDAL:REBX",
			"Lokmangal Co-Op. Bank Ltd. Solapur:LKMX", "The Agrasen Co-operative Urban Bank Ltd:AGCX",
			"THE MOGA CENTRAL COOPERATIVE BANK LTD:MOGX", "National Urban Cooperative Bank Ltd:NALX",
			"KONOKLOTA MAHILA URBAN COOP BANK LTD:KOCX", "UNIVERSAL CO-OPERATIVE URBAN BANK LTD:UCUX",
			"THE RAJPUTANA MAHILA URBAN CO OPERATIVE BAK LTD:RAMX", "ANDHRA PRAGATHI GRAMEENA BANK:APGB",
			"The Nabadwip Co-operative Credit Bank Ltd:NCCX", "THE MUGBERIA CENTRAL CO-OPERATIVE BANK LTD:MBCX",
			"The Shillong Cooperative Urban Bank Ltd:TSIX", "Amreli Jilla Madhyastha Sahakari Bank Ltd:AMRX" };

	@AttributeDefinition(name = "Enter Bank Names for 'Monthly' Frequency BILL DESK", description = "Enter Bank Names")
	String[] getFrequencyBankData() default { "Standard Chartered Bank", "Kotak Mahindra Bank ltd", "Indian Bank" };
	
	@AttributeDefinition(name = "Enter Bank Names with Bank Code For BILL DESK", description = "Enter the Bank Details in format: (Bank Name:Bank Code)")
	String[] getBankCodeBillDesk() default { "KOTAK MAHINDRA BANK LTD:KKBK","Yes Bank Ltd:YESB","UJJIVAN SMALL FINANCE BANK LTD:USFB",
		"INDUSIND BANK:INDB", "EQUITAS SMALL FINANCE BANK LTD:ESFB","ICICI BANK LTD:ICIC","IDFC BANK LIMITED:IDFB",
		"HDFC BANK LTD:HDFC","BANK OF MAHARASHTRA:MAHB","DEUTSCHE BANK AG:DEUT","UNITED BANK OF INDIA:UTBI",
		"FEDERAL BANK:FDRL","ANDHRA BANK:ANDB", "AXIS BANK LTD:UTIB","PUNJAB NATIONAL BANK:PUNB","KARNATAKA BANK LTD:KARB",
		"STATE BANK OF INDIA:SBIN","CENTRAL BANK OF INDIA:CBIN","BANK OF BARODA:BARB","TAMILNAD MERCANTILE BANK LTD:TMBL",
		"INDIAN OVERSEAS BANK:IOBA","RBL BANK LTD:RATN","PAYTM PAYMENTS BANK LTD:PYTM","CITY UNION BANK LTD:CIUB",
		"STANDARD CHARTERED BANK:SCBL","CANARA BANK:CNRB","SOUTH INDIAN BANK:SIBL","AU SMALL FINANCE BANK LTD:AUBL"};

}
