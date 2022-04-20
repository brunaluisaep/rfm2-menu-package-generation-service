package com.rfm.packagegeneration.health;

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health.Builder;
import org.springframework.stereotype.Component;

@Component
public class CustomHealthCheckIndicator extends AbstractHealthIndicator {
	private static final Logger LOGGER = LogManager.getLogger("CustomHealthCheckIndicator"); 
	@Override
	protected void doHealthCheck(Builder builder) throws Exception {
		if(LOGGER.isInfoEnabled()) LOGGER.info("RequestId: {}", Math.random());
		if(LOGGER.isInfoEnabled()) LOGGER.info(System.getenv("HOSTNAME"));
		long startTime = System.nanoTime();
		if(LOGGER.isInfoEnabled()) LOGGER.info("HealthCheck received {}", startTime);
		
		builder.up().withDetail("app", "App is Running").withDetail("error", "No error.");
		long endTime = System.nanoTime();
		if(LOGGER.isInfoEnabled()) LOGGER.info("HealthCheck responded {}", endTime);
		long duration = (endTime - startTime);
		
		long convert = TimeUnit.MILLISECONDS.convert(duration, TimeUnit.NANOSECONDS);
		if(LOGGER.isInfoEnabled()) LOGGER.info("HealthCheck duration mileseconds: {}", convert);
		if(LOGGER.isInfoEnabled()) LOGGER.info("HealthCheck duration nanoseconds: {}", duration);
	}
}