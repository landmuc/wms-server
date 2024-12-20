package com.landmuc.wms_server.event;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// using class instead of record because database configurations (mostly) need an empty constructor which record does not provide
@Entity
@Table(name = "events")
public class EventEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) // IDENTITY: relies on the database to generate unique Ids.
                                                      // Commonly used with dbs like MySQL and PostgreSQL
  private Long id;
  private String title;
  private String description;
  private LocalDate dateCreated;
  private LocalTime timeCreated;
  private LocalDate eventDate;
  private LocalTime eventTime;
  private LocalDate eventEndDate;
  private LocalTime eventEndTime;
  private EventStatus eventStatus;
  private boolean isFollowed;

  public EventEntity() {
  }

  public EventEntity(
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
    this.title = title;
    this.description = description;
    this.dateCreated = dateCreated;
    this.timeCreated = timeCreated;
    this.eventDate = eventDate;
    this.eventTime = eventTime;
    this.eventEndDate = eventEndDate;
    this.eventEndTime = eventEndTime;
    this.eventStatus = eventStatus;
    this.isFollowed = isFollowed;
  }

  public EventEntity(
      Long id,
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
    this.id = id;
    this.title = title;
    this.description = description;
    this.dateCreated = dateCreated;
    this.timeCreated = timeCreated;
    this.eventDate = eventDate;
    this.eventTime = eventTime;
    this.eventEndDate = eventEndDate;
    this.eventEndTime = eventEndTime;
    this.eventStatus = eventStatus;
    this.isFollowed = isFollowed;
  }

  // Event Mapper
  public Event toEvent() {
    return new Event(
        this.id,
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

  // Getters and Setters
  public Long getId() {
    return id;
  }

  // No need for setter Id gets generated by database
  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

}
