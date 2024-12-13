package com.landmuc.wms_server.event;

import java.time.LocalDateTime;

public record Event(
    Long id,
    String title,
    String description
// LocalDateTime dateCreated,
// LocalDateTime timeCreated,
// LocalDateTime eventDate,
// LocalDateTime eventTime,
// LocalDateTime eventEndDate,
// LocalDateTime eventEndTime,
// EventStatus eventStatus,
// boolean isFollowed
) {

  // EventEntity Mapper
  public EventEntity toEventEntity() {
    return new EventEntity(
        this.id,
        this.title,
        this.description);
  }
}
