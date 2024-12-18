package com.landmuc.wms_server.event;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.landmuc.wms_server.domain.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

// Business logic should return domain objects (DTO), not HTTP responses
// also handles data transformation
@Service
public class EventService {
  private final EventRepository eventRepository;

  @Autowired
  private EventService(EventRepository eventRepository) {
    this.eventRepository = eventRepository;
  }

  public Event findEventById(Long requestedId) {
    Optional<EventEntity> optionalEventEntity = eventRepository.findById(requestedId);

    if (optionalEventEntity.isPresent()) {
      return optionalEventEntity.get().toEvent();
    } else {
      return null;
    }
  }

  public EventEntity createEvent(Event event) {
    EventEntity eventEntity = new EventEntity(null, event.title(), event.description());
    return eventRepository.save(eventEntity);
  }

  // --------------------- CURRENTLY NOT USED ---------------------

  public Event getEventById(Long requestedId) {
    try {
      // getReferenceById() return a reference (proxy) to the entity and only triggers
      // a database query when you actually access the entity's properties
      EventEntity eventEntity = eventRepository.getReferenceById(requestedId);
      eventEntity.getId(); // Force loading to trigger exception if not found
      return eventEntity.toEvent();
    } catch (EntityNotFoundException e) {
      // For security reasonses this should not state that the event with the
      // requested id does not exist?!
      throw new ResourceNotFoundException("Event not found with id: " + requestedId);
    }

  }

  public Event getEventTest() {
    Event event = new Event(123L, "Title", "Description");
    return event;
  }
}
