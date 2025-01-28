package com.landmuc.wms_server.followed_events;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/follows")
public class FollowedEventsController {
  private final FollowedEventsService followedEventsService;

  @Autowired
  private FollowedEventsController(FollowedEventsService followedEventsService) {
    this.followedEventsService = followedEventsService;
  }

  @GetMapping("/events/{userId}")
  private ResponseEntity<List<UUID>> findAllFollowedEventIdsByUserId(@PathVariable UUID userId) {
    List<UUID> eventIds = followedEventsService.findAllFollowedEventIdsByUserId(userId);

    if (eventIds == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(eventIds);
  }

  @GetMapping("/users/{eventId}")
  private ResponseEntity<List<UUID>> findAllFollowedUserIdsByEventId(@PathVariable UUID eventId) {
    List<UUID> eventIds = followedEventsService.findAllFollowedUserIdsByEventId(eventId);

    if (eventIds == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(eventIds);
  }

}
