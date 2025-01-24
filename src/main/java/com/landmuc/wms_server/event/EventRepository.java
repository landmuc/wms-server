package com.landmuc.wms_server.event;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Use EventEntity because Jpa works with entity classes not records
@Repository
public interface EventRepository extends JpaRepository<EventEntity, UUID> {
  // Spring Data will automatically generate the implementation of the methods
  // below as long they are added to the Repository

  boolean existsByIdAndOwnerUsername(UUID id, String ownerUsername);

}
