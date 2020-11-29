package com.org.somak.event_api.domain;

import lombok.Data;

@Data
public class Image {
	
	private String id;
	private String caption;
	private String creator;
	private String source;
	private String url;
	private int width;
	private int height;
	private ImageItem small;
	private ImageItem medium;
	private ImageItem thumb;
}