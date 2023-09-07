package com.iib.platform.services.impl;

import javax.jcr.Session;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.framework.Constants;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iib.platform.services.DatabaseConnectionService;
import com.iib.platform.services.StoreOTPService;
import com.iib.platform.services.config.LoanAPIServletConfig;

/**
 * Store OTP Service Implementation
 *
 * @author niket goel
 *
 */
@Component(immediate = true, service = { StoreOTPService.class }, property = {
		Constants.SERVICE_DESCRIPTION + "=" + "Store OTP Service Implementation" })
public class StoreOTPServiceImpl implements StoreOTPService {

	/** Static Logger */
	private static Logger log = LoggerFactory.getLogger(StoreOTPServiceImpl.class);

	@Reference
	private ResourceResolverFactory resourceResolverFactory;

	ResourceResolver resourceResolver;

	Session session;

	@Reference
	DatabaseConnectionService databaseConService;

	@Activate
	protected void activate(LoanAPIServletConfig config) {
		log.info("Activated StoreOTPServiceImpl");

	}

	@Deactivate
	protected void deactivate(ComponentContext ctx)  {
		log.info("Deactivated StoreOTPServiceImpl");
		session.logout();
	}

	@Override
	public void storeOTP(String mobile, int otp, String appId) {

		/** Code Snippet to Store OTP in "otpUsers" Node */
		try {

			// Database Changes START
			String otpData = "" + otp;
			boolean otpStoredSuccess = databaseConService.storeOtpDetails(mobile, otpData, appId);
			if (!otpStoredSuccess) {
				log.debug("OTP Store in DB issue occurred for mobile number : {}", mobile);
			}
			// Database Changes END

		} catch (Exception e) {
			log.error("Exception in StoreOTPService :: ", e);
		}
	}

	@Override
	public void storeOTPWithSession(String mobile, int otp, String appId, String key) {

		/** Code Snippet to Store OTP in "otpUsers" Node */
		try {

			// Database Changes START
			String otpData = "" + otp;
			boolean otpStoredSuccess = databaseConService.storeOtpDetailsWithKey(mobile, otpData, appId, key);
			log.debug("OTP Store action calaled and returnedf : {} {} {} {} {} ", otpStoredSuccess, mobile, otpData,
					appId, key);
			if (!otpStoredSuccess) {
				log.debug("OTP Store in DB issue occurred for mobile number : {}", mobile);
			}
			// Database Changes END

		} catch (Exception e) {
			log.error("Exception in StoreOTPService :: ", e);
		}
	}
	
	
	@Override
	public void storeOTPWithSession(String mobile, String otp, String appId, String key) {

		/** Code Snippet to Store OTP in "otpUsers" Node */
		try {

			// Database Changes START
			String otpData = otp;
			boolean otpStoredSuccess = databaseConService.storeOtpDetailsWithKey(mobile, otpData, appId, key);
			log.debug("OTP Store action calaled and returnedf : {} {} {} {} {} ", otpStoredSuccess, mobile, otpData,
					appId, key);
			if (!otpStoredSuccess) {
				log.debug("OTP Store in DB issue occurred for mobile number : {}", mobile);
			}
			// Database Changes END

		} catch (Exception e) {
			log.error("Exception in StoreOTPService :: ", e);
		}
	}
	
	
	@Override
	public int checkCountOfOTP(String mobile,String appId, String key, String action) {

		int  noOfCallStored =-1;
		/** Code Snippet to Store OTP in "otpUsers" Node */
		try {

			// Database Changes START
			noOfCallStored = databaseConService.checkCountOfOTP(mobile, appId, key, action);
			log.info("noOfCallStored : {} ", noOfCallStored);
			
			return noOfCallStored;
			// Database Changes END

		} catch (Exception e) {
			log.error("Exception in StoreOTPService :: ", e);
		}
		
		return noOfCallStored;
	}
}
