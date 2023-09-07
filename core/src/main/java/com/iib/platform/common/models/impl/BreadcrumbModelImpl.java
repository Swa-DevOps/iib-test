package com.iib.platform.common.models.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.iib.platform.common.models.BreadcrumbModel;
import com.iib.platform.common.objects.PageObject;
import com.iib.platform.common.objects.WCMComponent;
import com.iib.platform.common.utils.ComponentsUtil;

/**
 * Sling Model Implementation of Breadcrumb Model
 *
 * @author TechChefz (TCZ Consulting LLP)
 *
 */
@Model(adaptables = Resource.class, adapters = BreadcrumbModel.class)
public class BreadcrumbModelImpl implements BreadcrumbModel {

	/** Logger. **/
	private static final Logger log = LoggerFactory.getLogger(BreadcrumbModelImpl.class);

	@Inject
	ResourceResolver resourceResolver;

	@Inject
	Resource resource;

	@Inject @Optional
	private String customHeight;

	@Inject @Optional
	private String customStyleClass;

	@Inject @Optional
	private String hideOnMobile;

	@Inject @Optional
	private String hideOnTablet;

	private WCMComponent wcmComponent;

	@Inject @Optional
	String parentSitePath;

	private  LinkedList<HashMap<String,String>> breadcrmbList ;
	private HashMap<String,String> mMap;
	private List<HashMap<String,String>> breadcrumbList;

	@PostConstruct
	public void init() {
		try {
		if (parentSitePath != null) {

			wcmComponent= ComponentsUtil.getComponentDetails("breadcrumb", customHeight, customStyleClass, hideOnMobile, hideOnTablet);
			
			mMap = new HashMap<String,String>();
			
			breadcrmbList = new LinkedList<HashMap<String,String>>();
			breadcrumbList = new LinkedList<HashMap<String,String>>();

			Resource entityPageResource = resourceResolver.getResource(parentSitePath);
			Page parentPageSite = entityPageResource.adaptTo(Page.class);
			Resource currentResource = resource.getParent();
			Resource pageresource;
		
			if(currentResource.getName().equalsIgnoreCase("jcr:content")) {
				pageresource = currentResource.getParent();
			}
			else if (currentResource.getName().equalsIgnoreCase("section-par")){
				pageresource = currentResource.getParent().getParent();
			}
			
			else {
				pageresource=currentResource.getParent().getParent().getParent();
			}
			Page ResourcePage = pageresource.adaptTo(Page.class);

			try {
				if (ResourcePage != null) {
					Resource PageResource = ResourcePage.adaptTo(Resource.class);
					while (!PageResource.getPath().equals(parentPageSite.getPath())) {
						Page currentPage = PageResource.adaptTo(Page.class);

						if (currentPage != null) {
							PageObject finalPage = new PageObject(currentPage.getProperties(), currentPage.getPath());													
							mMap.put("title", finalPage.getTitle());
							mMap.put("PageUrl", finalPage.getPageUrl());
							breadcrmbList.add(mMap);
							PageResource = PageResource.getParent();
							mMap = new HashMap<String,String>();
						}

					}
					Page doPage = PageResource.adaptTo(Page.class);
					PageObject lastPage = new PageObject(doPage.getProperties(), doPage.getPath());
					mMap.put("title", lastPage.getTitle());
					mMap.put("PageUrl", lastPage.getPageUrl());
					breadcrmbList.add(mMap);
					
					for(int i=breadcrmbList.size(); i>0; i--) {
						breadcrumbList.add(breadcrmbList.get(i-1));
					}
				}
			} catch (Exception e) {
				log.error("" , e);

			}
		}
		}catch (Exception e)
		{
			log.error("" , e);
			
		}
		
	}

	@Override
	public List<HashMap<String,String>> getBreadcrumbList() {
		return breadcrumbList;
	}

	@Override
	public WCMComponent getComponent() {
		return wcmComponent;
	}

}