package com.landmuc.wms_server.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.landmuc.wms_server.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class EventService {
  private final EventRepository eventRepository;

  @Autowired
  private EventService(EventRepository eventRepository) {
    this.eventRepository = eventRepository;
  }

  // Business logic should return domain objects (DTO), not HTTP responses
  // also handles data transformation

  public Event getEventById(Long requestedId) {
    try {
      // getReferenceById() return a reference (proxy) to the entity and only triggers
      // a database query when you actually access the entity's properties
      EventEntity eventEntity = eventRepository.getReferenceById(requestedId);
      eventEntity.getId(); // Force loading to trigger exception if not found
      return eventEntity.toEvent();
    } catch (EntityNotFoundException e) {
      // should not state that the event with the requested id does not exist?!
      throw new ResourceNotFoundException("Event not found with id: " + requestedId); // For security reasonses this
    }

  }

  public Event getEventTest() {
    Event event = new Event(123L, "Title", "Description");
    return event;
  }
}
