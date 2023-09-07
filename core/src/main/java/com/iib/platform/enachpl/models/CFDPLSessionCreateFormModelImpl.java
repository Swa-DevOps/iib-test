package com.iib.platform.enachpl.models;

import com.iib.platform.services.DatabaseConnectionService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import java.util.List;

@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CFDPLSessionCreateFormModelImpl{

    private static final Logger LOGGER = LoggerFactory.getLogger(CFDPLSessionCreateFormModelImpl.class);
    private static final int MAX_AMT_LIMIT = 1000000;
    
    @Inject
	DatabaseConnectionService databaseConService;
    
    @Inject
    private SlingHttpServletRequest request;


    CFDPLSessionCreateForm cFDPLSessionCreateForm = new CFDPLSessionCreateForm();
    /**
     *
     */
    @PostConstruct
    public void postConstruct() {
    	LOGGER.info ( "Landed Here {}", request);
    	
    	if(request !=null) {
    		LOGGER.info ( "Landed Here 2  {}",request.getParameter("sso") );
    	if (request.getParameter("sso") != null) {
        		 LOGGER.info ( request.getParameter("sso"));
        		 List<String> fetchresult= null;
        		 fetchresult = getParamValues(request.getParameter("sso"));
        		
        		 	
             	try {
             		
             		if(!fetchresult.isEmpty()) {
             			cFDPLSessionCreateForm.setStatus("success");	
             			cFDPLSessionCreateForm.setSession(fetchresult.get(0));
             			cFDPLSessionCreateForm.setMobileno(fetchresult.get(1));
             			cFDPLSessionCreateForm.setEmail(fetchresult.get(2));
             			cFDPLSessionCreateForm.setCifid(fetchresult.get(3));
             			cFDPLSessionCreateForm.setDealno(fetchresult.get(4));
             			cFDPLSessionCreateForm.setAccdetcustname(fetchresult.get(5));
             			cFDPLSessionCreateForm.setAccdetcustbank(fetchresult.get(6));
             			cFDPLSessionCreateForm.setAccdetcustifsc(fetchresult.get(7));
             			cFDPLSessionCreateForm.setAccdetcustacc(fetchresult.get(8));
             			cFDPLSessionCreateForm.setStartdate(fetchresult.get(9));
             			cFDPLSessionCreateForm.setEnddate(fetchresult.get(10));
             			cFDPLSessionCreateForm.setFrequency(fetchresult.get(11));
             			cFDPLSessionCreateForm.setEmiamount(fetchresult.get(12));
             			cFDPLSessionCreateForm.setTimestamp(fetchresult.get(13));
             			int siamount = Integer.parseInt(fetchresult.get(12));
             			siamount=siamount*2;
             			if(siamount>=MAX_AMT_LIMIT)
             				siamount=MAX_AMT_LIMIT;	
             			cFDPLSessionCreateForm.setSmiamount(""+siamount);
             		}else
             		{
             			cFDPLSessionCreateForm.setStatus("unauthorize");	
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
    public CFDPLSessionCreateForm getCFDPLSessionCreateForm() {
        return cFDPLSessionCreateForm;
    }
    
    /**
     * @return : get jsonmap
     */
    public  List<String>  getParamValues(String urlmapper) {
    	 List<String> jsonObj=null;
    	try {
    		jsonObj = databaseConService.getCFDplsession(urlmapper, "all");
		return jsonObj;
        
    	}catch(Exception e)
    	{
    		LOGGER.error("SQL Excetpion", e);
    		return jsonObj;
    	}  

    }
}
