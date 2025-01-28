package com.landmuc.wms_server.followed_events;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowedEventsService {
  private final FollowedEventsRepository followedEventsRepository;

  @Autowired
  public FollowedEventsService(FollowedEventsRepository followedEventsRepository) {
    this.followedEventsRepository = followedEventsRepository;
  }

  public List<UUID> findAllFollowedEventIdsByUserId(UUID userId) {
    List<UUID> followedEventIds = followedEventsRepository.findAllFollowedEventIdsByUserId(userId);
    return followedEventIds;
  }

  public List<UUID> findAllFollowedUserIdsByEventId(UUID eventId) {
    List<UUID> followedUserIds = followedEventsRepository.findAllFollowedUserIdsByEventId(eventId);
    return followedUserIds;
  }

}
