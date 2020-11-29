package com.org.somak.event_api.pojo;

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
public class LocationDTO {

	private String cityName;
	private String countryName;
	private String countryAbbreviation;
	private String countryAbbreviation2;
	private String regionName;
	private double longitude;
	private double latitude;
	private String tzOlsonPath;
	
}
