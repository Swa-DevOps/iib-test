 package com.iib.platform.services.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.jcr.Session;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.json.JSONObject;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iib.platform.cfdenach.models.CFDEnachSessionForm;
import com.iib.platform.services.DatabaseConnectionService;
import com.iib.platform.services.createFluxMandate;
import com.iib.platform.services.config.CreateFluxMandateConfig;

/**
 * Create Flux Service Implementation
 *
 * @author Niket Goel
 *
 */
@Component(immediate = true, service = { createFluxMandate.class }, enabled = true, property = {
		Constants.SERVICE_DESCRIPTION + "=" + "Create Flux Mandate Service Implementation" })
@Designate(ocd = CreateFluxMandateConfig.class)
public class CreateFluxMandateImpl implements createFluxMandate {

	/** Static Logger */
	private static Logger log = LoggerFactory.getLogger(CreateFluxMandateImpl.class);

	@Reference
	private ResourceResolverFactory resourceResolverFactory;

	ResourceResolver resourceResolver;

	Session session;

	@Reference
	DatabaseConnectionService databaseConService;

	String url;
	String clientidname;
	String clientidvalue;
	String secretidname;
	String secretidvalue;
	String includeHeader;

	@Activate
	protected void activate(CreateFluxMandateConfig config) {
		url = config.getURLCreateMandateFlux();
		clientidname = config.getClientID();
		clientidvalue = config.getClientIDValue();
		secretidname = config.getClientSecretKey();
		secretidvalue = config.getClientSecretValue();
		includeHeader = config.getIncludeHeader();

	}

	@Override
	public boolean createMandateforFlux(String emandateNumber) {
		JSONObject jsonObj = new JSONObject();
		try {
			List<String> mandateDetails = null;
			mandateDetails = databaseConService.getForMandateCreate(emandateNumber);
			JSONObject requestJson = new JSONObject();

			JSONObject emandateDetails = new JSONObject();

			if (mandateDetails.size() > 28) {
				emandateDetails.put("npciRefMsgId", mandateDetails.get(28));
			} else {
				emandateDetails.put("npciRefMsgId", "");
			}

			emandateDetails.put("merchantRefMsgId", emandateNumber);
			emandateDetails.put("seqType", "RCUR");

			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-DD");
			String convertedDateStr = dateFormatter.format(calendar.getTime());
			emandateDetails.put("createDate", convertedDateStr);
			emandateDetails.put("customerAccType", "SAVINGS");
			emandateDetails.put("corporateName", mandateDetails.get(3));
			emandateDetails.put("utilityCode", "INDB00160000010226");
			emandateDetails.put("mandateCataogoryCode", "A001");
			emandateDetails.put("payerName", mandateDetails.get(3));
			emandateDetails.put("payerAccountNumber", mandateDetails.get(16));
			emandateDetails.put("consumerRef", mandateDetails.get(7));
			emandateDetails.put("schemeRef", "API Mandate");
			emandateDetails.put("collectionAmount", mandateDetails.get(1));
			emandateDetails.put("frequency", mandateDetails.get(15));
			emandateDetails.put("mndStartdate", mandateDetails.get(4));
			emandateDetails.put("mndEnddate", mandateDetails.get(14));
			emandateDetails.put("sponserBankCode", "INDB0000098");
			emandateDetails.put("destbankCode", mandateDetails.get(8));
			emandateDetails.put("payerMobileNo", mandateDetails.get(2));

			emandateDetails.put("payerEmailId", mandateDetails.get(23));
			JSONObject request = new JSONObject();
			request.put("emandateDetails", emandateDetails);

			JSONObject header = new JSONObject();
			header.put("sourceId", "1001");
			header.put("mndAction", "CREATE");
			header.put("msgId", emandateNumber);
			requestJson.put("header", header);
			requestJson.put("request", request);
			HttpClient httpclient = HttpClientBuilder.create().build();
			URI uri = new URIBuilder(url).build();
			HttpPost httpPostRequest = new HttpPost(uri);

			if (includeHeader.equalsIgnoreCase("Yes")) {
				httpPostRequest.setHeader(secretidname, secretidvalue);
				httpPostRequest.setHeader(clientidname, clientidvalue);
			}

			StringEntity requestEntity = new StringEntity(requestJson.toString(), ContentType.APPLICATION_JSON);

			httpPostRequest.setEntity(requestEntity);

			HttpResponse resp = (HttpResponse) httpclient.execute(httpPostRequest);
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

			jsonObj.put("resultSet", new JSONObject(sb.toString()));

			JSONObject resultSet = jsonObj.optJSONObject("resultSet");
			JSONObject objects = resultSet.getJSONObject("header");

			String errorMessage = null;

			if (objects.getString("successMsg").equalsIgnoreCase("FAIL")) {
				errorMessage = objects.getString("errMsg");
			}

			if (databaseConService.updateFluxResult(emandateNumber, objects.getString("successMsg"), errorMessage))
				return true;
			else
				return false;

		} catch (Exception e) {

			databaseConService.updateFluxResult(emandateNumber, "FAIL", "ISSUE IN API");
			log.error("Exception in MandateDetailsServlet :: ", e);
			return false;
		}
	}
	
