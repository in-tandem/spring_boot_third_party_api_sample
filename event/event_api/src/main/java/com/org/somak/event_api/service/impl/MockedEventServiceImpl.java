package com.org.somak.event_api.service.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.org.somak.event_api.exception.CustomEventException;
import com.org.somak.event_api.pojo.EventDTO;
import com.org.somak.event_api.service.EventService;

@Component("mocked-eventful")
public class MockedEventServiceImpl  implements EventService{

	@Override
	public List<EventDTO> getEvents(String location, String category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EventDTO> getEventsWithWeather(String location, String category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EventDTO> getEvents(String location, String category, String startDate, String endDate)
			throws CustomEventException {
		// TODO Auto-generated method stub
		return null;
	}

}
