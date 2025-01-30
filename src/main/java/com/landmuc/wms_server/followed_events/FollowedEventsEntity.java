package com.landmuc.wms_server.followed_events;

import java.util.Objects;
import java.util.UUID;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.UUIDDeserializer;

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
  @JsonDeserialize(using = UUIDDeserializer.class)
  private UUID userId;
  @Id
  @Column(name = "event_id")
  @JsonDeserialize(using = UUIDDeserializer.class)
  private UUID eventId;

  // ---------- CONSTRUCTORS ----------
  public FollowedEventsEntity() {
  }

  public FollowedEventsEntity(UUID userId, UUID eventId) {
    this.userId = userId;
    this.eventId = eventId;
  }

  // ---------- EQUALS & HASHCODE----------
  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    FollowedEventsEntity that = (FollowedEventsEntity) o;
    return Objects.equals(userId, that.userId) && Objects.equals(eventId, that.eventId);
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
