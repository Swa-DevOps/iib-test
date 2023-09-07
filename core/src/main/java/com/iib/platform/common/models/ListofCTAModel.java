package com.iib.platform.common.models;

import java.util.List;
import com.iib.platform.common.objects.CTA;

public interface ListofCTAModel extends BaseComponentModel {

	public String getAlignment();

	public List<CTA> getCTAMap();

}
