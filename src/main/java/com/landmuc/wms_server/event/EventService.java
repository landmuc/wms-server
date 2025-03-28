package com.landmuc.wms_server.event;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.landmuc.wms_server.domain.exception.EventNotFoundException;

@Service
@PropertySource("classpath:messages.properties")
public class EventService {
  @Value("${exception.event}")
  private String exceptionEvent;

  private final EventRepository eventRepository;

  @Autowired
  private EventService(EventRepository eventRepository) {
    this.eventRepository = eventRepository;
  }

  public Event findEventById(UUID eventId) {
    EventEntity eventEntity = eventRepository.findById(eventId)
        .orElseThrow(() -> new EventNotFoundException(exceptionEvent + eventId));

    return eventEntity.toEvent();
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

  public boolean deleteEventById(UUID eventId, Principal principal) {
    if (!eventRepository.existsByIdAndOwnerUsername(eventId, principal.getName())) {
      return false;
    }
    eventRepository.deleteById(eventId);
    return true;
  }

  public EventEntity updateEvent(Event updatedEvent) {
    eventRepository.findById(updatedEvent.getId())
        .orElseThrow(() -> new EventNotFoundException(exceptionEvent + updatedEvent.getId()));

    return eventRepository.save(updatedEvent.toEventEntity());
  }

}
