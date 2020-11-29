package com.org.somak.event_api.pojo;

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
public class ImageDTO {

	private OrdinatesDTO small;
	private OrdinatesDTO medium;
	private OrdinatesDTO thumbNail;
	private String imageType;
	private long width;
	private String caption;
	private long height;
	
}
