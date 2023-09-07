package com.iib.platform.cfdenach.models;

import com.iib.platform.common.objects.EnachSSO;
import com.iib.platform.services.DatabaseConnectionService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CFDEnachssoModelImpl{

    private static final Logger LOGGER = LoggerFactory.getLogger(CFDEnachssoModelImpl.class);
    private static final Double MAX_AMT_LIMIT = 1000000.00;
    
    @Inject
	DatabaseConnectionService databaseConService;
    
    @Inject
    private SlingHttpServletRequest request;


    CFDEnachSessionForm cFDEnachSessionCreateForm = new CFDEnachSessionForm();
    EnachSSO enachSSO = new EnachSSO();
    /**
     *
     */
    @PostConstruct
    public void postConstruct() {
    	LOGGER.info ( "Landed Here {}", request);
    	
    	if(request !=null) {
    		LOGGER.info ( "Landed Here 2  {}",request.getParameter("jcrsession") );
    	if (request.getParameter("jcrsession") != null) {
        		 LOGGER.info ( request.getParameter("jcrsession"));
        		 List<String> fetchresult= null;
        		 enachSSO = getParamValues(request.getParameter("jcrsession"));
        		
        		 	
             	try {
             		
             		LOGGER.info ("In try {}", enachSSO.getSession() );
             		if(((enachSSO.getSession()!="") || (enachSSO.getSession() != null)) && (enachSSO.getUnauthstatus().equalsIgnoreCase("authorize"))) {
             			
             			cFDEnachSessionCreateForm.setStatus("success");	
             			cFDEnachSessionCreateForm.setSession(enachSSO.getSession().trim());
             			cFDEnachSessionCreateForm.setMobileno(enachSSO.getMobileno().trim());
             			cFDEnachSessionCreateForm.setEmail(enachSSO.getEmailid().trim());
             			cFDEnachSessionCreateForm.setCifid(enachSSO.getCifid().trim());
             			cFDEnachSessionCreateForm.setAccountno(enachSSO.getAccountno().trim());
             			cFDEnachSessionCreateForm.setAccdetcustname(enachSSO.getDetcustomername().trim());
             			cFDEnachSessionCreateForm.setAccdetcustbank(enachSSO.getDetcustomerbank().trim());
             			cFDEnachSessionCreateForm.setAccdetcustifsc(enachSSO.getDetcustomerifsc().trim());
             			cFDEnachSessionCreateForm.setAccdetcustacc(enachSSO.getDetcustomeracc().trim());
             			cFDEnachSessionCreateForm.setStartdate(setDateFormat(enachSSO.getStartdate()));          
             			cFDEnachSessionCreateForm.setEnddate(setDateFormat(enachSSO.getEnddate()));
             			cFDEnachSessionCreateForm.setFrequency(enachSSO.getFrequency().trim());
             			cFDEnachSessionCreateForm.setEmiamount(enachSSO.getEmiamount().trim());
             			cFDEnachSessionCreateForm.setRefno(enachSSO.getRefno()!=null?enachSSO.getRefno():"");
             			cFDEnachSessionCreateForm.setClickedit(enachSSO.getClickedit().equalsIgnoreCase("yes")?"yes":"no");
             			cFDEnachSessionCreateForm.setAppid(enachSSO.getAppid()!=null?enachSSO.getAppid():"notprovided");
             			cFDEnachSessionCreateForm.setRedirectURL(enachSSO.getRedirecturl()!=null?enachSSO.getRedirecturl():"https://www.indusidn.com");
             			if(!cFDEnachSessionCreateForm.getEnddate().equalsIgnoreCase("1900-01-01"))
             				cFDEnachSessionCreateForm.setShowenddate("yes");
             			if(!(enachSSO.getEmiamount().contains(".")))
             					enachSSO.setEmiamount(enachSSO.getEmiamount()+".00");
             			if(!(enachSSO.getAutopayfactor().contains(".")))
         					enachSSO.setAutopayfactor(enachSSO.getAutopayfactor()+".00");
             			double siamount = Double.parseDouble(enachSSO.getEmiamount());
             			double sifactor= Double.parseDouble(enachSSO.getAutopayfactor());     			
             			siamount=siamount*sifactor;
             			if(siamount>=MAX_AMT_LIMIT)
             				siamount=MAX_AMT_LIMIT;	
             			
             			cFDEnachSessionCreateForm.setSmiamount(""+(int)(siamount));
             			
             			LOGGER.info("Values  {} ---- {}  ---- {}", cFDEnachSessionCreateForm.getEnddate(), cFDEnachSessionCreateForm.getSmiamount(), cFDEnachSessionCreateForm.getClickedit());
             		}else
             		{
             			
             			
             			
             			cFDEnachSessionCreateForm.setStatus("unauthorize");	
             		}
             	
             
            	}catch(Exception e)
            	{
            		LOGGER.error("Excetpion", e);
            	}                       		
            }
        }
    }

    /**
     * @return : get cFDPLSessionCreateForm
     */
    public CFDEnachSessionForm getCFDEnachSession() {
        return cFDEnachSessionCreateForm;
    }
    
    /**
     * @return : get jsonmap
     */
    private  EnachSSO  getParamValues(String urlmapper) {
    	EnachSSO jsonObj=new EnachSSO();
    	try {
    		jsonObj = databaseConService.getCFDenachsession(urlmapper, "all");
    		return jsonObj;
        
    	}catch(Exception e)
    	{
    		LOGGER.error("SQL Excetpion", e);
    		return jsonObj;
    	}  

    }
    
    private String setDateFormat(String s)
    {
    	if(s.isEmpty() || s.equalsIgnoreCase(""))
    		s="1900-01-01";
    
    	LOGGER.info("SQL Datae Excetpion {}", s);
    	
    	String finalformat = "";
    	Date date1;
		try {
			date1 = new SimpleDateFormat("yyyy-mm-dd").parse(s);
			SimpleDateFormat dateFor = new SimpleDateFormat("yyyy-mm-dd");
			finalformat= dateFor.format(date1);
	    	
		} catch (ParseException e) {
			LOGGER.error("setDateFormat Excetpion", e);
		}  
    	
    	
    	return finalformat;
    	
    }
    
}
