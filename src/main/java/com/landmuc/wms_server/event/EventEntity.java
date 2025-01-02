package com.landmuc.wms_server.event;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.*;

// using class instead of record because database configurations (mostly) need an empty constructor which record does not provide
@Entity
@Table(name = "events")
public class EventEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) // IDENTITY: relies on the database to generate unique Ids.
                                                      // Commonly used with dbs like MySQL and PostgreSQL
  private Long id;
  private String owner;
  private String title;
  private String description;
  @Column(name = "date_created")
  private LocalDate dateCreated;
  @Column(name = "time_created")
  private LocalTime timeCreated;
  @Column(name = "event_date")
  private LocalDate eventDate;
  @Column(name = "event_time")
  private LocalTime eventTime;
  @Column(name = "event_end_date")
  private LocalDate eventEndDate;
  @Column(name = "event_end_time")
  private LocalTime eventEndTime;
  @Enumerated(EnumType.STRING)
  @Column(name = "event_status")
  private EventStatus eventStatus;
  @Column(name = "is_followed")
  private boolean isFollowed;

  public EventEntity() {
  }

  // Constructor without id, owner, dateCreated and timeCreated
  public EventEntity(
      String title,
      String description,
      LocalDate eventDate,
      LocalTime eventTime,
      LocalDate eventEndDate,
      LocalTime eventEndTime,
      EventStatus eventStatus,
      boolean isFollowed) {
    this.title = title;
    this.description = description;
    this.eventDate = eventDate;
    this.eventTime = eventTime;
    this.eventEndDate = eventEndDate;
    this.eventEndTime = eventEndTime;
    this.eventStatus = eventStatus;
    this.isFollowed = isFollowed;
  }

  //
  // Constructor without id dateCreated and timeCreated
  public EventEntity(
      String owner,
      String title,
      String description,
      LocalDate eventDate,
      LocalTime eventTime,
      LocalDate eventEndDate,
      LocalTime eventEndTime,
      EventStatus eventStatus,
      boolean isFollowed) {
    this.owner = owner;
    this.title = title;
    this.description = description;
    this.eventDate = eventDate;
    this.eventTime = eventTime;
    this.eventEndDate = eventEndDate;
    this.eventEndTime = eventEndTime;
    this.eventStatus = eventStatus;
    this.isFollowed = isFollowed;
  }

  // Constructor with all instance variables
  public EventEntity(
      Long id,
      String owner,
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
    this.owner = owner;
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
        this.owner,
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

  // Getters (and Setters)
  public Long getId() {
    return id;
  }

  public String getOwner() {
    return owner;
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

  public boolean isFollowed() {
    return isFollowed;
  }

}
