package com.landmuc.wms_server.followed_events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/followed-events")
public class FollowedEventsController {
  private FollowedEventsService followedEventsService;

  @Autowired
  public FollowedEventsController(FollowedEventsService followedEventsService) {
    this.followedEventsService = followedEventsService;
  }
}
