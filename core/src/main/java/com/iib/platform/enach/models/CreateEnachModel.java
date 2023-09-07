package com.iib.platform.enach.models;

import java.util.List;

import org.osgi.annotation.versioning.ConsumerType;

import com.iib.platform.common.objects.MenuItem;

@ConsumerType
public interface CreateEnachModel {

	public String getBackButtonUrl();

	public String getCancelButtonUrl();

	public List<MenuItem> getListItems();

}
