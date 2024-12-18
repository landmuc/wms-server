package com.landmuc.wms_server.event;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/events") // all request going to /events will be controlled by this controller
public class EventController {
  private final EventService eventService;

  @Autowired
  private EventController(EventService eventService) {
    this.eventService = eventService;
  }

  @GetMapping("/{requestedId}")
  private ResponseEntity<Event> getEventById(@PathVariable Long requestedId) {
    try {
      Event event = eventService.getEventById(requestedId);
      return ResponseEntity.ok(event);
    } catch (EntityNotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping
  private ResponseEntity<Void> createEvent(@RequestBody Event newEventRequest, UriComponentsBuilder ucb) {
    EventEntity savedEventEntity = eventService.createEvent(newEventRequest);
    URI locationOfNewEvent = ucb
        .path("/events/{id}")
        .buildAndExpand(savedEventEntity.getId())
        .toUri();

    return ResponseEntity.created(locationOfNewEvent).build();
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
