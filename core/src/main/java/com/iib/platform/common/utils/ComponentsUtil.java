package com.iib.platform.common.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.iib.platform.common.objects.WCMComponent;

/**
 * Components Util - Static utility helpers to add random numbers
 * 
 * @author TechChefz
 */

public class ComponentsUtil {

	private ComponentsUtil() {
		// To Do

	}

	private static Random random = new Random();

	public static WCMComponent getComponentDetails(String componentName, String customHeight, String customStyleClass,
			String hideOnMobile, String hideOnTablet) {

		WCMComponent componentDetails = new WCMComponent();

		int randomNumber = random.nextInt(10000);

		componentDetails.setName(componentName);
		componentDetails.setId(componentName + randomNumber);
		componentDetails.setCustomHeight(customHeight);
		componentDetails.setCustomStyleClass(customStyleClass);
		componentDetails.setHideOnMobile(hideOnMobile);
		componentDetails.setHideOnTablet(hideOnTablet);
		componentDetails.setRandomNumber(Integer.toString(randomNumber));

		return componentDetails;
	}

	public static Map<String, WCMComponent> getComponents() {

		Map<String, WCMComponent> components = new HashMap<>();

		WCMComponent component = new WCMComponent();
		component.setName("login");
		component.setPath("re-platform/components/content/common/login");
		components.put("login", component);

		return components;
	}

}
