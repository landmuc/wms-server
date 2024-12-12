package com.landmuc.wms_server.event;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/events") // all request going to /events will be controlled by this controller
public class EventController {
  private final EventService eventService;

  public EventController(EventService eventService) {
    this.eventService = eventService;
  }

  @GetMapping
  public Event getEvent() {
    return eventService.getEvent();
  }

  @GetMapping("/newEntity")
  public EventEntity getEventEntity() {
    EventEntity newEntity = new EventEntity();
    return newEntity;
  }
}
