package com.landmuc.wms_server.step;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.landmuc.wms_server.domain.exception.StepNotFoundException;

@RestController
@RequestMapping("/steps")
public class StepController {
  private final StepService stepService;

  @Autowired
  StepController(StepService stepService) {
    this.stepService = stepService;
  }

  @GetMapping("/{stepId}")
  private ResponseEntity<Step> findStepById(@PathVariable UUID stepId) {
    Step step = stepService.findStepById(stepId);

    if (step == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(step);
  }

  @GetMapping("/event/{eventId}")
  private ResponseEntity<List<UUID>> getAllStepIdsOfASingleEvent(@PathVariable UUID eventId) {
    List<UUID> stepList = stepService.getAllStepIdsOfASingleEvent(eventId);

    if (stepList.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(stepList);
  }

  @PostMapping
  private ResponseEntity<Void> createStep(@RequestBody Step newStepRequest, UriComponentsBuilder ucb) {
    StepEntity savedStepEntity = stepService.createStep(newStepRequest);
    URI locationOfNewStep = ucb
        .path("/steps/{id}")
        .buildAndExpand(savedStepEntity.getId())
        .toUri();

    return ResponseEntity.created(locationOfNewStep).build();
  }

  @PutMapping("/{stepId}")
  private ResponseEntity<Void> updateStep(@PathVariable UUID stepId, @RequestBody Step updatedStep) {
    StepEntity stepEntity = stepService.updateStep(stepId, updatedStep);

    if (stepEntity == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{stepId}")
  private ResponseEntity<Void> deleteEvent(@PathVariable UUID stepId) {
    if (!stepService.deleteStepById(stepId)) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.noContent().build();
  }

  @ExceptionHandler(StepNotFoundException.class)
  private ResponseEntity<Map<String, String>> handleStepNotFoundException(StepNotFoundException exception) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", exception.getMessage()));
  }

}
