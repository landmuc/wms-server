package com.landmuc.wms_server.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {
  private final EventRepository eventRepository;

  @Autowired
  private EventService(EventRepository eventRepository) {
    this.eventRepository = eventRepository;
  }

  public Event getEvent() {
    Event event = new Event(123L, "Title", "Description");
    return event;
  }
}
