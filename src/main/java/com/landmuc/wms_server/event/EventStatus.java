package com.landmuc.wms_server.event;

public enum EventStatus {
  UPCOMING("Upcoming"),
  ONGOING("Ongoing"),
  OVER("Over");

  private final String eventStatusText;

  private EventStatus(String eventStatusText) {
    this.eventStatusText = eventStatusText;
  }

  public String getText() {
    return eventStatusText;
  }
}
