package com.landmuc.wms_server.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/events") // all request going to /events will be controlled by this controller
public class EventController {
  private final EventService eventService;

  @Autowired
  public EventController(EventService eventService) {
    this.eventService = eventService;
  }

  @GetMapping("/{requestedId}")
  public ResponseEntity<Event> getEventById(@PathVariable Long requestedId) {
    try {
      Event event = eventService.getEventById(requestedId);
      return ResponseEntity.ok(event);
    } catch (EntityNotFoundException e) {
      return ResponseEntity.notFound().build();
    }

  }

  @GetMapping
  public Event getEventTest() {
    return eventService.getEventTest();
  }

  @GetMapping("/newEntity")
  public EventEntity getEventEntity() {
    EventEntity newEntity = new EventEntity();
    return newEntity;
  }
}
