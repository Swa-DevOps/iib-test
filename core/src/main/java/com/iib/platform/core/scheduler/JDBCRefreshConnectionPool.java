package com.iib.platform.core.scheduler;

import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Designate(ocd = JDBCRefreshConnectionPool.Config.class)
@Component(service = Runnable.class)
public class JDBCRefreshConnectionPool implements Runnable {

	@ObjectClassDefinition(name = "Indisnd Custom JDBCRefreshConnectionPool Details Config")
	public static @interface Config {

		@AttributeDefinition(name = "Cron-job expression")
		String scheduler_expression() default "0 0/30 * 1/1 * ? *";

		@AttributeDefinition(name = "Concurrent task", description = "Whether or not to schedule this task concurrently")
		boolean scheduler_concurrent() default false;

		@AttributeDefinition(name = "Scheduler Start Stop", description = "check the box for stop the scheduler functionality")
		boolean schedulerStartStop() default false;

		@AttributeDefinition(name = "Service PID task", description = "JDBC Service PID to reset connect pool size")
		String[] servicePid() default "";

		@AttributeDefinition(name = "Pool Size", description = "JDBC connection pool size")
		long poolSize() default 10L;

	}

	/** Service to get OSGi configurations */
	@Reference
	private ConfigurationAdmin configAdmin;

	private boolean schedulerStartStop;
	private String[] pidArray;
	private long poolSize;
	private static final String POOL_SIZE_PROPERTY = "pool.size";

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void run() {
		logger.debug("JDBCRefreshConnectionPool is now running");
		if (!schedulerStartStop) {
			this.processRefreshDetails();
		} else {
			logger.debug("SchedulerStartStop is set to true so JDBCRefreshConnectionPool process wont run");
		}
	}

	protected void processRefreshDetails() {

		try {
			for (String pid : pidArray) {
				if (configAdmin.getConfiguration(pid) != null) {
					Configuration jdbcManangerConfig = configAdmin.getConfiguration(pid);
					Dictionary<String, Object> properties = jdbcManangerConfig.getProperties();
					if (properties == null) {
						properties = new Hashtable<>();
					}
					long existingPoolSize=(long) properties.get(POOL_SIZE_PROPERTY);
					if(existingPoolSize == 100)
						poolSize = 99;
					else if(existingPoolSize == 99)
						poolSize = 100;
						
					properties.put(POOL_SIZE_PROPERTY, poolSize);
					jdbcManangerConfig.update(properties);
					jdbcManangerConfig.setBundleLocation(null);
				}
			}
		} catch (IOException e) {

			logger.error("JDBCRefreshConnectionPool Scheduler Throws Exception {}", e.getMessage());

		}
	}

	@Activate
	protected void activate(final Config config) {
		schedulerStartStop = config.schedulerStartStop();
		pidArray = config.servicePid();
		poolSize = config.poolSize();
	}
}