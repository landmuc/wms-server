package com.landmuc.wms_server.security;

public enum AuthorityRole {
  USER("USER"),
  ADMIN("ADMIN");

  private final String asString;

  private AuthorityRole(String asString) {
    this.asString = asString;
  }

  public String getAuthorityRoleAsString() {
    return asString;
  }
}
