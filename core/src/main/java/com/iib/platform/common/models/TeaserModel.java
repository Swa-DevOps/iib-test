package com.iib.platform.common.models;

import org.osgi.annotation.versioning.ConsumerType;
import com.iib.platform.common.objects.Image;

/**
 * Teaser Model
 *
 * @author TechChefz (TCZ Consulting LLP)
 *
 */
@ConsumerType
public interface TeaserModel extends BaseComponentModel {

	public String getTitle();

	public Image getImage();

	public String getDescription();

	public String getCtaText();

	public String getCtaOpenInNewWindow();

	public String getLink();

}
