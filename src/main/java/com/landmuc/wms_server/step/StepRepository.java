package com.landmuc.wms_server.step;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StepRepository extends JpaRepository<StepEntity, UUID> {

  @Query("SELECT se.id FROM StepEntity se WHERE se.eventId = :eventId")
  List<StepEntity> getAllStepsOfASingleEvent(@Param("eventId") UUID eventId);

}
