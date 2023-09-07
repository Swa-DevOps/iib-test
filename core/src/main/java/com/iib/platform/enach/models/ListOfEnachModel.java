package com.iib.platform.enach.models;

import java.util.List;

import org.osgi.annotation.versioning.ConsumerType;

import com.iib.platform.common.models.BaseComponentModel;
import com.iib.platform.common.objects.Enach;

/**
 * List Of E-Nach Sling Model
 *
 * @author TechChefz (TCZ Consulting LLP)
 *
 */

@ConsumerType
public interface ListOfEnachModel extends BaseComponentModel {

	public List<Enach> getEnachItems();

}
