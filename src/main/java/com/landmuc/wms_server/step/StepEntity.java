package com.landmuc.wms_server.step;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import jakarta.persistence.*;

@Entity
@Table(name = "steps")
class StepEntity {
  // ---------- INSTANCE VARIABLES ----------
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  private String title;
  private String description;
  @Column(name = "date_created")
  private LocalDate dateCreated;
  @Column(name = "time_created")
  private LocalTime timeCreated;
  @Column(name = "step_date")
  private LocalDate stepDate;
  @Column(name = "step_time")
  private LocalTime stepTime;
  @Column(name = "step_end_date")
  private LocalDate stepEndDate;
  @Column(name = "step_end_time")
  private LocalTime stepEndTime;
  @Column(name = "event_id")
  private UUID eventId;

  // ---------- CONSTRUCTORS ----------
  public StepEntity() {
  }

  public StepEntity(
      UUID id, String title, String description, LocalDate dateCreated, LocalTime timeCreated,
      LocalDate stepDate, LocalTime stepTime, LocalDate stepEndDate, LocalTime stepEndTime, UUID eventId) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.dateCreated = dateCreated;
    this.timeCreated = timeCreated;
    this.stepDate = stepDate;
    this.stepTime = stepTime;
    this.stepEndDate = stepEndDate;
    this.stepEndTime = stepEndTime;
    this.eventId = eventId;
  }

  // ---------- EQUALS & HASHCODE----------
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((title == null) ? 0 : title.hashCode());
    result = prime * result + ((description == null) ? 0 : description.hashCode());
    result = prime * result + ((dateCreated == null) ? 0 : dateCreated.hashCode());
    result = prime * result + ((timeCreated == null) ? 0 : timeCreated.hashCode());
    result = prime * result + ((stepDate == null) ? 0 : stepDate.hashCode());
    result = prime * result + ((stepTime == null) ? 0 : stepTime.hashCode());
    result = prime * result + ((stepEndDate == null) ? 0 : stepEndDate.hashCode());
    result = prime * result + ((stepEndTime == null) ? 0 : stepEndTime.hashCode());
    result = prime * result + ((eventId == null) ? 0 : eventId.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    StepEntity other = (StepEntity) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (title == null) {
      if (other.title != null)
        return false;
    } else if (!title.equals(other.title))
      return false;
    if (description == null) {
      if (other.description != null)
        return false;
    } else if (!description.equals(other.description))
      return false;
    if (dateCreated == null) {
      if (other.dateCreated != null)
        return false;
    } else if (!dateCreated.equals(other.dateCreated))
      return false;
    if (timeCreated == null) {
      if (other.timeCreated != null)
        return false;
    } else if (!timeCreated.equals(other.timeCreated))
      return false;
    if (stepDate == null) {
      if (other.stepDate != null)
        return false;
    } else if (!stepDate.equals(other.stepDate))
      return false;
    if (stepTime == null) {
      if (other.stepTime != null)
        return false;
    } else if (!stepTime.equals(other.stepTime))
      return false;
    if (stepEndDate == null) {
      if (other.stepEndDate != null)
        return false;
    } else if (!stepEndDate.equals(other.stepEndDate))
      return false;
    if (stepEndTime == null) {
      if (other.stepEndTime != null)
        return false;
    } else if (!stepEndTime.equals(other.stepEndTime))
      return false;
    if (eventId == null) {
      if (other.eventId != null)
        return false;
    } else if (!eventId.equals(other.eventId))
      return false;
    return true;
  }

  // ---------- MAPPERS ----------
  public Step toStep() {
    return new Step(
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

  // ---------- GETTERS & SETTERS ----------

  public UUID getId() {
    return id;
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

  public LocalDate getStepDate() {
    return stepDate;
  }

  public LocalTime getStepTime() {
    return stepTime;
  }

  public LocalDate getStepEndDate() {
    return stepEndDate;
  }

  public LocalTime getStepEndTime() {
    return stepEndTime;
  }

  public UUID getEventId() {
    return eventId;
  }

}
