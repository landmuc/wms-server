package com.landmuc.wms_server.followed_events;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowedEventsRepository extends JpaRepository<FollowedEventsRepository, FollowedEventsId> {
}
