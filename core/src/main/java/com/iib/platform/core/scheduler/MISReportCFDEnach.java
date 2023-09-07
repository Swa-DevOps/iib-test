package com.iib.platform.core.scheduler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.Date;
import java.util.List;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.commons.mail.EmailAttachment;
import org.apache.poi.hssf.record.crypto.Biff8EncryptionKey;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.crypt.HashAlgorithm;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iib.platform.common.objects.EmailVO;
import com.iib.platform.common.objects.EnachSSO;
import com.iib.platform.services.DatabaseConnectionService;
import com.iib.platform.services.EmailService;


/**
 * A simple demo for cron-job like tasks that get executed regularly. It also
 * demonstrates how property values can be set. Users can set the property
 * values in /system/console/configMgr
 */
@Designate(ocd = MISReportCFDEnach.Config.class)
@Component(service = Runnable.class)
public class MISReportCFDEnach implements Runnable {

	@ObjectClassDefinition(name = "CFD Enach Scheduler MIS Report Config")
	public static @interface Config {

		@AttributeDefinition(name = "Cron-job expression")
		String scheduler_expression() default "0 45 23 * * ?";

		@AttributeDefinition(name="Scheduler Frequeny ",  description = "How woudl you like to run scheduler, For Daily Please set the cron expresion with at what time, For Hourly please change accordinly  [ Values should be only Hourly or Daily ] ")
		String schedulerFrequency() default "Daily";
		
		@AttributeDefinition(name="Scheduler Frequeny Fetch Data on  Old Date ", description = "How many old days result will be required no of Days will be provided")
		int schedulerFrequencyForFetchData() default 1;
	
		@AttributeDefinition(name = "Concurrent task", description = "Whether or not to schedule this task concurrently")
		boolean scheduler_concurrent() default false;

		@AttributeDefinition(name = "Scheduler Start Stop", description = "check the box for stop the scheduler functionality")
		boolean schedulerStartStop() default false;

		@AttributeDefinition(name = "CSV/XLSX location", description = "miccfdenach.csv")
		String csvLocation() default "/opt/users-data/microsites/miccfdenach.csv";

		@AttributeDefinition(name = "To Address", description = "reachus@indusind.com")
		String toAddress() default "reachus@indusind.com";

		@AttributeDefinition(name = "From Address", description = "reachus@indusind.com")
		String fromAddress() default "reachus@indusind.com";

		@AttributeDefinition(name = "Email Subject")
		String emailSubject() default "MIS CFD ENACH REPORT";

		@AttributeDefinition(name = "Email template path", description = "it's always be dam / assets path with .txt file")
		String emailTemplatePath() default "/content/dam/indusind/email-template/miscfdFormEmail.txt";
	
		@AttributeDefinition(name = "Provide the XLSX Password", description = "Enter the password to be provied to Protect File")
		String passwordProtect() default "Indusind@123";
	
	}

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Reference
	private DatabaseConnectionService dbService;

	@Reference
	private EmailService emailService;

	private String csvLocation;
	private String toAddress;
	private String fromAddress;
	private String emailSubject;
	private String emailTemplatePath;
	private String frequency;
	private String passowrd;
	private int oldfrequencyData;
	private boolean schedulerStartStop;
	int count = 1;

	
	@Activate
	protected void activate(final Config config) {
		csvLocation = config.csvLocation();
		toAddress = config.toAddress();
		fromAddress = config.fromAddress();
		emailSubject = config.emailSubject();
		emailTemplatePath = config.emailTemplatePath();
		schedulerStartStop = config.schedulerStartStop();
		frequency= config.schedulerFrequency();
		oldfrequencyData=config.schedulerFrequencyForFetchData();
		passowrd = config.passwordProtect();
	}
	
