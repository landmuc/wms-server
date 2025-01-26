package com.landmuc.wms_server.followed_events;

import java.util.UUID;

public record FollowedEvents(
    UUID userId,
    UUID eventId) {
}
