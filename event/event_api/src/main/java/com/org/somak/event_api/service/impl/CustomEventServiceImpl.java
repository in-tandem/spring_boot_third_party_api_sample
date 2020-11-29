package com.org.somak.event_api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.org.somak.event_api.config.ApiConfig;
import com.org.somak.event_api.domain.EventGenericResponse;
import com.org.somak.event_api.exception.CustomEventException;
import com.org.somak.event_api.exception.ExceptionBean;
import com.org.somak.event_api.pojo.EventDTO;
import com.org.somak.event_api.service.EventService;
import com.org.somak.event_api.util.CommonUtils;

@Component("eventful")
public class CustomEventServiceImpl implements EventService{


	@Autowired
	@Qualifier("endPointService")
	private RestTemplate endPointService;

	@Autowired
	private ApiConfig apiConfig;
	
	@Override
	public List<EventDTO> getEvents(String location, String category) throws CustomEventException {
		
		UriComponentsBuilder builder = buildUrlComponent(location, category);
		
		ResponseEntity<EventGenericResponse> event = endPointService
															.exchange(
															        builder.toUriString(), 
															        HttpMethod.GET, 
															        CommonUtils.getHeaders(), 
															        EventGenericResponse.class
															        );

		if(event!=null && !event.getStatusCode().equals(HttpStatus.OK))
			throw new CustomEventException(new ExceptionBean("EVENT_001","Something went wrong with downstream "));
		
		return CommonUtils.getEvent(event.getBody());
		
	}

	@Override
	public List<EventDTO> getEventsWithWeather(String location, String category) {
		
		throw new UnsupportedOperationException();
	}

	@Override
	public List<EventDTO> getEvents(String location, String category, String startDate, String endDate)
			throws CustomEventException {

		UriComponentsBuilder builder = buildUrlComponent(location, category);
		
		builder.queryParam("date", CommonUtils.formatDateRange(startDate, endDate));
		
		ResponseEntity<EventGenericResponse> event = endPointService
															.exchange(
															        builder.toUriString(), 
															        HttpMethod.GET, 
															        CommonUtils.getHeaders(), 
															        EventGenericResponse.class
															        );

		if(event!=null && !event.getStatusCode().equals(HttpStatus.OK))
			throw new CustomEventException(new ExceptionBean("EVENT_001","Something went wrong with downstream "));
		
		return CommonUtils.getEvent(event.getBody());

		
	}
	
	private UriComponentsBuilder buildUrlComponent(String location, String category) {
		
		return UriComponentsBuilder
					.fromHttpUrl(apiConfig.getEvent().getEndpoint())
			        .queryParam("l", location)
			        .queryParam("category", category)
			        .queryParam("sort_order", "date")
			        .queryParam("app_key", apiConfig.getEvent().getKey())
			        
			        ;
		
	}

}
