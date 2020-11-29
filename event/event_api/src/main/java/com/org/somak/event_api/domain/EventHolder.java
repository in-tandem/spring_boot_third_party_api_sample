package com.org.somak.event_api.domain;

import java.util.List;

import lombok.Data;

@Data
public class EventHolder {

	private List<EventDomain> event;
}
