package com.iib.platform.enach.models;

import java.util.List;

import org.osgi.annotation.versioning.ConsumerType;

import com.iib.platform.common.models.BaseComponentModel;
import com.iib.platform.common.objects.Enach;

/**
 * Benifits of E-Nach Sling Model
 *
 * @author TechChefz (TCZ Consulting LLP)
 *
 */

@ConsumerType
public interface BenifitsOfEnachModel extends BaseComponentModel {

	public String getSectionHeading();

	public String getTitle();

	public String getDescription();

	public List<Enach> getBenifitItems();

}
