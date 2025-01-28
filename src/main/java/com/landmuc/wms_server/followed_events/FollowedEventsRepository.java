package com.landmuc.wms_server.followed_events;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowedEventsRepository extends JpaRepository<FollowedEventsEntity, FollowedEventsId> {

  // finds all events (ids) which a certain user follows
  @Query("SELECT fe.eventId FROM FollowedEventsEntity fe WHERE fe.userId = :userId")
  List<UUID> findAllFollowedEventIdsByUserId(@Param("userId") UUID userId);

  // finds all users (ids) which follow a certain event
  @Query("SELECT fe.userId FROM FollowedEventsEntity fe WHERE fe.eventId = :eventId")
  List<UUID> findAllFollowedUserIdsByEventId(@Param("eventId") UUID eventId);

}
