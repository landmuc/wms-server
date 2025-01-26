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
@RequestMapping("/followed-events")
public class FollowedEventsController {
  private FollowedEventsService followedEventsService;

  @Autowired
  private FollowedEventsController(FollowedEventsService followedEventsService) {
    this.followedEventsService = followedEventsService;
  }

  @GetMapping("/{requestedId}")
  private ResponseEntity<List<UUID>> findAllFollowedEventIdsByUserId(@PathVariable UUID requestedId) {
    List<UUID> eventIds = followedEventsService.findAllFollowedEventIdsByUserId(requestedId);

    if (eventIds == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(eventIds);
  }
}
