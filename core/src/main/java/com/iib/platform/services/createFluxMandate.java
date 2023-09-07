package com.iib.platform.services;

import org.osgi.annotation.versioning.ConsumerType;

@ConsumerType
public interface createFluxMandate {

	public boolean createMandateforFlux(String emandateNumber);
	public boolean createMandateforFluxForCFD(String emandateNumber, String action);

}