	@Override
	public void run() {
		logger.debug("CMISReportCFDEnachScheduler is now running");
		if (!schedulerStartStop) {
			logger.debug("MISReportCFDEnachScheduler is now started to process");
			this.processMISReportCFDEnachDetails();
		}
		logger.debug("MISReportCFDEnachScheduler is now stopped");
	}

	
	private List<EnachSSO> createSqlQuery()
	{
		String sql="";
		String parameterValue="";
		  int i = oldfrequencyData;
	        Calendar cal = Calendar.getInstance();
	        
	        if (frequency.equalsIgnoreCase("Hourly")) {	
	        	cal.add(Calendar.MINUTE, -i);
	        	Date date = cal.getTime();
	        	DateFormat formatterWithTime = new SimpleDateFormat("yyyy-MM-dd HH:MM");
				java.sql.Date dateParam = new java.sql.Date(date.getTime());
				parameterValue =formatterWithTime.format(dateParam);
	        }
	        
	        if (frequency.equalsIgnoreCase("Daily")) {	
	        	cal.add(Calendar.DATE, -i);
	        	Date date = cal.getTime();
	        	DateFormat formatterWithDate = new SimpleDateFormat("yyyy-MM-dd");
				java.sql.Date dateParam = new java.sql.Date(date.getTime());
				parameterValue =formatterWithDate.format(dateParam);
	        }
			
		   sql = "select * from cfdenachcreatedet where updated_date >='"+parameterValue+"';";
		   
		   logger.debug("sql query we passed {}",sql);
		
		   return dbService.getCFDenachdetailsforExl(sql, parameterValue);
	}
	
	
	private void processMISReportCFDEnachDetails() {
		List<EnachSSO> enachexl = new ArrayList<>();
		
		enachexl = createSqlQuery();
		logger.debug("MISReportCFDEnachScheduler is enter {}", enachexl.size());
		//String sql = createSqlQuery();
		
		//Connection con = null;
		//ResultSet rs=null;
		EmailVO mail = null;
		logger.debug("MISReportCFDEnachScheduler is enter");
		
		
		File file = new File(csvLocation);
		
			if(file.exists()) {
				Boolean status=file.delete();
				logger.error("file delete {}", status);
			}
		
		  try {
			  
			  logger.debug("Enter in 2nd try block");
			  
			if(file.createNewFile()){
				 logger.debug("Enter in 1st if of 2nd try block");
			      try (HSSFWorkbook workbook = new HSSFWorkbook()) {
			          Sheet sheet = workbook.createSheet("Sheet1");
			          Row rowHead = sheet.createRow(0);
			          rowHead.createCell(0).setCellValue("S.No");
			          rowHead.createCell(1).setCellValue("CustomerName");
			          rowHead.createCell(2).setCellValue("CustomerMobile");
			          rowHead.createCell(3).setCellValue("CustomerEmail");
			          rowHead.createCell(4).setCellValue("DealNo");
			          rowHead.createCell(5).setCellValue("CIFID");
			          rowHead.createCell(6).setCellValue("STEP");
			          rowHead.createCell(7).setCellValue("APPID");
			          rowHead.createCell(8).setCellValue("UMRN NO");
			          rowHead.createCell(9).setCellValue("DIGIO REF ID");
			          rowHead.createCell(10).setCellValue("DIGI STATUS");
			          rowHead.createCell(11).setCellValue("FLUX STATUS");            
			          rowHead.createCell(12).setCellValue("FLUX MESSAGE");
			          rowHead.createCell(13).setCellValue("SSO SESSION ID");
			          rowHead.createCell(14).setCellValue("NPCI Reference ID");
			          rowHead.createCell(15).setCellValue("NPCI MESSAGE");
			          
			          rowHead.createCell(16).setCellValue("Destination Bank");
			          rowHead.createCell(17).setCellValue("Destination IFSC");
			          rowHead.createCell(18).setCellValue("Destination AccNo");
			          rowHead.createCell(19).setCellValue("EMI Amount");
			          rowHead.createCell(20).setCellValue("Instructed Amount");
			          rowHead.createCell(21).setCellValue("Frequency");
			          rowHead.createCell(22).setCellValue("start date");
			          rowHead.createCell(23).setCellValue("end date");
			          rowHead.createCell(24).setCellValue("Referral code");
			          rowHead.createCell(25).setCellValue("NPCI TXN REJECT REASON");
			          rowHead.createCell(26).setCellValue("Vendor API Reference");
			          rowHead.createCell(27).setCellValue("AUTH MODE");
			          
			          
			          rowHead.createCell(28).setCellValue("CREATED DATE");
			          rowHead.createCell(29).setCellValue("UPDATED DATE");
			          int lastRowNum = sheet.getLastRowNum();
			          int lastRowCount = ++lastRowNum;
			          boolean recordEnter = false;	
			          logger.debug("Enter before while 2nd try block");
			          
			         if((enachexl!=null)) {
			          for (int i=0; i<enachexl.size();i++ ) {
			        	  recordEnter=true;  
			        	  
			          Row row = sheet.createRow((short) lastRowCount); 
			          row.createCell(0).setCellValue(lastRowCount);
			          row.createCell(1).setCellValue(enachexl.get(i).getDetcustomername()  != null ? enachexl.get(i).getDetcustomername().toUpperCase(): "");
			          row.createCell(2).setCellValue(enachexl.get(i).getMobileno()   != null ? enachexl.get(i).getMobileno()   : "");
			          row.createCell(3).setCellValue(enachexl.get(i).getEmailid()  != null ? enachexl.get(i).getEmailid()  : "");
			          row.createCell(4).setCellValue(enachexl.get(i).getAccountno()  != null ? enachexl.get(i).getAccountno()   : "");
			          row.createCell(5).setCellValue(enachexl.get(i).getCifid()  != null ? enachexl.get(i).getCifid() : "");
			          row.createCell(6).setCellValue(enachexl.get(i).getStep() != null ? repalceStepValues(enachexl.get(i).getStep())  : "");
			          row.createCell(7).setCellValue(enachexl.get(i).getAppid()  != null ? enachexl.get(i).getAppid() : "");
			          row.createCell(8).setCellValue(enachexl.get(i).getUrmn()  != null ? enachexl.get(i).getUrmn()   : "");
			          row.createCell(9).setCellValue(enachexl.get(i).getDigioid()  != null ? enachexl.get(i).getDigioid() : "");
			          row.createCell(10).setCellValue(enachexl.get(i).getEnachstatus() != null ? enachexl.get(i).getEnachstatus()  : "");
			          row.createCell(11).setCellValue(enachexl.get(i).getFluxstatus() != null ? enachexl.get(i).getFluxstatus()  : "");
			          row.createCell(12).setCellValue(enachexl.get(i).getFluxmessage() != null ? enachexl.get(i).getFluxmessage()  : "");
			          row.createCell(13).setCellValue(enachexl.get(i).getSession()  != null ? enachexl.get(i).getSession() : "");
			          row.createCell(14).setCellValue(enachexl.get(i).getNpcitxid()  != null ? enachexl.get(i).getNpcitxid() : "");
			          row.createCell(15).setCellValue(enachexl.get(i).getNpcistatus()  != null ? enachexl.get(i).getNpcistatus()  : "");
			          row.createCell(16).setCellValue(enachexl.get(i).getDetcustomerbank()  != null ? enachexl.get(i).getDetcustomerbank()  : "");
			          row.createCell(17).setCellValue(enachexl.get(i).getDetcustomerifsc()  != null ? enachexl.get(i).getDetcustomerifsc().toUpperCase(): "");
			          row.createCell(18).setCellValue(enachexl.get(i).getDetcustomeracc()  != null ? enachexl.get(i).getDetcustomeracc()  : "");
			          row.createCell(19).setCellValue(enachexl.get(i).getEmiamount()  != null ? enachexl.get(i).getEmiamount()  : "");
			          row.createCell(20).setCellValue(enachexl.get(i).getSmiamount() != null ? enachexl.get(i).getSmiamount()  : "");
			          row.createCell(21).setCellValue(enachexl.get(i).getFrequency()  != null ? enachexl.get(i).getFrequency()  : "");
			          row.createCell(22).setCellValue(enachexl.get(i).getStartdate() != null ? enachexl.get(i).getStartdate()  : "");
			          row.createCell(23).setCellValue((enachexl.get(i).getEnddate()  != null && enachexl.get(i).getEnddate()  != "1900-01-01")? enachexl.get(i).getEnddate()  : "");
			          row.createCell(24).setCellValue(enachexl.get(i).getRefno()  != null ? enachexl.get(i).getRefno()  : "");
			          
			          row.createCell(25).setCellValue(enachexl.get(i).getNpcirejectreason() != null ? enachexl.get(i).getNpcirejectreason()  : "");
			          row.createCell(26).setCellValue(enachexl.get(i).getVendorAPIRef() != null ? enachexl.get(i).getVendorAPIRef()  : "");
				         
			          row.createCell(27).setCellValue(enachexl.get(i).getAuthmode() != null ? enachexl.get(i).getAuthmode()  : "");
			         
			          row.createCell(28).setCellValue(enachexl.get(i).getCreatedtime()  != null ? enachexl.get(i).getCreatedtime() : "");
			          row.createCell(29).setCellValue(enachexl.get(i).getUpdatedtime()  != null ? enachexl.get(i).getUpdatedtime() : "");
				       
			          lastRowCount=lastRowCount +1;
			          logger.info("Creating new sheet with  record! {}", enachexl.get(i).getDigioid());
			          }
			         }
			         
			          Biff8EncryptionKey.setCurrentUserPassword(passowrd);
			          FileOutputStream fileOuts = new FileOutputStream(file);
			          logger.info("Setting Passowrd with  {} {}",passowrd,Biff8EncryptionKey.getCurrentUserPassword() );
			          workbook.writeProtectWorkbook(Biff8EncryptionKey.getCurrentUserPassword(), "");
			         /* if(!passowrd.equalsIgnoreCase("")) {
			        	  logger.info("Setting Passowrd with  {} {}",passowrd,Biff8EncryptionKey.getCurrentUserPassword() );
			        	  workbook.writeProtectWorkbook(Biff8EncryptionKey.getCurrentUserPassword(), "");
			          }*/
			          workbook.write(fileOuts);
			        
			     
			          	if(!passowrd.equalsIgnoreCase(""))
			          		//workbook.setWorkbookPassword(passowrd, HashAlgorithm.sha256);
			          		
			           logger.info("Creating new sheet with new record!");
			          
			          lastRowNum = sheet.getLastRowNum();
			          
			      	if (recordEnter) {
						mail = new EmailVO();
						mail.setFromAddress(fromAddress);
						mail.setToAddress(toAddress);
						mail.setSubject(emailSubject);

						mail.setBodyMessage("Please find the details in attached xls for MIS CFD Reprorts form Details");
						mail.setTemplatePath(emailTemplatePath);
						logger.info("email trigger update {}", emailTemplatePath);

						mail.setfileEmailName(csvLocation.substring(csvLocation.lastIndexOf('/') + 1, csvLocation.length()));
						mail.setfileEmailPath(csvLocation);
						EmailAttachment ea = new EmailAttachment();
						ea.setName(csvLocation.substring(csvLocation.lastIndexOf('/') + 1, csvLocation.length()));
						ea.setPath(csvLocation);
						ea.setDescription("csv file");
						mail.setEmailAttachment(ea);
						boolean isEmailSent = emailService.sendEmailUsingDayCQ(mail);

						logger.info("Email with attachement Tiggered");
						if (isEmailSent) {
							logger.info("Email with attachement Tiggered Send");
						}else
						{
							logger.info("Email with attachement Tiggered Failed");
						}
					} else {
						logger.info("Email without attachement Tiggered send");
						mail = new EmailVO();
						mail.setFromAddress(fromAddress);
						mail.setToAddress(toAddress);
						mail.setSubject(emailSubject);
						mail.setBodyMessage("Sorry,Records are not available.");
						mail.setTemplatePath(emailTemplatePath);
						logger.info("email trigger update {}", emailTemplatePath);
						emailService.sendSimpleEmailUsingDayCQ(mail);
						logger.info("Email without attachement Tiggered called and failed");
					}
			} catch (IOException e) {
				logger.error("IOException occurred in CelestaFormScheduler 1 {}", e);
			}  catch (Exception e) {
				logger.error("Exception occurred in CelestaFormScheduler 3 {}", e);
			} 
			}
		} catch (Exception e) {
			logger.error("Exception occurred in CelestaFormScheduler 2 {}", e);
		}
	}

	

	private String replaceComma(String value) {
		return value.isEmpty() ? " " : value.replace(",", " ");
	}
	
	private String repalceStepValues(String value) {
		
		/*otpgenerated – OTP Generated
		validOtp – OTP Validated
		invalidOtp – In-Valid OTP Entered
		WithDealID – Entered Deal Number
		Cancelled – User cancelled on Indigo Page
		Digioclicked – User redirected to Digio Page
		duringdigoIssue – Issue in Digio Journey
		digiojourneycomplete – Completed Digio Journey*/
			switch(value) {
				case "otpgenerated": return "OTP Generated";
				case "validOtp": return "OTP Validated";	
				case "invalidOtp": return "In-Valid OTP Entered";	
				case "WithDealID": return "Entered Deal Number";	
				case "Cancelled": return "User cancelled on Indigo Page";	
				case "Digioclicked": return "User redirected to Digio Page";	
				case "duringdigoIssue": return "Issue in Digio Journey";	
				case "digiojourneycomplete": return "Completed Digio Journey";	
				default:return "";
					
			}
		
	}
	
}