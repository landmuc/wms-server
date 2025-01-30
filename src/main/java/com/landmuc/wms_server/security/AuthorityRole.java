package com.landmuc.wms_server.security;

public enum AuthorityRole {
  USER("USER"),
  NON_USER("NON_USER"),
  ADMIN("ADMIN");

  private final String asString;

  private AuthorityRole(String asString) {
    this.asString = asString;
  }

  @Override
  public String toString() {
    return asString;
  }
}
