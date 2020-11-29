package com.org.somak.event_api.service;

import org.springframework.stereotype.Service;

@Service
public interface AbstractServiceFactory {

	public EventService getEventService(String qualifierName);
	public WeatherService getWeatherService(String qualifierName);
	
}
