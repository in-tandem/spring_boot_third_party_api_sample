package com.org.somak.event_api.domain;

import lombok.Data;

@Data
public class EventGenericResponse {

	private int last_item;
	private int total_items;
	private int first_item;
	private int page_number;
	private int page_size;
	private int page_items;
	private double search_time;
	private int page_count;
	
	private EventHolder events;


	}







