package com.org.somak.event_api.pojo;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class EventDTO {

	private String id;
	private LocalDateTime startTime;
	private LocalDateTime stopTime;
	private String owner;
	private String url;
	private String description;
	private String title;
	private String geoCodeType;
	private String venueName;
	private LocalDateTime created;
	private String venueUrl;
	private int venueDisplay;
	private ImageDTO image;
	private LocationDTO location;
}
