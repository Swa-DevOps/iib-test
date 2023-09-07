package com.iib.platform.services;

import org.osgi.annotation.versioning.ConsumerType;

@ConsumerType
public interface StoreOTPService {

	public void storeOTP(String mobile, int otp, String appId);

	public void storeOTPWithSession(String mobile, int otp, String appId, String key);
	
	public void storeOTPWithSession(String mobile, String otp, String appId, String key);
	
	public int checkCountOfOTP(String mobile, String appId, String key, String action);

}
