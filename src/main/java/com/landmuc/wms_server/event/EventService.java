package com.landmuc.wms_server.event;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

// Business logic should return domain objects (DTO), not HTTP responses
// also handles data transformation
@Service
public class EventService {
  private final EventRepository eventRepository;

  @Autowired
  private EventService(EventRepository eventRepository) {
    this.eventRepository = eventRepository;
  }

  public Event findEventById(UUID requestedId) {
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
    return eventRepository.save(event.toEventEntity());
  }

  public boolean deleteEventById(UUID requestedId, Principal principal) {
    if (!eventRepository.existsByIdAndOwnerUsername(requestedId, principal.getName())) {
      return false;
    }
    eventRepository.deleteById(requestedId);
    return true;
  }

  // create new Event and replace new Event with the old one -> same ID
  public EventEntity updateEvent(UUID requestedId, Event updatedEvent) {
    Optional<EventEntity> optionalEventEntity = eventRepository.findById(requestedId);

    if (optionalEventEntity.isEmpty()) {
      return null;
    }

    EventEntity updatedEventEntity = new Event(
        optionalEventEntity.get().getId(),
        optionalEventEntity.get().getOwnerUsername(),
        updatedEvent.title(),
        updatedEvent.description(),
        optionalEventEntity.get().getDateCreated(),
        optionalEventEntity.get().getTimeCreated(),
        updatedEvent.eventDate(),
        updatedEvent.eventTime(),
        updatedEvent.eventEndDate(),
        updatedEvent.eventEndTime(),
        updatedEvent.eventStatus()).toEventEntity();

    return eventRepository.save(updatedEventEntity);
  }

}
