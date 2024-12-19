package com.landmuc.wms_server.event;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

  public List<Event> getAllEvents(Pageable pageable) {
    Page<EventEntity> page = eventRepository.findAll(PageRequest.of(
        pageable.getPageNumber(),
        pageable.getPageSize(),
        pageable.getSortOr(Sort.by(Sort.Direction.ASC, "title"))));

    List<Event> eventList = page.getContent() // getContent() returns an empty list if no entities are found
        .stream()
        .map(EventEntity::toEvent)
        .toList(); // returns a unmodifiable list

    return eventList;
  }

  public EventEntity createEvent(Event event) {
    EventEntity eventEntity = new EventEntity(null, event.title(), event.description());
    return eventRepository.save(eventEntity);
  }

  public boolean deleteEventById(Long requestedId) {
    if (!eventRepository.existsById(requestedId)) {
      return false;
    }
    eventRepository.deleteById(requestedId);
    return true;
  }

  // create new Event and replace new Event with the old one -> same ID
  public EventEntity updateEvent(Long requestedId, Event updatedEvent) {
    Optional<EventEntity> optionalEventEntity = eventRepository.findById(requestedId);

    if (optionalEventEntity.isEmpty()) {
      return null;
    }

    EventEntity updatedEventEntity = new Event(
        optionalEventEntity.get().getId(),
        updatedEvent.title(),
        updatedEvent.description()).toEventEntity();

    return eventRepository.save(updatedEventEntity);
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

}
