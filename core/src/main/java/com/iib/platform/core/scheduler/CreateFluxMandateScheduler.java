package com.iib.platform.core.scheduler;

import java.sql.Connection;
import java.util.List;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.commons.datasource.poolservice.DataSourcePool;
import com.iib.platform.services.DatabaseConnectionService;
import com.iib.platform.services.createFluxMandate;

@Component(immediate = true, configurationPid = "com.iib.platform.core.schedulers.CreateFluxMandateScheduler")
@Designate(ocd = CreateFluxMandateScheduler.Configuration.class)
public class CreateFluxMandateScheduler implements Runnable {

	protected final Logger log = LoggerFactory.getLogger(this.getClass());

	@Reference
	private DatabaseConnectionService databaseConService;

	@Reference
	private DataSourcePool source;

	@Reference
	createFluxMandate createfluxmandate;

	Connection connection = null;

	@Override
	public void run() {
		this.createFluxMandate();
	}

	private void createFluxMandate() {
		try {
			/*
			 * connection = getConnection();
			 * 
			 * ResultSet digirefrs = null;
			 * 
			 * // Use prepared statements to protected against SQL injection attacks
			 * PreparedStatement digirefpstmt = null;
			 * 
			 * /*String digirefquery =
			 * "SELECT digireferenceid FROM enachcreatedet WHERE  (fluxstatus=\"'fail\" or fluxstatus=\"FAIL\") and udpateDate = '"
			 * + getDate() + "'";
			 * 
			 * String digirefquery =
			 * "SELECT digireferenceid FROM enachcreatedet WHERE  (fluxstatus=\"fail\" or fluxstatus=\"FAIL\");"
			 * ;
			 * 
			 * digirefpstmt = connection.prepareStatement(digirefquery);
			 * 
			 * log.info("digiref pstmt " + digirefpstmt.toString()); digirefrs =
			 * digirefpstmt.executeQuery();
			 * 
			 * while (digirefrs.next()) { String mandate = digirefrs.getString(1);
			 * 
			 * log.info("digiref pstmt " + mandate);
			 * 
			 * if(mandate.isEqualIgnoreCase("")) {
			 * createfluxmandate.createMandateforFlux(mandate);
			 * 
			 * } } digirefrs.close(); digirefpstmt.close(); connection.close();
			 */

			List<String> mandate = databaseConService.getMandateIdforFailStatus();

			for (String tempmandate : mandate) {
				log.info("value we get " + tempmandate);
				createfluxmandate.createMandateforFlux(tempmandate);
			}

		} catch (Exception e) {
			log.error("Exception occured in createFluxMandateScheduler {}", e.getMessage());
		}

	}

	/*
	 * private String getDate() { String DayBefore =
	 * java.time.LocalDate.now().minusDays(days).toString(); return DayBefore; }
	 * private Connection getConnection() { DataSource dataSource = null;
	 * 
	 * try {
	 * 
	 * log.info("GET CONNECTION");
	 * 
	 * dataSource = (DataSource) source.getDataSource("EnachMandateConnection");
	 * connection = dataSource.getConnection();
	 * 
	 * log.info("CONNECTION is returned");
	 * 
	 * } catch (Exception e) {
	 * log.error("Connection Error in  CreateFluxMandateScheduler {}",
	 * e.getMessage()); } return connection; }
	 */
	private long days;

	@Activate
	protected void activate(Configuration config) {

		days = config.getdays();
	}

	@ObjectClassDefinition(name = "Create Flux Mandate Scheduler Configuration")
	public @interface Configuration {

		@AttributeDefinition(name = "search in datatbase within days", description = "eneter days to be search in database", type = AttributeType.LONG)
		long getdays() default 4;

		@AttributeDefinition(name = "Expression", description = "Cron-job expression. Default: run once in 24 hrs.", type = AttributeType.STRING)
		String scheduler_expression() default "0 0 0/24 1/1 * ? *";

	}
}
