package com.iib.platform.services.impl;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import com.day.cq.mailer.MessageGateway;
import com.day.cq.mailer.MessageGatewayService;
import com.iib.platform.common.objects.EmailVO;
import com.iib.platform.services.EmailService;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Email Service Implementation
 *
 */
@Component(immediate = true, service = { EmailService.class }, enabled = true, property = {
		Constants.SERVICE_DESCRIPTION + "=" + "IIB Email Service Impl" })
@Designate(ocd = EmailServiceImpl.Config.class)
public class EmailServiceImpl implements EmailService {

	@Reference
	ResourceResolverFactory resourceResolverFactory;

	@Reference
	private MessageGatewayService messageGatewayService;

	private String diffFromAddress;

	private final Logger log = LoggerFactory.getLogger(getClass());

	@ObjectClassDefinition(name = "IIB PLATFORM Email Server Configuration")
	public static @interface Config {

		@AttributeDefinition(name = "Use Diffrent From Address As per configutaion if yes :- enter values as 'DIFF'")
		String diffFromAddress() default "NODIFF";
	}

	@Override
	public boolean sendEmailUsingDayCQ(final EmailVO emailVO) {

		boolean isEmailSent = false;
		try {
			log.debug("Unable process and send report 1");
			MessageGateway messageGateway = this.messageGatewayService.getGateway(HtmlEmail.class);
			log.debug("Unable process and send report 2");
			MultiPartEmail multiPartEmail = new MultiPartEmail();
			log.debug("Unable process and send report 3");
			String fileName = emailVO.getfileEmailName();
			log.debug("Unable process and send report 4");
			EmailAttachment attachment = new EmailAttachment();
			log.debug("Unable process and send report 5");
			attachment.setPath(emailVO.getfileEmailPath());
			log.debug("Unable process and send report 6");
			attachment.setDisposition(EmailAttachment.ATTACHMENT);
			log.debug("Unable process and send report 7");
			attachment.setDescription("MIS Lead Report");
			log.debug("Unable process and send report 8");
			attachment.setName(fileName);
			log.debug("Unable process and send report 9");

			String emailArray[] = (emailVO.getToAddress()).split(";");
			log.debug("Unable process and send report 10");
			multiPartEmail.addTo(emailArray);
			log.debug("Unable process and send report 11");
			multiPartEmail.setSubject(emailVO.getSubject());
			log.debug("Unable process and send report 12");
			multiPartEmail.setMsg(emailVO.getBodyMessage());
			log.debug("Unable process and send report 13");
			multiPartEmail.attach(attachment);
			log.debug("Unable process and send report 14");
			if(diffFromAddress.equalsIgnoreCase("DIFF"))
				multiPartEmail.setFrom(emailVO.getFromAddress());
			log.debug("Unable process and send report 15");
			messageGateway.send(multiPartEmail);
			log.debug("Unable process and send report 16");
			isEmailSent = true;

		} catch (Exception e) {
			log.debug("Unable process and send report {} {}", emailVO.getfileEmailName(), e);
		}

		return isEmailSent;
	}
	@Activate
	protected void activate(final Config config) {

		diffFromAddress=config.diffFromAddress();
	}

	@Override
	public boolean sendSimpleEmailUsingDayCQ(EmailVO emailVO) {

		boolean isEmailSent = false;
		try {
			MessageGateway messageGateway = this.messageGatewayService.getGateway(HtmlEmail.class);
			MultiPartEmail multiPartEmail = new MultiPartEmail();
			String emailArray[] = (emailVO.getToAddress()).split(";");
			multiPartEmail.addTo(emailArray);
			multiPartEmail.setSubject(emailVO.getSubject());
			 multiPartEmail.setMsg(emailVO.getBodyMessage());
			//multiPartEmail.setContent(emailVO.getBodyMessage(), "text/html");
			if(diffFromAddress.equalsIgnoreCase("DIFF"))
				multiPartEmail.setFrom(emailVO.getFromAddress());
			messageGateway.send(multiPartEmail);
			isEmailSent = true;

		} catch (Exception e) {
			log.debug("Unable process and send report {} {}", emailVO.getfileEmailName(), e);
		}

		return isEmailSent;
	}

}