	@Override
	public boolean createMandateforFluxForCFD(String emandateNumber, String action) {
		JSONObject jsonObj = new JSONObject();
		try {
			CFDEnachSessionForm  mandateDetails = null;
			mandateDetails = databaseConService.getCFDenachdetails(emandateNumber,action);
			JSONObject requestJson = new JSONObject();

			JSONObject emandateDetails = new JSONObject();

			emandateDetails.put("npciRefMsgId", "");
			
			/*if (mandateDetails.size() > 28) {
				emandateDetails.put("npciRefMsgId", mandateDetails.get(28));
			} else {
				emandateDetails.put("npciRefMsgId", "");
			}*/

			emandateDetails.put("merchantRefMsgId", emandateNumber);
			emandateDetails.put("seqType", "RCUR");

			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-DD");
			String convertedDateStr = dateFormatter.format(calendar.getTime());
			emandateDetails.put("createDate", convertedDateStr);
			emandateDetails.put("customerAccType", "SAVINGS");
			emandateDetails.put("corporateName", mandateDetails.getAccdetcustbank());
			emandateDetails.put("utilityCode", "INDB00160000010226");
			emandateDetails.put("mandateCataogoryCode", "A001");
			emandateDetails.put("payerName", mandateDetails.getAccdetcustname());
			emandateDetails.put("payerAccountNumber", mandateDetails.getAccountno());
			emandateDetails.put("consumerRef", mandateDetails.getCifid());
			emandateDetails.put("schemeRef", "API Mandate");
			emandateDetails.put("collectionAmount", mandateDetails.getSmiamount());
			emandateDetails.put("frequency", mandateDetails.getFrequency());
			emandateDetails.put("mndStartdate", mandateDetails.getStartdate());
			emandateDetails.put("mndEnddate", mandateDetails.getEnddate());
			emandateDetails.put("sponserBankCode", "INDB0000098");
			emandateDetails.put("destbankCode", mandateDetails.getAccdetcustifsc());
			emandateDetails.put("payerMobileNo",mandateDetails.getMobileno() );

			emandateDetails.put("payerEmailId", mandateDetails.getEmail());
			JSONObject request = new JSONObject();
			request.put("emandateDetails", emandateDetails);

			JSONObject header = new JSONObject();
			header.put("sourceId", "1001");
			header.put("mndAction", "CREATE");
			header.put("msgId", emandateNumber);
			requestJson.put("header", header);
			requestJson.put("request", request);
			HttpClient httpclient = HttpClientBuilder.create().build();
			URI uri = new URIBuilder(url).build();
			HttpPost httpPostRequest = new HttpPost(uri);

			if (includeHeader.equalsIgnoreCase("Yes")) {
				httpPostRequest.setHeader(secretidname, secretidvalue);
				httpPostRequest.setHeader(clientidname, clientidvalue);
			}

			StringEntity requestEntity = new StringEntity(requestJson.toString(), ContentType.APPLICATION_JSON);

			httpPostRequest.setEntity(requestEntity);

			HttpResponse resp = (HttpResponse) httpclient.execute(httpPostRequest);
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

			jsonObj.put("resultSet", new JSONObject(sb.toString()));

			JSONObject resultSet = jsonObj.optJSONObject("resultSet");
			JSONObject objects = resultSet.getJSONObject("header");

			String errorMessage = null;

			if (objects.getString("successMsg").equalsIgnoreCase("FAIL")) {
				errorMessage = objects.getString("errMsg");
			}

			if (databaseConService.updateFluxResultCFD(emandateNumber, objects.getString("successMsg"), errorMessage))
				return true;
			else
				return false;

		} catch (Exception e) {

			databaseConService.updateFluxResultCFD(emandateNumber, "FAIL", "ISSUE IN API");
			log.error("Exception in CFD Flux Update  :: ", e);
			return false;
		}
	}
}
