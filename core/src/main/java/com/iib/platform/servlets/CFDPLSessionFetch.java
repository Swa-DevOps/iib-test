package com.iib.platform.servlets;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.List;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;

import javax.servlet.Servlet;
import javax.servlet.ServletException;


import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.iib.platform.services.DatabaseConnectionService;

/**
 * Servlet that writes some sample content into the response. It is mounted for
 * all resources of a specific Sling resource type. The
 * {@link SlingAllMethodsServlet} shall be used for HTTP methods that are
 * idempotent. For write operations use the @link SlingAllMethodsServlet.
 */

@Component(service = Servlet.class,
        property = {
                Constants.SERVICE_DESCRIPTION + "=Concent form Servlet",
                "sling.servlet.methods=" + HttpConstants.METHOD_POST,
                "sling.servlet.paths=" + "/bin/cfdpl/cfdplsessionme",
                "sling.servlet.extensions=" + "sample"
        })
public class CFDPLSessionFetch extends SlingAllMethodsServlet {
	
	@Reference
	transient DatabaseConnectionService databaseConService;
    private static final String EMAIL_LOWER_CASE = "email";
    private static final String FAIL = "unathorize";
    private static final String SUCCESS = "success";
    private static final Logger LOGGER = LoggerFactory.getLogger(CFDPLSessionFetch.class);

    /**
     *
     * @param slingRequest : request
     * @param slingResponse : response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(final SlingHttpServletRequest slingRequest, final SlingHttpServletResponse slingResponse)
            throws ServletException, IOException {

        slingRequest.setCharacterEncoding("utf8");
        slingResponse.setContentType("application/json");
        PrintWriter out = slingResponse.getWriter();


        Map<String, Object> formKeyValue = getXssEncodedRequestParamMap(slingRequest.getParameterMap());
        Map<String, String> finalMap = new HashMap<>();
        Iterator hmIterator = formKeyValue.entrySet().iterator();
        Map<String, String> responseMap = new HashMap<>();

        LOGGER.info("Fetched from url {} ",formKeyValue);
        while (hmIterator.hasNext()) {
            Map.Entry mapElement = (Map.Entry)hmIterator.next();
            String key = mapElement.getKey().toString();
            String value = (String) mapElement.getValue();
            List<String> fetchresult= null;
            LOGGER.info("Fetched from url {} {} ",key, value);
            
            if(key.equalsIgnoreCase("sso"))
            {
            	try {
            	fetchresult = databaseConService.getCFDplsession(value,"all");
            	
            	finalMap.put("status",SUCCESS);
        		finalMap.put("session",fetchresult.get(0));
        		finalMap.put("mobileno",fetchresult.get(1));
        		finalMap.put(EMAIL_LOWER_CASE ,fetchresult.get(2));
        		finalMap.put("cifid",fetchresult.get(3));
        		finalMap.put("dealno",fetchresult.get(4));
        		finalMap.put("accdetcustname",fetchresult.get(5));
        		finalMap.put("accdetcustbank",fetchresult.get(6));
        		finalMap.put("accdetcustifsc",fetchresult.get(7));
        		finalMap.put("accdetcustacc",fetchresult.get(8));
        		finalMap.put("startdate",fetchresult.get(9));
        		finalMap.put("enddate",fetchresult.get(10));
        		finalMap.put("frequency",fetchresult.get(11));
        		finalMap.put("emiamount",fetchresult.get(12));
        		finalMap.put("timestamp",fetchresult.get(13));
        		
        		responseMap=finalMap;
            	LOGGER.info("Fetched from Database {} ",fetchresult);		
            	}catch(Exception e)
            	{
            		finalMap.put("status",FAIL);
            		LOGGER.error("Excetpion", e);
            	}            	
            		
            } else {
               //To Do
            	finalMap.put("status",FAIL);
            }
        }
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        out.print(gson.toJson(responseMap));
    }

   

    /**
     *
     * @param parameterMap :- parameter map from browser.
     * @return :- Get Xss Encoded Request Param Map.
     */
    private Map<String, Object> getXssEncodedRequestParamMap(final Map<String, String[]> parameterMap) {
        final Map<String, Object> xssEncodedRequestParams = new HashMap<>();
        if (parameterMap != null && parameterMap.size() > 0) {
            for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
                xssEncodedRequestParams.put(entry.getKey(), entry.getValue()[0]);
            }
        }
        return xssEncodedRequestParams;
    }
}