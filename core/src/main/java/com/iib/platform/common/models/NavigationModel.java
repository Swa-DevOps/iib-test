package com.iib.platform.common.models;

import java.util.List;
import org.osgi.annotation.versioning.ConsumerType;
import com.iib.platform.common.objects.MenuItem;

/**
 * Navigation Model
 *
 * @author TechChefz (TCZ Consulting LLP)
 *
 */
@ConsumerType
public interface NavigationModel extends BaseComponentModel {

	public List<MenuItem> getListItems();

	public String getHamburgerMenuText();
}
