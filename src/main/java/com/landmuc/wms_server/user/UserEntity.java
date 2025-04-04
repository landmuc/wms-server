package com.landmuc.wms_server.user;

import java.time.LocalDate;
import java.util.Objects;
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
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id = UUID.randomUUID();
  private String username;
  @Column(name = "hashed_password")
  private String hashedPassword;
  @Enumerated(EnumType.STRING)
  @Column(name = "authority_role")
  private AuthorityRole authorityRole;
  @Column(name = "date_created")
  private LocalDate dateCreated;

  // ---------- CONSTRUCTORS ----------
  public UserEntity() {
  }

  // Constructor without id and date_created
  public UserEntity(
      String username,
      String hashedPassword,
      AuthorityRole authorityRole) {
    this.username = username;
    this.hashedPassword = hashedPassword;
    this.authorityRole = authorityRole;
  }

  // Constructor without password for mapping
  public UserEntity(
      UUID id,
      String username,
      AuthorityRole authorityRole,
      LocalDate dateCreated) {
    this.id = id;
    this.username = username;
    this.authorityRole = authorityRole;
    this.dateCreated = dateCreated;
  }

  // Constructor with all instance variables
  public UserEntity(
      UUID id,
      String username,
      String hashedPassword,
      AuthorityRole authorityRole,
      LocalDate dateCreated) {
    this.id = id;
    this.username = username;
    this.hashedPassword = hashedPassword;
    this.authorityRole = authorityRole;
    this.dateCreated = dateCreated;
  }

  // ---------- MAPPERS ----------
  public User toUser() {
    return new User(
        this.id,
        this.username,
        this.authorityRole,
        this.dateCreated);
  }

  // ---------- EQUALS & HASHCODE----------
  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    UserEntity that = (UserEntity) o;
    return Objects.equals(id, that.id) &&
        Objects.equals(username, that.username) &&
        Objects.equals(hashedPassword, that.hashedPassword) &&
        Objects.equals(authorityRole, that.authorityRole) &&
        Objects.equals(dateCreated, that.dateCreated);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, username, hashedPassword, authorityRole, dateCreated);
  }

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

}
