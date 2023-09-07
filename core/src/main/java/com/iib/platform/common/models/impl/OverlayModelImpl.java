package com.iib.platform.common.models.impl;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iib.platform.common.models.OverlayModel;
import com.iib.platform.common.objects.WCMComponent;
import com.iib.platform.common.utils.ComponentsUtil;

/**
 * Sling Model Implementation of Overlay Model
 *
 * @author TechChefz (TCZ Consulting LLP)
 *
 */
@Model(adaptables = Resource.class, adapters = OverlayModelImpl.class)
public class OverlayModelImpl implements OverlayModel {

	/** Logger. **/
	private static final Logger log = LoggerFactory.getLogger(OverlayModelImpl.class);

	@Inject
	@Optional
	private String customHeight;

	@Inject
	@Optional
	private String customStyleClass;

	@Inject
	@Optional
	private String hideOnMobile;

	@Inject
	@Optional
	private String hideOnTablet;

	private WCMComponent wcmComponent;

	@Inject
	@Optional
	private String sectionHeading;

	@Inject
	@Optional
	private String closeIconClass;

	@Inject
	@Optional
	private String opacity;

	@Inject
	@Optional
	@Default(values = "login")
	private String includeComponent;

	private WCMComponent component;

	@PostConstruct
	public void init() {

		wcmComponent = ComponentsUtil.getComponentDetails("overlay", customHeight, customStyleClass, hideOnMobile,
				hideOnTablet);

		Map<String, WCMComponent> componentsMap = ComponentsUtil.getComponents();
		component = new WCMComponent();
		component = componentsMap.get(includeComponent);
		component.setId(includeComponent + wcmComponent.getRandomNumber());

	}

	@Override
	public String getSectionHeading() {
		return sectionHeading;
	}

	@Override
	public String getCloseIconClass() {
		return closeIconClass;
	}

	@Override
	public String getOpacity() {
		return opacity;
	}

	@Override
	public WCMComponent getWcmComponent() {
		log.info("Returned");
		return component;
	}

	@Override
	public WCMComponent getComponent() {
		log.info("Returned Comp");
		return wcmComponent;
	}

}