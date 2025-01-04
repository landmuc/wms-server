package com.landmuc.wms_server.event;

import java.time.LocalDate;
import java.time.LocalTime;

public record Event(
    Long id,
    String ownerUsername,
    String title,
    String description,
    LocalDate dateCreated,
    LocalTime timeCreated,
    LocalDate eventDate,
    LocalTime eventTime,
    LocalDate eventEndDate,
    LocalTime eventEndTime,
    EventStatus eventStatus,
    boolean isFollowed) {

  public Event(
      String ownerUsername,
      String title,
      String description,
      LocalDate eventDate,
      LocalTime eventTime,
      LocalDate eventEndDate,
      LocalTime eventEndTime,
      EventStatus eventStatus,
      boolean isFollowed) {
    this(
        null,
        ownerUsername,
        title,
        description,
        null,
        null,
        eventDate,
        eventTime,
        eventEndDate,
        eventEndTime,
        eventStatus,
        isFollowed);
  }

  // EventEntity Mapper
  public EventEntity toEventEntity() {
    return new EventEntity(
        this.id,
        this.ownerUsername,
        this.title,
        this.description,
        this.dateCreated,
        this.timeCreated,
        this.eventDate,
        this.eventTime,
        this.eventEndDate,
        this.eventEndTime,
        this.eventStatus,
        this.isFollowed);
  }
}
