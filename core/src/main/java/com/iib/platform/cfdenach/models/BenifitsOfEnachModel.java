package com.iib.platform.cfdenach.models;

import java.util.List;

import org.osgi.annotation.versioning.ConsumerType;

import com.iib.platform.common.models.BaseComponentModel;
import com.iib.platform.common.objects.CfdEnach;

/**
 * Benifits of E-Nach Sling Model
 *
 * @author
 *
 */

@ConsumerType
public interface BenifitsOfEnachModel extends BaseComponentModel {

	public String getSectionHeading();

	public String getTitle();

	public String getDescription();

	public List<CfdEnach> getBenifitItems();

}
