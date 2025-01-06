package com.landmuc.wms_server.user;

import java.time.LocalDate;
import java.util.List;

import com.landmuc.wms_server.security.AuthorityRole;

// hashedPassword for security reasons not included
public record User(
    Long id,
    String username,
    AuthorityRole authorityRole,
    LocalDate dateCreated,
    List<Long> followedEvents) {

  // TODO: DELETE Constructor?
  // ---------- CONSTRUCTORS ----------
  public User(
      String username,
      AuthorityRole authorityRole,
      List<Long> followedEvents) {
    this(
        null,
        username,
        authorityRole,
        null,
        followedEvents);
  }

  // ---------- MAPPERS ----------
  public UserEntity toUserEntity() {
    return new UserEntity(
        this.id,
        this.username,
        this.authorityRole,
        this.dateCreated,
        this.followedEvents);
  }

}
