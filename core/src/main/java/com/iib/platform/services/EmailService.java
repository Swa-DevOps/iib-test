package com.iib.platform.services;

import com.iib.platform.common.objects.EmailVO;

public interface EmailService {

	/**
	 * This method is used read email txt from dam and send an email.
	 * 
	 * @param emailVO
	 * @return
	 */
	public boolean sendEmailUsingDayCQ(final EmailVO emailVO);

	public boolean sendSimpleEmailUsingDayCQ(final EmailVO emailVO);
}
