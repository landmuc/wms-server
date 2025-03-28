package com.landmuc.wms_server.event;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.landmuc.wms_server.domain.exception.EventNotFoundException;

@RestController
@RequestMapping("/events") // all request going to /events will be controlled by this controller
public class EventController {
  private final EventService eventService;

  @Autowired
  private EventController(EventService eventService) {
    this.eventService = eventService;
  }

  @GetMapping("/{eventId}")
  private ResponseEntity<Event> findEventById(@PathVariable UUID eventId) {
    Event event = eventService.findEventById(eventId);

    if (event == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(event);
  }

  @GetMapping
  private ResponseEntity<List<Event>> getAllEvents(Pageable pageable) {
    List<Event> eventList = eventService.getAllEvents(pageable);

    if (eventList.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(eventList);
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

  @DeleteMapping("/{eventId}")
  private ResponseEntity<Void> deleteEvent(@PathVariable UUID eventId, Principal principal) {
    if (!eventService.deleteEventById(eventId, principal)) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{eventId}")
  private ResponseEntity<Void> updateEvent(@PathVariable UUID eventId, @RequestBody Event updatedEvent) {
    EventEntity eventEntity = eventService.updateEvent(updatedEvent);

    if (eventEntity == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.noContent().build();
  }

  @ExceptionHandler(EventNotFoundException.class)
  private ResponseEntity<Map<String, String>> handleEventNotFoundException(EventNotFoundException exception) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", exception.getMessage()));
  }

}
