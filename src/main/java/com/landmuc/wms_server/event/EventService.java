package com.landmuc.wms_server.event;

import org.springframework.stereotype.Component;

@Component // tells spring to automatically scan for and register this class as a bean
public class EventService {

  public Event getEvent() {
    Event event = new Event(123L, "Title", "Description");
    return event;
  }
}
