package com.org.somak.event_api.controller;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.org.somak.event_api.config.ServiceFactoryConfig;
import com.org.somak.event_api.exception.CustomEventException;
import com.org.somak.event_api.pojo.EventDTO;
import com.org.somak.event_api.service.AbstractServiceFactory;
import com.org.somak.event_api.service.ServiceFactory;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/rest/event/")
@Validated
public class EventController {

	@Autowired
	private AbstractServiceFactory abstractServiceFactory;
	
	@Autowired
	private ServiceFactoryConfig serviceFactoryConfig;

		
	/***
	 * Rest api to fetch the list of events for the specified location and category
	 * 
	 * @param location
	 * @param category
	 * @return list of events and correlated data
	 * @throws CustomEventException
	 */
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<EventDTO> getEventDetails(
			@NotNull @NotBlank @RequestParam("location") String location,
			@NotNull @NotBlank @RequestParam("category") String category) throws CustomEventException  {

		ServiceFactory serviceFactory = new ServiceFactory(serviceFactoryConfig, abstractServiceFactory);
		
		List<EventDTO> response =  serviceFactory.getEventService().getEvents(location, category);
		
		if(response == null)
			throw new ResponseStatusException( HttpStatus.NOT_FOUND);
		else
			return response;
	}

	
	/***
	 * Rest api to fetch the list of events for the specified location and category
	 * 
	 * @param location
	 * @param category
	 * @return list of events and correlated data
	 * @throws CustomEventException
	 */
	@GetMapping("/date")
	@ResponseStatus(HttpStatus.OK)
	public List<EventDTO> getEventDetailsWithinDate(
			@NotNull @NotBlank @RequestParam("location") String location,
			@NotNull @NotBlank @RequestParam("category") String category,
			@NotNull @NotBlank @Pattern(regexp="^\\d{4}\\d{2}\\d{2}$", message="Should be valid YYYYMMDD format") @RequestParam("startDate") String startDate,
			@NotNull @NotBlank @Pattern(regexp="^\\d{4}\\d{2}\\d{2}$", message="Should be valid YYYYMMDD format") @RequestParam("endDate") String endDate) throws CustomEventException  {

		ServiceFactory serviceFactory = new ServiceFactory(serviceFactoryConfig, abstractServiceFactory);
		
		List<EventDTO> response =  serviceFactory.getEventService().getEvents(location, category,startDate, endDate);
		
		if(response == null)
			throw new ResponseStatusException( HttpStatus.NOT_FOUND);
		else
			return response;
	}

	
	/***
	 * Rest api to fetch the list of events for the specified location and category
	 * 
	 * @param location
	 * @param category
	 * @return list of events and correlated data
	 * @throws CustomEventException
	 */
	@GetMapping(produces="application/event.api-v2+json")
	@ResponseStatus(HttpStatus.OK)
	public List<EventDTO> getEventWithWeatherDetails(
			@NotNull @NotBlank @RequestParam("location") String location,
			@NotNull @NotBlank @RequestParam("category") String category) {

		throw new ResponseStatusException( HttpStatus.HTTP_VERSION_NOT_SUPPORTED);
	}

}
