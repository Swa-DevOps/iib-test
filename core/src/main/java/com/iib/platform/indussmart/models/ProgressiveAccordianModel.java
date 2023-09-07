package com.iib.platform.indussmart.models;

import java.util.List;
import org.osgi.annotation.versioning.ConsumerType;
import com.iib.platform.common.models.BaseComponentModel;
import com.iib.platform.common.objects.AccordianItem;

/**
 * Progressive Accordian Model
 *
 * @author TechChefz (TCZ Consulting LLP)
 *
 */
@ConsumerType
public interface ProgressiveAccordianModel extends BaseComponentModel {

	public List<AccordianItem> getAccordianItems();

}
