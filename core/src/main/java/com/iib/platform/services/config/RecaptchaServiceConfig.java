/*
 *
 *  * Copyright (c) 2019, Indusind and/or its affiliates. All rights reserved.
 *  *
 *  * Redistribution and use in source and binary forms, with or without
 *  * modification, are permitted provided that the following conditions
 *  * are met:
 *  *
 *  *   - Redistributions of source code must retain the above copyright
 *  *     notice, this list of conditions and the following disclaimer.
 *  *
 *  *   - Redistributions in binary form must reproduce the above copyright
 *  *     notice, this list of conditions and the following disclaimer in the
 *  *     documentation and/or other materials provided with the distribution.
 *  *
 *  *   - Neither the name of Indusind or the names of its
 *  *     contributors may be used to endorse or promote products derived
 *  *     from this software without specific prior written permission.
 *
 */
package com.iib.platform.services.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name="Indusind IIB Enach and Other Modules Recaptcha Service Configuration", description="Indusind IIB Enach and Other Modules Recaptcha Service Configuration")
public @interface RecaptchaServiceConfig {
	
	@AttributeDefinition(name="Public Key")
	public String public_key() default "6LfDIr0UAAAAADgp9bMIe4_xuyf6e39epkibX3oz";
	
	@AttributeDefinition(name="Private Key")
	public String private_key() default "6LfDIr0UAAAAAP0-Zy1IMjTEKe9RbCxmfQKJbJBl";

}
