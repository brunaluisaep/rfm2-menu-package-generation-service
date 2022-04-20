package com.rfm.packagegeneration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rfm.packagegeneration.health.CustomHealthCheckIndicator;

@RestController
@RequestMapping()
public class HealthCheckController {
	@Autowired
	CustomHealthCheckIndicator customHealthCheckIndicator;
	
	
	@GetMapping("/health")
	public Health checkingHealthStatus() {
		return customHealthCheckIndicator.health();

	}

}
