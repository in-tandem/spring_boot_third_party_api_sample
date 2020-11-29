package com.org.somak.event_api.service;

import com.org.somak.event_api.pojo.WeatherDTO;

public interface WeatherService {

	/**
	 * For the given date/ latitute and longitude expected to return the
	 * forecast
	 * @param latitude
	 * @param longitude
	 * @param date
	 * @return
	 */
	public WeatherDTO getWeather(String latitude, String longitude , String date);
}
