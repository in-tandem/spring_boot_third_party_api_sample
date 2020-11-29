package com.org.somak.event_api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties("api")
@Data
public class ApiConfig {

	private URLConfig event;
	private boolean ssl=false;
    private int maxTotalNumberOfConnections= 10;
    private int maxNumberOfConnectionsPerHost= 10;
    private int connectionRequestTimeoutInMilliSeconds = 20;
    private String keyStorePath;
    private String keyStorePassword;
}
