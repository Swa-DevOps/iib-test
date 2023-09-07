package com.iib.platform.common.models;

import org.osgi.annotation.versioning.ConsumerType;
import com.iib.platform.common.objects.WCMComponent;

/**
 * Base Component Model
 *
 * @author TechChefz (TCZ Consulting LLP)
 *
 */

@ConsumerType
public interface BaseComponentModel {

	public WCMComponent getComponent();

}
