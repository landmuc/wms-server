package com.landmuc.wms_server.step;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/steps")
public class StepController {
  private final StepService stepService;

  @Autowired
  StepController(StepService stepService) {
    this.stepService = stepService;
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

}
