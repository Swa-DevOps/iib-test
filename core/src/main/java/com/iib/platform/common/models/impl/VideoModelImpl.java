package com.iib.platform.common.models.impl;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;

import com.iib.platform.common.models.VideoModel;
import com.iib.platform.common.objects.WCMComponent;
import com.iib.platform.common.utils.ComponentsUtil;

@Model(adaptables = Resource.class, adapters = VideoModel.class)
public class VideoModelImpl implements VideoModel {

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

	/**
	 * This method will construct the model after doing injections.
	 */
	@PostConstruct
	public void init() {

		wcmComponent = ComponentsUtil.getComponentDetails("video", customHeight, customStyleClass, hideOnMobile,
				hideOnTablet);

	}

	@Override
	public WCMComponent getComponent() {
		return wcmComponent;
	}

}
