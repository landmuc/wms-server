package com.landmuc.wms_server.event;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record Event(
    // ---------- INSTANCE VARIABLES ----------
    UUID id,
    String ownerUsername,
    String title,
    String description,
    LocalDate dateCreated,
    LocalTime timeCreated,
    LocalDate eventDate,
    LocalTime eventTime,
    LocalDate eventEndDate,
    LocalTime eventEndTime,
    EventStatus eventStatus) {

  // ---------- MAPPERS ----------
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
        this.eventStatus);
  }

  // ---------- GETTERS & SETTERS ----------

  public UUID getId() {
    return id;
  }

  public String getOwnerUsername() {
    return ownerUsername;
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  public LocalDate getDateCreated() {
    return dateCreated;
  }

  public LocalTime getTimeCreated() {
    return timeCreated;
  }

  public LocalDate getEventDate() {
    return eventDate;
  }

  public LocalTime getEventTime() {
    return eventTime;
  }

  public LocalDate getEventEndDate() {
    return eventEndDate;
  }

  public LocalTime getEventEndTime() {
    return eventEndTime;
  }

  public EventStatus getEventStatus() {
    return eventStatus;
  }

}
