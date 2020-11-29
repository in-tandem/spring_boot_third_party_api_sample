package com.org.somak.event_api.service;

import java.util.List;

import com.org.somak.event_api.exception.CustomEventException;
import com.org.somak.event_api.pojo.EventDTO;


public interface EventService {

	/***
	 * Method will return a list of events in the format of {@link EventDTO}
	 * for the given location and category
	 * 
	 * <br>
	 * In case downstream resource is not able to return properly method is
	 * expected to raise {@linkplain CustomEventException}
	 * 
	 * @param location
	 * @param category
	 * @return
	 * @throws CustomEventException
	 */
	public List<EventDTO> getEvents(String location, String category) throws CustomEventException;
	
	/**
	 * Method will return a list of events in the format of {@link EventDTO}
	 * for the given location and category and the selected date range
	 * 
	 * <br>
	 * In case downstream resource is not able to return properly method is
	 * expected to raise {@linkplain CustomEventException}
	 * 
	 * @param location
	 * @param category
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws CustomEventException
	 */
	public List<EventDTO> getEvents(String location, String category,String startDate , String endDate) throws CustomEventException;
	
	
	/***
	 * Method is expected to return the list of events for the selected location
	 * and category along with weather information about the venue
	 * 
	 * @param location
	 * @param category
	 * @return
	 */
	public List<EventDTO> getEventsWithWeather(String location, String category);
}
