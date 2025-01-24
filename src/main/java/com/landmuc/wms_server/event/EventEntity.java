package com.landmuc.wms_server.event;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.*;

// using class instead of record because database configurations (mostly) need an empty constructor which record does not provide
@Entity
@Table(name = "events")
public class EventEntity {
  // ---------- INSTANCE VARIABLES ----------
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) // IDENTITY: relies on the database to generate unique Ids.
                                                      // Commonly used with dbs like MySQL and PostgreSQL
  private Long id;
  @Column(name = "owner_username")
  private String ownerUsername;
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

  // ---------- CONSTRUCTORS ----------
  public EventEntity() {
  }

  // Constructor without id, ownerUsername, dateCreated and timeCreated
  public EventEntity(
      String title,
      String description,
      LocalDate eventDate,
      LocalTime eventTime,
      LocalDate eventEndDate,
      LocalTime eventEndTime,
      EventStatus eventStatus) {
    this.title = title;
    this.description = description;
    this.eventDate = eventDate;
    this.eventTime = eventTime;
    this.eventEndDate = eventEndDate;
    this.eventEndTime = eventEndTime;
    this.eventStatus = eventStatus;
  }

  // Constructor without id dateCreated and timeCreated
  public EventEntity(
      String ownerUsername,
      String title,
      String description,
      LocalDate eventDate,
      LocalTime eventTime,
      LocalDate eventEndDate,
      LocalTime eventEndTime,
      EventStatus eventStatus) {
    this.ownerUsername = ownerUsername;
    this.title = title;
    this.description = description;
    this.eventDate = eventDate;
    this.eventTime = eventTime;
    this.eventEndDate = eventEndDate;
    this.eventEndTime = eventEndTime;
    this.eventStatus = eventStatus;
  }

  // Constructor with all instance variables
  public EventEntity(
      Long id,
      String ownerUsername,
      String title,
      String description,
      LocalDate dateCreated,
      LocalTime timeCreated,
      LocalDate eventDate,
      LocalTime eventTime,
      LocalDate eventEndDate,
      LocalTime eventEndTime,
      EventStatus eventStatus) {
    this.id = id;
    this.ownerUsername = ownerUsername;
    this.title = title;
    this.description = description;
    this.dateCreated = dateCreated;
    this.timeCreated = timeCreated;
    this.eventDate = eventDate;
    this.eventTime = eventTime;
    this.eventEndDate = eventEndDate;
    this.eventEndTime = eventEndTime;
    this.eventStatus = eventStatus;
  }

  // ---------- MAPPERS ----------
  public Event toEvent() {
    return new Event(
        this.id,
        this.ownerUsername,
        this.title,
        this.description,
        this.dateCreated,
        this.timeCreated,
        this.eventDate,
        this.eventTime,
        this.eventEndDate,
        this.eventEndTime,
        this.eventStatus);
  }

  // ---------- GETTERS (AND SETTERS) ----------
  public Long getId() {
    return id;
  }

  public String getownerUsername() {
    return ownerUsername;
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

}
