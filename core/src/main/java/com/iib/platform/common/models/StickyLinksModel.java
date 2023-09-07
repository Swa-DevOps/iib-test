package com.iib.platform.common.models;

import java.util.List;
import org.osgi.annotation.versioning.ConsumerType;
import com.iib.platform.common.objects.MenuItem;

@ConsumerType
public interface StickyLinksModel extends BaseComponentModel {

	public String getHideByDefault();

	public String getButtonText();

	public List<MenuItem> getLinks();

	public String getViewAllText();

	public String getViewAllLink();

}
