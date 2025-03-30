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
  @Value("${exception.mismatchedIds}")
  private String exceptionMismatchedIds;

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
        .toList(); // returns an unmodifiable list

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

  public EventEntity updateEvent(UUID eventId, Event updatedEvent) {
    // checks if the step which you want to update exists
    eventRepository.findById(eventId)
        .orElseThrow(() -> new EventNotFoundException(exceptionEvent + eventId));

    // checks if the id provided in the URI (stepId) and the id of the updated step
    // you provided are actually the same
    if (!eventId.equals(updatedEvent.id())) {
      throw new EventNotFoundException(String.format(exceptionMismatchedIds, eventId, updatedEvent.id()));
    }

    // saves and returns the updated StepEntity
    return eventRepository.save(updatedEvent.toEventEntity());
  }

}
