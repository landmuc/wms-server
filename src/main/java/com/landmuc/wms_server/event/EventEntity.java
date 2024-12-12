package com.landmuc.wms_server.event;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

// using class instead of record because database configurations (mostly) need an empty constructor which record does not provide
@Entity
public class EventEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public Long id;
  public String title;
  public String description;

  public EventEntity() {
  }

  public EventEntity(Long id, String title, String description) {
    this.id = id;
    this.title = title;
    this.description = description;
  }

  public Long getId() {
    return id;
  }

  // No need for setter Id gets generated by database
  // public void setId(Long id) {
  // this.id = id;
  // }

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