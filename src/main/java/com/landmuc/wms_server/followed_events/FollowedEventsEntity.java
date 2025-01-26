package com.landmuc.wms_server.followed_events;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table(name = "followed_events")
@IdClass(FollowedEventsId.class)
public class FollowedEventsEntity {
  // ---------- INSTANCE VARIABLES ----------
  @Id
  @Column(name = "user_id")
  private UUID userId;
  @Id
  @Column(name = "event_id")
  private UUID eventId;

  // ---------- CONSTRUCTORS ----------
  public FollowedEventsEntity() {
  }

  public FollowedEventsEntity(UUID userId, UUID eventId) {
    this.userId = userId;
    this.eventId = eventId;
  }

  // ---------- GETTERS (AND SETTERS) ----------
  public UUID getUserId() {
    return this.userId;
  }

  public UUID getEventId() {
    return this.eventId;
  }
}
