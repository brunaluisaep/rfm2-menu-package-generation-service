package com.rfm.packagegeneration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.PropertySource;

@PropertySource(value = { "classpath:application.properties"})
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class, RedisAutoConfiguration.class})
public class RfmPackageGenerationPrototypeApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(RfmPackageGenerationPrototypeApplication.class, args);
	}

}
