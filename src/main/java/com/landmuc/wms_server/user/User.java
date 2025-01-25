package com.landmuc.wms_server.user;

import java.time.LocalDate;
import java.util.UUID;

import com.landmuc.wms_server.security.AuthorityRole;

// hashedPassword for security reasons not included
public record User(
    UUID id,
    String username,
    AuthorityRole authorityRole,
    LocalDate dateCreated) {

  // TODO: DELETE Constructor?
  // ---------- CONSTRUCTORS ----------
  public User(
      String username,
      AuthorityRole authorityRole) {
    this(
        null,
        username,
        authorityRole,
        null);
  }

  // ---------- MAPPERS ----------
  public UserEntity toUserEntity() {
    return new UserEntity(
        this.id,
        this.username,
        this.authorityRole,
        this.dateCreated);
  }

}
