package com.org.somak.event_api.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.containsString;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;

import com.org.somak.event_api.domain.EventDomain;
import com.org.somak.event_api.domain.EventGenericResponse;
import com.org.somak.event_api.domain.EventHolder;
import com.org.somak.event_api.domain.Image;
import com.org.somak.event_api.domain.ImageItem;
import com.org.somak.event_api.exception.CustomEventException;
import com.org.somak.event_api.pojo.EventDTO;
import com.org.somak.event_api.pojo.ImageDTO;
import com.org.somak.event_api.pojo.OrdinatesDTO;

public class CommonUtilsTest {

	@Rule
	public ExpectedException expected = ExpectedException.none();
	
	@Test
	public void test_getHeaders() {
		
		//given && when
		
		HttpEntity<?> entity = CommonUtils.getHeaders();
		
		//then
		assertNotNull(entity);
		assertNotNull(entity.getHeaders());
		assertEquals(MediaType.APPLICATION_JSON,entity.getHeaders().getContentType());
	}
	
	@Test
	public void test_getEvent() {
		
		//given
		EventGenericResponse response = new EventGenericResponse();
		
		EventHolder eventHolder = new EventHolder();
		
		eventHolder.setEvent(
						Stream.of(
						
								getEventDomain(
										"thisissmallImage", 
										"thisismediumimage", 
										"thumbNail", 
										"thisismyimagecaption", 
										"Kanpur", 
										"India", 
										"vaccination", 
										"12v120202-12"),
								
								getEventDomain(
										"thisissmallImage", 
										"thisismediumimage_2", 
										"thumbNail_1", 
										"thisismyimagecaption", 
										"Varanasi", 
										"India", 
										"arti", 
										"12v120202-12"),
								
								getEventDomain(
										"bla bla", 
										"yes bla bla again", 
										"blablbalblaaaa", 
										"thisismyimagecaption", 
										"Agra", 
										"India", 
										"music", 
										"mumtaj123shahja")
								
						)
						.collect(Collectors.toList())
				);
		
		
		response.setEvents(eventHolder);
		
		//when
		
		List<EventDTO> event = CommonUtils.getEvent(response);
		
		//then
		
		assertNotNull(event);
		assertTrue(event.size()>0);
		assertEquals(3, event.size());
		assertNotNull(event.get(0).getLocation());
		assertNotNull(event.get(0).getImage());
		assertNotNull(event.get(1).getLocation());
		assertNotNull(event.get(1).getImage());
		assertNotNull(event.get(2).getLocation());
		assertNotNull(event.get(2).getImage());
		
		//given
		response.setEvents(null);
		
		//when
		event = CommonUtils.getEvent(response);
		
		//then
		assertNull(event);
		
		
		//given
		EventHolder holder1= new EventHolder();
		response.setEvents(holder1);
		
		//when
		event = CommonUtils.getEvent(response);
		
		//then
		assertNull(event);
		
		
		
	}
	
	@Test
	public void test_getEventFromDomain() {
		
		//given
		EventDomain eventDomain = getEventDomain(
				"thisissmallImage", 
				"thisismediumimage", 
				"thumbNail", 
				"thisismyimagecaption", 
				"Kanpur", 
				"India", 
				"vaccination", 
				"12v120202-12");
		
		eventDomain.setStart_time("2021-03-26 15:00:00");
		eventDomain.setStop_time("2021-05-26 15:00:00");
		eventDomain.setVenue_display(2);
		eventDomain.setVenue_url("this is the url for the venue");
		
		
		//when
		
		EventDTO event = CommonUtils.getEvent(eventDomain);
		
		//then
		assertNotNull(event);
		assertNotNull(event.getLocation());
		assertNotNull(event.getImage());
		assertEquals(2021, event.getStartTime().getYear());
		assertEquals(3, event.getStartTime().getMonthValue());
		assertEquals(26, event.getStartTime().getDayOfMonth());
		assertNull(event.getCreated());
		assertEquals("this is the url for the venue", event.getVenueUrl());
		assertEquals(2, event.getVenueDisplay());
		assertEquals("Kanpur", event.getLocation().getCityName());
		assertEquals("India", event.getLocation().getCountryName());
		assertEquals("12v120202-12",event.getId());
	}

	
	@Test
	public void test_getLocation() {
		
		//given
		EventDomain eventDomain = getEventDomain(
				"thisissmallImage", 
				"thisismediumimage", 
				"thumbNail", 
				"thisismyimagecaption", 
				"Kanpur", 
				"India", 
				"vaccination", 
				"12v120202-12");
		
		eventDomain.setCountry_abbr("IND");
		eventDomain.setCountry_abbr("ind");
		eventDomain.setLatitude(256.2);
		eventDomain.setLongitude(2344);
		eventDomain.setRegion_name("uttar pradesh");
		
		
		//when
		
		EventDTO event = CommonUtils.getEvent(eventDomain);
		
		//then
		assertNotNull(event);
		assertNotNull(event.getLocation());
		assertEquals("Kanpur", event.getLocation().getCityName());
		assertEquals("uttar pradesh", event.getLocation().getRegionName());
		assertEquals("India", event.getLocation().getCountryName());
		assertEquals("12v120202-12",event.getId());
		assertTrue(256.2 == event.getLocation().getLatitude());
		assertTrue(2344 == event.getLocation().getLongitude());
	}
	
