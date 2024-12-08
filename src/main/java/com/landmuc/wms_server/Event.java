package com.landmuc.wms_server;

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
}
