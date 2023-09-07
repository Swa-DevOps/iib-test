package com.iib.platform.common.models.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;

import com.iib.platform.common.models.ColumnGridModel;
import com.iib.platform.common.objects.WCMComponent;
import com.iib.platform.common.utils.ComponentsUtil;

@Model(adaptables = Resource.class, adapters = ColumnGridModel.class)
public class ColumnGridModelImpl implements ColumnGridModel {

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

	@Inject
	@Optional
	private String sectionHeading;

	private WCMComponent wcmComponent;

	@Inject
	@Optional
	@Default(intValues = 1)
	private int columns;

	private List<String> columnList;

	@PostConstruct
	public void init() {

		wcmComponent = ComponentsUtil.getComponentDetails("columnGrid", customHeight, customStyleClass, hideOnMobile,
				hideOnTablet);

		columnList = new ArrayList<>();

		for (int count = 0; count < columns; count++) {
			columnList.add("col-par-" + count + 1);
		}
	}

	@Override
	public String getSectionHeading() {
		return sectionHeading;
	}

	@Override
	public List<String> getColumnList() {
		return columnList;
	}

	@Override
	public WCMComponent getComponent() {
		return wcmComponent;
	}

}