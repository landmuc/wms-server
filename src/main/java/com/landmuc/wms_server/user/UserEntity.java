package com.landmuc.wms_server.user;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.landmuc.wms_server.security.AuthorityRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class UserEntity {
  // ---------- INSTANCE VARIABLES ----------
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private UUID id = UUID.randomUUID();
  private String username;
  @Column(name = "hashed_password")
  private String hashedPassword;
  @Enumerated(EnumType.STRING)
  @Column(name = "authority_role")
  private AuthorityRole authorityRole;
  @Column(name = "date_created")
  private LocalDate dateCreated;
  private List<Long> followedEvents;

  // ---------- CONSTRUCTORS ----------
  public UserEntity() {
  }

  // Constructor without id and date_created
  public UserEntity(
      String username,
      String hashedPassword,
      AuthorityRole authorityRole,
      List<Long> followedEvents) {
    this.username = username;
    this.hashedPassword = hashedPassword;
    this.authorityRole = authorityRole;
    this.followedEvents = followedEvents;
  }

  // Constructor without password for mapping
  public UserEntity(
      UUID id,
      String username,
      AuthorityRole authorityRole,
      LocalDate dateCreated,
      List<Long> followedEvents) {
    this.id = id;
    this.username = username;
    this.authorityRole = authorityRole;
    this.dateCreated = dateCreated;
    this.followedEvents = followedEvents;
  }

  // Constructor with all instance variables
  public UserEntity(
      UUID id,
      String username,
      String hashedPassword,
      AuthorityRole authorityRole,
      LocalDate dateCreated,
      List<Long> followedEvents) {
    this.id = id;
    this.username = username;
    this.hashedPassword = hashedPassword;
    this.authorityRole = authorityRole;
    this.dateCreated = dateCreated;
    this.followedEvents = followedEvents;
  }

  // ---------- MAPPERS ----------

  // ---------- GETTERS (AND SETTERS) ----------
  public UUID getId() {
    return this.id;
  }

  public String getUsername() {
    return this.username;
  }

  public String getHashedPassword() {
    return this.hashedPassword;
  }

  public AuthorityRole getAuthorityRole() {
    return this.authorityRole;
  }

  public LocalDate getDateCreated() {
    return dateCreated;
  }

  public List<Long> getFollowedEvents() {
    return followedEvents;
  }

}
