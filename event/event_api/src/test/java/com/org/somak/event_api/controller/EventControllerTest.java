package com.org.somak.event_api.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;

import com.org.somak.event_api.config.ServiceFactoryConfig;
import com.org.somak.event_api.pojo.EventDTO;
import com.org.somak.event_api.service.AbstractServiceFactory;
import com.org.somak.event_api.service.EventService;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = EventController.class)
public class EventControllerTest {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Autowired
    private MockMvc mvc;
	
	@MockBean
    private AbstractServiceFactory factory;
	
	@MockBean
    private ServiceFactoryConfig config;
	
	@Test
	public void test_getEvents()
	  throws Exception {
	    
	 
		List<EventDTO> response = new ArrayList<EventDTO>();
		response.add(EventDTO.builder().build());
		EventService eventService = spy(EventService.class);
		
		when(config.getEventService()).thenReturn("somevalue");
		when(factory.getEventService(any(String.class))).thenReturn(eventService);
		when(eventService.getEvents(any(String.class), any(String.class))).thenReturn(response);
		
	    mvc.perform(
	    			get("/rest/event/?location=london&category=music")
	    			)
	      			.andExpect(status().isOk());
	    
	}
	
	@Test
	public void test_getEvents_notfound()
	  throws Exception {
	    
	 
		EventService eventService = spy(EventService.class);
		
		when(config.getEventService()).thenReturn("somevalue");
		when(factory.getEventService(any(String.class))).thenReturn(eventService);
		when(eventService.getEvents(any(String.class), any(String.class))).thenReturn(null);
		
	    mvc.perform(
	    			get("/rest/event/?location=london&category=music")
	    			)
	      			.andExpect(status().isNotFound());
	    
	}
	
	@Test
	public void test_getEvents_bad_request()
	  throws Exception {
	    
	 
		EventService eventService = spy(EventService.class);
		
		when(config.getEventService()).thenReturn("somevalue");
		when(factory.getEventService(any(String.class))).thenReturn(eventService);
		when(eventService.getEvents(any(String.class), any(String.class))).thenReturn(null);
		
		expectedException.expect(NestedServletException.class);
		mvc.perform(get("/rest/event/?location= &category=music"));
		expectedException.expect(NestedServletException.class);
		mvc.perform(get("/rest/event/?location=l&category="));
	    

	}
	

	@Test
	public void test_getEvents_second_version()
	  throws Exception {
	    
	 
		EventService eventService = spy(EventService.class);
		
		when(config.getEventService()).thenReturn("somevalue");
		when(factory.getEventService(any(String.class))).thenReturn(eventService);
		when(eventService.getEvents(any(String.class), any(String.class))).thenReturn(null);
		
		mvc.perform(get("/rest/event/?location=london&category=music")
						.accept("application/event.api-v2+json"))
				.andExpect(status().is5xxServerError());

	}
	
	@Test
	public void test_getEventsDated()
	  throws Exception {
	    

		List<EventDTO> response = new ArrayList<EventDTO>();
		response.add(EventDTO.builder().build());
		EventService eventService = spy(EventService.class);
		
		when(config.getEventService()).thenReturn("somevalue");
		when(factory.getEventService(any(String.class))).thenReturn(eventService);
		when(eventService.getEvents(any(String.class), any(String.class))).thenReturn(response);
		
	    
	    mvc.perform(
    			get("/rest/event/date?location=l&category=c&startDate=20200111&endDate=19800901")
    			)
      			.andExpect(status().isOk());
	}
	

	
	@Test
	public void test_getEventsDated_bad_request()
	  throws Exception {
	    

		List<EventDTO> response = new ArrayList<EventDTO>();
		response.add(EventDTO.builder().build());
		EventService eventService = spy(EventService.class);
		
		when(config.getEventService()).thenReturn("somevalue");
		when(factory.getEventService(any(String.class))).thenReturn(eventService);
		when(eventService.getEvents(any(String.class), any(String.class))).thenReturn(response);
		
		expectedException.expect(NestedServletException.class);
	    mvc.perform(get("/rest/event/date?location=l&category=c&startDate=abgs&endDate=19800901"));
      			
	}

}
