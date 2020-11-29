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
public class WeatherDTO {

	private long lowerTemp;
	private long highedTemp;
	private String unit;
	private boolean isRainy;
	private boolean isCloudy;
	private long humidity;
	
}
