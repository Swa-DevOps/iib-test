package com.iib.platform.services.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "Calculator Service Config", description = "Configure the Calculator")
public @interface CalculatorServiceConfig {

	@AttributeDefinition(name = "Goal Inflation", description = "Inflation for Goal Calculator")
	double getInflation() default 6;

	@AttributeDefinition(name = "Retirement Corpus", description = "Retirement Corpus Return")
	double getRetirementCorpusReturn() default 8;

	@AttributeDefinition(name = "Retirement Inflation", description = "Retirement Inflation")
	double getRetirementInflation() default 6;
}