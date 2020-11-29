package com.org.somak.event_api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "service")
@Data
public class ServiceFactoryConfig {

	private String weatherService;
	private String eventService;
	
}
