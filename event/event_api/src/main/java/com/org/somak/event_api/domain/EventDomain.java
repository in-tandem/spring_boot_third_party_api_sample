package com.org.somak.event_api.domain;

import lombok.Data;

@Data
public class EventDomain{
	
	private int watching_count;
	private String olson_path;
	private String calendar_count;
	private int comment_count;
	private String region_abbr;
	private String postal_code;
	private int going_count;
	private int all_day;
	private double latitude;
	private String groups;
	private String url;
	private String id;
	private int privacy;
	private String city_name;
	private int link_count;
	private double longitude;
	private String country_name;
	private String country_abbr;
	private String region_name;
	private String start_time;
	private String tz_id;
	private String description;
	private String modified;
	private int venue_display;
	private String tz_country;
	private PerformerHolder performers;
	private String title;
	private String venue_address;
	private String geocode_type;
	private String tz_olson_path;
	private String recur_string;
	private String calendars;
	private String owner;
	private int going;
	private String country_abbr2;
	private String created;
	private String venue_id;
	private String tz_city;
	private String stop_time;
	private String venue_name;
	private String venue_url;
	private Image image;
}
