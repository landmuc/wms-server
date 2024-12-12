package com.landmuc.wms_server.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long> {
  // Spring Data will automatically generate the implementation of the methods
  // below as long they are added to the Repository

}
