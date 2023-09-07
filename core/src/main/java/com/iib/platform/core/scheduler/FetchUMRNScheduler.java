package com.iib.platform.core.scheduler;

import java.io.BufferedReader;
import java.util.List;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import org.json.JSONObject;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iib.platform.services.DatabaseConnectionService;
import com.iib.platform.common.utils.EncryptDecrypt;
import com.iib.platform.core.smsemail.EmailSmsApiService;

@Component(immediate = true, configurationPid = "com.iib.platform.core.schedulers.FetchUMRNScheduler")
@Designate(ocd = FetchUMRNScheduler.Configuration.class)
public class FetchUMRNScheduler implements Runnable {

	protected final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private static String DBENCRYPTYKEY= "!ndus!nd!ndus!nd";

	@Reference
	private DatabaseConnectionService databaseConService;

	@Reference
	private EmailSmsApiService emailSmsApiService;

	@Override
	public void run() {
		this.getNullUMRNMandateNumber();
	}

	private void getNullUMRNMandateNumber() {
		try {

			List<String> mandate = databaseConService.getMandateIdforNullUrn();

			for (String tempmandate : mandate) {

				List<String> npciDetails = databaseConService.getFluxDetailsforNullUrn(tempmandate);

				if (!npciDetails.isEmpty()) {

					String umrnNo = fetchUMRNumber(npciDetails.get(0), npciDetails.get(1), npciDetails.get(2));

					Boolean status = databaseConService.updateURMNONENACH(umrnNo, tempmandate);

					if (umrnNo.length() > 0) {
						List<String> custDetails = databaseConService.getClientDetailsSMSEMAIL(tempmandate, "ENACH");
						String customerEmail = custDetails.get(1);
						String mobileNo = EncryptDecrypt.decrypt(DBENCRYPTYKEY, DBENCRYPTYKEY, custDetails.get(0));

						callSMSEMAILTRIGGER(customerEmail, mobileNo, umrnNo, tempmandate);
					}

					log.debug("URNNO successfully updated for MandateID {} {}", tempmandate, status);

				}

			}

		} catch (Exception e) {
			log.error("Exceprion occured in NullURNMandateNumber() {}", e.getMessage());
		}

	}

	private void callSMSEMAILTRIGGER(String customerEmail, String mobileNo, String umrnNo, String tempmandate) {
		try {

			if ((customerEmail != null) && !(customerEmail.equalsIgnoreCase(""))) {
				log.info("EMAIL trigger for URMN");
				String response = emailSmsApiService.emailServiceCallWithExtraParam("EA0029", customerEmail,
						"Your Emandate Status from IndusInd Bank", "", "", "", "", tempmandate, "", 4);
				log.info(response);
			}
		} catch (Exception e) {
			log.error("Exception in EMAIL TRIGEER FROM URMN SUCESSESS :: ", e);
		}
		try {
			if ((mobileNo != null) && !(mobileNo.equalsIgnoreCase(""))) {
				log.info("SMS trigger for URMN");
				String response = emailSmsApiService.smsServiceCall(umrnNo, "EA0030", mobileNo, "http://indusind.com",
						3);
				log.info("SMS trigger for URMN {}", response);
			}

		} catch (Exception e) {
			log.error("Exception in SMS TRIGEER FROM URMN SUCESSESS :: ", e);
		}

	}

	private String fetchUMRNumber(String npcitxnid, String msgid, String customerrefnumber) {
		String umrn = StringUtils.EMPTY;
		String url = mandateUrl;
		try {
			JSONObject jsonObj = new JSONObject();

			HttpClient httpclient = HttpClientBuilder.create().build();
			URI uri = new URIBuilder(url).build();
			HttpPost httpPostRequest = new HttpPost(uri);

			if (headerConditon.equalsIgnoreCase("Yes")) {
				httpPostRequest.setHeader("X-IBM-Client-Secret", "F8fF4nK5bY4aE3dQ7uW1jY7hP7bI1sY5qW0hD6tJ5kH0iX4oO1");
				httpPostRequest.setHeader("X-IBM-Client-Id", "020939a3-017d-40d0-b011-511c2f52631b");
			}

			JSONObject requestJsonWrapper = new JSONObject();

			JSONObject headerJson = new JSONObject(
					"{ \"sourceId\": \"1002\", \"mndAction\": \"ENQUIRY\", \"msgId\": \"\" }");
			JSONObject requestJson = new JSONObject();

			long randomNumber = (long) Math.floor(Math.random() * 9000000000000L) + 1000000000000L;

			headerJson.put("msgId", "USFB" + randomNumber);
			requestJson.put("npciRefMsgId", npcitxnid);

			requestJsonWrapper.put("header", headerJson);
			requestJsonWrapper.put("request", requestJson);
			StringEntity requestEntity = new StringEntity(requestJsonWrapper.toString(), ContentType.APPLICATION_JSON);

			httpPostRequest.setEntity(requestEntity);

			HttpResponse resp = httpclient.execute(httpPostRequest);
			HttpEntity entity = resp.getEntity();

			// Read the content stream
			InputStream instream = entity.getContent();
			BufferedReader br = new BufferedReader(new InputStreamReader((instream)));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}

			jsonObj.put("transaction", new JSONObject(sb.toString()));

			log.info(" Result for URMN number is :-{} {} {} {}", sb, msgid, customerrefnumber, days);

			umrn = "";

		} catch (Exception e) {
			log.error("IOException occured in Fetch URMN IMPL {} ", e);
		}

		return umrn;

	}

	private long days;
	private String mandateUrl;
	private String headerConditon;

	@Activate
	protected void activate(Configuration config) {

		days = config.getdays();
		mandateUrl = config.getMandateUrl();
		headerConditon = config.getHeaderConditon();
	}

	@ObjectClassDefinition(name = "Fetch UMRN Scheduler  Configuration")
	public @interface Configuration {

		@AttributeDefinition(name = "within days", description = "eneter days to be search in database", type = AttributeType.LONG)
		long getdays() default 7;

		@AttributeDefinition(name = "Expression", description = "Cron-job expression. Default: run once in 24 hrs.", type = AttributeType.STRING)
		String scheduler_expression() default "0 0 0/24 1/1 * ? *";

		@AttributeDefinition(name = "Mandate URL", description = "eneter days to be search in database", type = AttributeType.STRING)
		String getMandateUrl() default "https://ibluatapig.indusind.com/app/uat/api/mandate";

		@AttributeDefinition(name = "Is Without Header", description = "If this Prod or UAT without Header", type = AttributeType.STRING)
		String getHeaderConditon() default "Yes";

	}
}
