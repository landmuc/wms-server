package com.landmuc.wms_server.step;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.landmuc.wms_server.domain.exception.StepNotFoundException;

@Service
@PropertySource("classpath:messages.properties")
public class StepService {
  @Value("${exception.step}")
  private String exceptionStep;

  private final StepRepository stepRepository;

  @Autowired
  StepService(StepRepository stepRepository) {
    this.stepRepository = stepRepository;
  }

  public Step findStepById(UUID stepId) {
    StepEntity stepEntity = stepRepository.findById(stepId)
        .orElseThrow(() -> new StepNotFoundException(exceptionStep + stepId));

    return stepEntity.toStep();
  }
}
