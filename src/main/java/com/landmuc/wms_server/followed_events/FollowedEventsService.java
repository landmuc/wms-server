package com.landmuc.wms_server.followed_events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowedEventsService {
  private final FollowedEventsRepository followedEventsRepository;

  @Autowired
  public FollowedEventsService(FollowedEventsRepository followedEventsRepository) {
    this.followedEventsRepository = followedEventsRepository;
  }

}
