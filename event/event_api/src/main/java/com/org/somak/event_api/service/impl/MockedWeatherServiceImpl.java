package com.org.somak.event_api.service.impl;

import org.springframework.stereotype.Component;

import com.org.somak.event_api.pojo.WeatherDTO;
import com.org.somak.event_api.service.WeatherService;

@Component("mocked-weather")
public class MockedWeatherServiceImpl implements WeatherService{

	@Override
	public WeatherDTO getWeather(String latitude, String longitude, String date) {
		throw new UnsupportedOperationException();
	}

}
