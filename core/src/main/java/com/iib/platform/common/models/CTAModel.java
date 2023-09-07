package com.iib.platform.common.models;

import java.util.List;
import org.osgi.annotation.versioning.ConsumerType;
import com.iib.platform.common.objects.CTA;
import com.iib.platform.common.objects.WCMComponent;

/**
 * Tabs Model
 *
 * @author TechChefz (TCZ Consulting LLP)
 *
 */
@ConsumerType
public interface CTAModel extends BaseComponentModel {

	public String getAlign();

	public List<CTA> getCtaList();

	public WCMComponent getComponent();

}
