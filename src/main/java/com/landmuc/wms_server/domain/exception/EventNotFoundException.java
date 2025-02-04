package com.landmuc.wms_server.domain.exception;

public class EventNotFoundException extends RuntimeException {

  public EventNotFoundException(String message) {
    super(message);
  }
}

