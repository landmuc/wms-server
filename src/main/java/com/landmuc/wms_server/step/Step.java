package com.landmuc.wms_server.step;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record Step(
    UUID id,
    String title,
    String description,
    LocalDate dateCreated,
    LocalTime timeCreated,
    LocalDate stepDate,
    LocalTime stepTime,
    LocalDate stepEndDate,
    LocalTime stepEndTime,
    UUID eventId) {
  // ---------- MAPPERS ----------
  StepEntity toStepEntity() {
    return new StepEntity(
        this.id,
        this.title,
        this.description,
        this.dateCreated,
        this.timeCreated,
        this.stepDate,
        this.stepTime,
        this.stepEndDate,
        this.stepEndTime,
        this.eventId);
  }

  // ---------- GETTERS & SETTERS ----------

  public UUID getId() {
    return id;
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

  public LocalDate getStepDate() {
    return stepDate;
  }

  public LocalTime getStepTime() {
    return stepTime;
  }

  public LocalDate getStepEndDate() {
    return stepEndDate;
  }

  public LocalTime getStepEndTime() {
    return stepEndTime;
  }

  public UUID getEventId() {
    return eventId;
  }

}
