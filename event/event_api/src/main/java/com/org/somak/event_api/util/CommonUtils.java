package com.org.somak.event_api.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.org.somak.event_api.domain.EventDomain;
import com.org.somak.event_api.domain.EventGenericResponse;
import com.org.somak.event_api.domain.Image;
import com.org.somak.event_api.domain.ImageItem;
import com.org.somak.event_api.exception.CustomEventException;
import com.org.somak.event_api.exception.ExceptionBean;
import com.org.somak.event_api.pojo.EventDTO;
import com.org.somak.event_api.pojo.ImageDTO;
import com.org.somak.event_api.pojo.LocationDTO;
import com.org.somak.event_api.pojo.OrdinatesDTO;

public class CommonUtils {

	private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	/***
	 * 
	 * Returns a standard httpheader value
	 * @return
	 */
	public static HttpEntity<?> getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		return new HttpEntity<>(headers);
	}

	/***
	 * Converter for generating pojo out of downstream response
	 * 
	 * @param response
	 * @return
	 */
	public static List<EventDTO> getEvent(EventGenericResponse response) {

		List<EventDTO> eventDTO = null;
		
		if (response != null && response.getEvents()!=null && response.getEvents().getEvent()!=null  && !response.getEvents().getEvent().isEmpty()) {
			
			eventDTO = response
							.getEvents()
								.getEvent()
									.stream()
									.map(CommonUtils::getEvent)
									.collect(Collectors.toList());
		}
		
		return eventDTO;

	}

	/**
	 * Method to get /set values from domain to dto object using builder pattern
	 * 
	 * @param eventDomain
	 * @return
	 */
	public static EventDTO getEvent(EventDomain eventDomain) {
		
		EventDTO event = EventDTO
							.builder()
							.id(eventDomain.getId())
							.location(getLocation(eventDomain))							
							.image(eventDomain.getImage()!=null?getImage(eventDomain.getImage()):null)
							.owner(eventDomain.getOwner())
							.title(eventDomain.getTitle())
							.startTime(eventDomain.getStart_time()!=null?LocalDateTime.parse(eventDomain.getStart_time(), dateFormatter):null)
							.created(eventDomain.getCreated()!=null?LocalDateTime.parse(eventDomain.getCreated(), dateFormatter):null)
							.stopTime(eventDomain.getStop_time()!=null?LocalDateTime.parse(eventDomain.getStop_time(), dateFormatter):null)
							.venueDisplay(eventDomain.getVenue_display())
							.venueName(eventDomain.getVenue_name())
							.venueUrl(eventDomain.getVenue_url())
							.build();
		return event;
	}
	/***
	 * Method to get /set values from domain to dto object using builder pattern
	 * 
	 * @param event
	 * @return
	 */
	public static LocationDTO getLocation(EventDomain event) {
		
		LocationDTO locationDTO = LocationDTO
									.builder()
									.cityName(event.getCity_name())
									.countryAbbreviation(event.getCountry_abbr())
									.countryAbbreviation2(event.getCountry_abbr2())
									.countryName(event.getCountry_name())
									.latitude(event.getLatitude())
									.longitude(event.getLongitude())
									.regionName(event.getRegion_name())
									.build();
		return locationDTO;
	}
	
	/**
	 * Method to get /set values from domain to dto object using builder pattern
	 * 
	 * @param image
	 * @return
	 */
	public static ImageDTO getImage(Image image) {
		
		Objects.requireNonNull(image);
		
		ImageDTO dto = ImageDTO
						.builder()
						.height(image.getHeight())
						.width(image.getWidth())
						.caption(image.getCaption())
						.small(CommonUtils.getOrdinates(image.getSmall()))
						.medium(CommonUtils.getOrdinates(image.getMedium()))
						.thumbNail(CommonUtils.getOrdinates(image.getThumb()))
						.build();
		return dto;
		
		
	}

	/***
	 * Method to get /set values from domain to dto object using builder pattern
	 * 
	 * @param item
	 * @return
	 */
	public static OrdinatesDTO getOrdinates(ImageItem item) {
		
		Objects.requireNonNull(item);
		return OrdinatesDTO.builder().height(item.getHeight()).width(item.getWidth()).url(item.getUrl()).build();		
		
	}

	/***
	 * Utility method for mapping given start date and enddate ranges to the format
	 * startdate00-enddate00.
	 * <br>
	 * in case dates do not adhere to YYYYMMDD format a {@link CustomEventException} would be raised
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws CustomEventException
	 */
	public static String formatDateRange(String startDate, String endDate) throws CustomEventException {

	    try {
	    	Objects.requireNonNull(startDate);
	    	Objects.requireNonNull(endDate);
			new SimpleDateFormat("yyyyMMdd").parse(startDate);
			new SimpleDateFormat("yyyyMMdd").parse(endDate);
		} catch (ParseException e) {
			throw new CustomEventException(new ExceptionBean("EVENT_002","Start /End Date parameters should be YYYYMMDD format "));
		}
		
		return startDate.concat("00-").concat(endDate).concat("00");
	}
}
