package com.org.somak.event_api.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import com.org.somak.event_api.config.ApiConfig;
import com.org.somak.event_api.config.URLConfig;
import com.org.somak.event_api.domain.EventDomain;
import com.org.somak.event_api.domain.EventGenericResponse;
import com.org.somak.event_api.domain.EventHolder;
import com.org.somak.event_api.exception.CustomEventException;
import com.org.somak.event_api.pojo.EventDTO;

public class CustomEventServiceImplTest {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	
	@Test
	public void test_getEvents() throws CustomEventException {
		
		//given
		String category = "new category";
		String location = "new location";
		
		CustomEventServiceImpl customEventService = new CustomEventServiceImpl();
		RestTemplate restTemplate = spy(RestTemplate.class);
		ApiConfig config = spy(ApiConfig.class);
		URLConfig urlConfig = spy(URLConfig.class);
		ReflectionTestUtils.setField(customEventService, "endPointService", restTemplate);
		ReflectionTestUtils.setField(customEventService, "apiConfig", config);
		EventGenericResponse response = new EventGenericResponse();
		EventHolder eventHolder= new EventHolder();
		EventDomain eventDomain = new EventDomain();
		eventDomain.setId("1st");
		eventHolder.setEvent(Stream.of(eventDomain).collect(Collectors.toList()));
		response.setEvents(eventHolder);

		ResponseEntity<EventGenericResponse> responseEntity = ResponseEntity.ok(response);
		
		
		//when
		doReturn(urlConfig).when(config).getEvent();
		doReturn("http://dummy.url/").when(urlConfig).getEndpoint();
		
		doReturn(responseEntity).when(restTemplate).exchange(eq("http://dummy.url/?l=new%20location&category=new%20category&sort_order=date&app_key"), any(HttpMethod.class), any(),any(Class.class));
		
		List<EventDTO> eventList = customEventService.getEvents(location, category);
		
		
		//then
		assertNotNull(eventList);
		assertEquals(1, eventList.size());
		assertEquals("1st", eventList.get(0).getId());
		
	}

	
	@Test
	public void test_getEvents_notfound() throws CustomEventException {
		
		//given
		String category = "new category";
		String location = "new location";
		
		CustomEventServiceImpl customEventService = new CustomEventServiceImpl();
		RestTemplate restTemplate = spy(RestTemplate.class);
		ApiConfig config = spy(ApiConfig.class);
		URLConfig urlConfig = spy(URLConfig.class);
		ReflectionTestUtils.setField(customEventService, "endPointService", restTemplate);
		ReflectionTestUtils.setField(customEventService, "apiConfig", config);
		EventGenericResponse response = new EventGenericResponse();
		EventHolder eventHolder= new EventHolder();
		EventDomain eventDomain = new EventDomain();
		eventDomain.setId("1st");
		response.setEvents(eventHolder);

		ResponseEntity<EventGenericResponse> responseEntity = ResponseEntity.ok(response);
		
		
		//when
		doReturn(urlConfig).when(config).getEvent();
		doReturn("http://dummy.url/").when(urlConfig).getEndpoint();
		
		doReturn(responseEntity).when(restTemplate).exchange(eq("http://dummy.url/?l=new%20location&category=new%20category&sort_order=date&app_key"), any(HttpMethod.class), any(),any(Class.class));
		
		List<EventDTO> eventList = customEventService.getEvents(location, category);
		
		
		//then
		assertNull(eventList);
		
	}

	
	@Test
	public void test_sad_getEvents() throws CustomEventException {
		
		//given
		String category = "new category";
		String location = "new location";
		
		CustomEventServiceImpl customEventService = new CustomEventServiceImpl();
		RestTemplate restTemplate = spy(RestTemplate.class);
		ApiConfig config = spy(ApiConfig.class);
		URLConfig urlConfig = spy(URLConfig.class);
		ReflectionTestUtils.setField(customEventService, "endPointService", restTemplate);
		ReflectionTestUtils.setField(customEventService, "apiConfig", config);
		EventGenericResponse response = new EventGenericResponse();
		EventHolder eventHolder= new EventHolder();
		EventDomain eventDomain = new EventDomain();
		eventDomain.setId("1st");
		eventHolder.setEvent(Stream.of(eventDomain).collect(Collectors.toList()));
		response.setEvents(eventHolder);

		ResponseEntity<?> responseEntity = ResponseEntity.badRequest().body(eventHolder);
		
		
		//when
		doReturn(urlConfig).when(config).getEvent();
		doReturn("http://dummy.url/").when(urlConfig).getEndpoint();
		
		
		doReturn(responseEntity).when(restTemplate).exchange(eq("http://dummy.url/?l=new%20location&category=new%20category&sort_order=date&app_key"), any(HttpMethod.class), any(),any(Class.class));
		
		expectedException.expect(CustomEventException.class);
		customEventService.getEvents(location, category);
		
	}
	
	
	
	
	@Test
	public void test_getEvents_date() throws CustomEventException {
		
		//given
		String category = "new category";
		String location = "new location";
		String startDate = "19880101";
		String endDate = "19890101";
		
		CustomEventServiceImpl customEventService = new CustomEventServiceImpl();
		RestTemplate restTemplate = spy(RestTemplate.class);
		ApiConfig config = spy(ApiConfig.class);
		URLConfig urlConfig = spy(URLConfig.class);
		ReflectionTestUtils.setField(customEventService, "endPointService", restTemplate);
		ReflectionTestUtils.setField(customEventService, "apiConfig", config);
		EventGenericResponse response = new EventGenericResponse();
		EventHolder eventHolder= new EventHolder();
		EventDomain eventDomain = new EventDomain();
		eventDomain.setId("1st");
		eventHolder.setEvent(Stream.of(eventDomain).collect(Collectors.toList()));
		response.setEvents(eventHolder);

		ResponseEntity<EventGenericResponse> responseEntity = ResponseEntity.ok(response);
		
		
		//when
		doReturn(urlConfig).when(config).getEvent();
		doReturn("http://dummy.url/").when(urlConfig).getEndpoint();
		
		doReturn(responseEntity).when(restTemplate).exchange(eq("http://dummy.url/?l=new%20location&category=new%20category&sort_order=date&app_key&date=1988010100-1989010100"), any(HttpMethod.class), any(),any(Class.class));
		
		List<EventDTO> eventList = customEventService.getEvents(location, category, startDate, endDate);
		
		
		//then
		assertNotNull(eventList);
		assertEquals(1, eventList.size());
		assertEquals("1st", eventList.get(0).getId());
		
	}

	
	@Test
	public void test_getDatedEvents_notfound() throws CustomEventException {
		
		//given
		String category = "new category";
		String location = "new location";
		String startDate = "19880101";
		String endDate = "19890101";
		
		CustomEventServiceImpl customEventService = new CustomEventServiceImpl();
		RestTemplate restTemplate = spy(RestTemplate.class);
		ApiConfig config = spy(ApiConfig.class);
		URLConfig urlConfig = spy(URLConfig.class);
		ReflectionTestUtils.setField(customEventService, "endPointService", restTemplate);
		ReflectionTestUtils.setField(customEventService, "apiConfig", config);
		EventGenericResponse response = new EventGenericResponse();
		EventHolder eventHolder= new EventHolder();
		EventDomain eventDomain = new EventDomain();
		eventDomain.setId("1st");
		response.setEvents(eventHolder);

		ResponseEntity<EventGenericResponse> responseEntity = ResponseEntity.ok(response);
		
		
		//when
		doReturn(urlConfig).when(config).getEvent();
		doReturn("http://dummy.url/").when(urlConfig).getEndpoint();
		
		doReturn(responseEntity).when(restTemplate).exchange(eq("http://dummy.url/?l=new%20location&category=new%20category&sort_order=date&app_key&date=1988010100-1989010100"), any(HttpMethod.class), any(),any(Class.class));
		
		List<EventDTO> eventList = customEventService.getEvents(location, category, startDate, endDate);
		
		
		//then
		assertNull(eventList);
		
	}

	
	@Test
	public void test_sad_getDatedEvents() throws CustomEventException {
		
		//given
		String category = "new category";
		String location = "new location";
		String startDate = "19880101";
		String endDate = "19890101";
		
		CustomEventServiceImpl customEventService = new CustomEventServiceImpl();
		RestTemplate restTemplate = spy(RestTemplate.class);
		ApiConfig config = spy(ApiConfig.class);
		URLConfig urlConfig = spy(URLConfig.class);
		ReflectionTestUtils.setField(customEventService, "endPointService", restTemplate);
		ReflectionTestUtils.setField(customEventService, "apiConfig", config);
		EventGenericResponse response = new EventGenericResponse();
		EventHolder eventHolder= new EventHolder();
		EventDomain eventDomain = new EventDomain();
		eventDomain.setId("1st");
		eventHolder.setEvent(Stream.of(eventDomain).collect(Collectors.toList()));
		response.setEvents(eventHolder);

		ResponseEntity<?> responseEntity = ResponseEntity.badRequest().body(eventHolder);
		
		
		//when
		doReturn(urlConfig).when(config).getEvent();
		doReturn("http://dummy.url/").when(urlConfig).getEndpoint();
		
		
		doReturn(responseEntity).when(restTemplate).exchange(eq("http://dummy.url/?l=new%20location&category=new%20category&sort_order=date&app_key&date=1988010100-1989010100"), any(HttpMethod.class), any(),any(Class.class));
		
		expectedException.expect(CustomEventException.class);
		customEventService.getEvents(location, category,startDate, endDate);
		
	}
	
	@Test
	public void test_getEventsWithWeather() {

		//given
		String category = "new category";
		String location = "new location";
	
		CustomEventServiceImpl customEventService = new CustomEventServiceImpl();
		RestTemplate restTemplate = spy(RestTemplate.class);
		ApiConfig config = spy(ApiConfig.class);
		URLConfig urlConfig = spy(URLConfig.class);
		ReflectionTestUtils.setField(customEventService, "endPointService", restTemplate);
		ReflectionTestUtils.setField(customEventService, "apiConfig", config);
			
		//when		
		expectedException.expect(UnsupportedOperationException.class);
		customEventService.getEventsWithWeather(location, category);
	
	}
}
