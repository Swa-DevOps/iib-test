package com.iib.platform.common.models;

import java.util.List;

import org.osgi.annotation.versioning.ConsumerType;
import com.iib.platform.common.objects.Teaser;

/**
 * List of Teasers Model
 *
 * @author TechChefz (TCZ Consulting LLP)
 *
 */
/**
 * @author TechChefz (TCZ Consulting LLP)
 *
 */
@ConsumerType
public interface ListOfTeasersModel extends BaseComponentModel {

	public String getSectionHeading();

	public List<Teaser> getTeaserItems();

}
