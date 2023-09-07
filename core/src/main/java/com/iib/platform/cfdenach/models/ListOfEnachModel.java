package com.iib.platform.cfdenach.models;

import java.util.List;

import org.osgi.annotation.versioning.ConsumerType;

import com.iib.platform.common.models.BaseComponentModel;
import com.iib.platform.common.objects.CfdEnach;

/**
 * List Of E-Nach Sling Model
 *
 * @author ADS (Niket Goel)
 *
 */

@ConsumerType
public interface ListOfEnachModel extends BaseComponentModel {

	public List<CfdEnach> getEnachItems();

}
