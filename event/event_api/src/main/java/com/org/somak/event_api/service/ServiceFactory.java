package com.org.somak.event_api.service;

import com.org.somak.event_api.config.ServiceFactoryConfig;


public class ServiceFactory {

	
	private ServiceFactoryConfig serviceFactoryConfig;	
	
	private AbstractServiceFactory serviceFactory;
	
	public ServiceFactory(ServiceFactoryConfig serviceFactoryConfig, AbstractServiceFactory abstractServiceFactory) {
		this.serviceFactory = abstractServiceFactory;
		this.serviceFactoryConfig = serviceFactoryConfig;
	}
	
	
	public EventService getEventService() {
		
		return serviceFactory.getEventService(serviceFactoryConfig.getEventService());
	}

	
	public WeatherService getWeatherService() {
		
		return serviceFactory.getWeatherService(serviceFactoryConfig.getWeatherService());
	}
	
}
