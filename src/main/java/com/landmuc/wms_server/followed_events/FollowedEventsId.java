package com.landmuc.wms_server.followed_events;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class FollowedEventsId implements Serializable {
  // ---------- INSTANCE VARIABLES ----------
  private UUID userId;
  private UUID eventId;

  // ---------- CONSTRUCTORS ----------
  public FollowedEventsId() {
  }

  public FollowedEventsId(UUID userId, UUID eventId) {
    this.userId = userId;
    this.eventId = eventId;
  }

  // ---------- EQUALS & HASHCODE----------

  @Override
  public boolean equals(Object o) {
    // Check if the object is compared with itself
    if (this == o)
      return true;

    // Check if the object is null or of a different class
    if (o == null || getClass() != o.getClass())
      return false;

    // Cast the object to the specific type
    FollowedEventsId that = (FollowedEventsId) o;

    // Compare the actual values of userId and eventId
    return Objects.equals(userId, that.userId) &&
        Objects.equals(eventId, that.eventId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, eventId);
  }

  // ---------- GETTERS (AND SETTERS) ----------
  public UUID getUserId() {
    return this.userId;
  }

  public UUID getEventId() {
    return this.eventId;
  }
}
