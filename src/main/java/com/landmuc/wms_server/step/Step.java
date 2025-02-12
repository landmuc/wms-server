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
}