	@Test
	public void test_Image() {
		
		//given
		Image image = new Image();
		image.setMedium(getImageItem("this is medium image"));
		image.setSmall(getImageItem("this is small image"));
		image.setThumb(getImageItem("this is image"));
		image.setHeight(23);
		image.setId("new image");
		
		//when
		
		ImageDTO imageDTO = CommonUtils.getImage(image);
		
		//then
		
		assertNotNull(imageDTO);
		assertNotNull(imageDTO.getMedium());
		assertThat( image.getMedium().getUrl(),containsString("this is medium"));
		assertEquals(23,  image.getHeight());
		
		
		//given
		image = null;
		
		//when
		expected.expect(NullPointerException.class);		
		CommonUtils.getImage(image);
		
		
		//given
		
		image.setMedium(null);
		
		//when
		expected.expect(NullPointerException.class);
		CommonUtils.getImage(image);
		
	}
	
	@Test
	public void test_getordinates() {
		
		//given
		
		ImageItem item = getImageItem("this is new url");
		
		//when
		
		OrdinatesDTO ordinates = CommonUtils.getOrdinates(item);
		
		//then
		
		assertNotNull(ordinates);
		assertEquals(23, ordinates.getHeight());
		assertThat( ordinates.getUrl(),containsString("this is new"));
		
		
		//given
		item = null;
		
		expected.expect(NullPointerException.class);
		CommonUtils.getOrdinates(item);
		
		
	}
	
	@Test
	public void test_formatDateRange() throws CustomEventException {
		
		//given
		String startDate = "20201001";
		String endDate = "20201012";
		
		//when
		
		String response = CommonUtils.formatDateRange(startDate, endDate);
		
		//then
		assertNotNull(response);
		assertEquals("2020100100-2020101200", response);
		
		
		//given
		startDate = "abc";
		endDate = "20200211";
		
		//when
		
		expected.expect(CustomEventException.class);
		CommonUtils.formatDateRange(startDate, endDate);
		
		
	}
	
	private EventDomain getEventDomain(
					String smallImageUrl, 
					String mediumUrl, 
					String thumbUrl,
					String caption,
					String cityName,
					String countryName,
					String venueName,
					String eventId) {
		
		EventDomain event = new EventDomain();
		
		event.setCity_name(cityName);
		event.setCountry_name(countryName);
		event.setId(eventId);
		event.setVenue_name(venueName);
		Image image = new Image();
		ImageItem imageItem = getImageItem(smallImageUrl);
		ImageItem mediaumImageItem = getImageItem(mediumUrl);
		ImageItem thumbImageItem = getImageItem(thumbUrl);
		image.setMedium(mediaumImageItem);
		image.setSmall(imageItem);
		image.setThumb(thumbImageItem);
		image.setCaption(caption);
		image.setCreator("icreatedthis");
		image.setId("3u33xes");
		image.setSource("godgiven");
		
		event.setImage(image);
		
		return event;
	}
	
	private ImageItem getImageItem(String url) {
		
		
		ImageItem item = new ImageItem();
		item.setHeight(23);
		item.setUrl(url);
		item.setWidth(55);
		return item;
	}
}
