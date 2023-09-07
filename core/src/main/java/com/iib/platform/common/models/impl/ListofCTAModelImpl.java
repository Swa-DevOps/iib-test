package com.iib.platform.common.models.impl;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iib.platform.common.models.ListofCTAModel;
import com.iib.platform.common.objects.CTA;
import com.iib.platform.common.objects.WCMComponent;
import com.iib.platform.common.utils.ComponentsUtil;

@Model(adaptables = Resource.class, adapters = ListofCTAModel.class, resourceType = ListofCTAModelImpl.RESOURCE_TYPE)
public class ListofCTAModelImpl implements ListofCTAModel {

	/** Logger. **/
	private static final Logger logger = LoggerFactory.getLogger(ListofCTAModelImpl.class);

	protected static final String CTAS = "ctas";
	protected static final String TEXT = "text";
	protected static final String COLOR = "color";
	protected static final String URL = "url";
	protected static final String ICON = "icon";
	protected static final String TYPE = "type";
	protected static final String RESOURCE_TYPE = "re-platform/components/content/common/ctarow";

	@ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
	private String align;

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
	private Resource resource;

	private List<CTA> ctaMap;

	@PostConstruct
	public void init() {

		wcmComponent = ComponentsUtil.getComponentDetails("list-of-cta", customHeight, customStyleClass, hideOnMobile,
				hideOnTablet);

		Resource ctaResource = resource.getChild(CTAS);
		if ((ctaResource != null) && (ctaResource instanceof Resource)) {
			Iterable<Resource> ctaChildResources = ctaResource.getChildren();

			for (Resource ctaChildResource : ctaChildResources) {
				ValueMap resourceProperties = ctaChildResource.adaptTo(ValueMap.class);
				CTA cta = new CTA();
				if (resourceProperties != null) {
					cta.setColor(resourceProperties.get(COLOR, String.class));
					cta.setText(resourceProperties.get(TEXT, String.class));
					cta.setIcon(resourceProperties.get(ICON, String.class));
					cta.setUrl(resourceProperties.get(URL, String.class));
					cta.setType(resourceProperties.get(TYPE, String.class));
					ctaMap.add(cta);
				} else {
					logger.info("resourceProperties is null here !!!!");
				}

			}
		}
	}

	@Override
	public String getAlignment() {
		return align;
	}

	@Override
	public List<CTA> getCTAMap() {
		return ctaMap;
	}

	@Override
	public WCMComponent getComponent() {
		return wcmComponent;
	}

}