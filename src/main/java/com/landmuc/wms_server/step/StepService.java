package com.landmuc.wms_server.step;

import java.util.List;
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
  @Value("${exception.mismatchedIds}")
  private String exceptionMismatchedIds;

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

  public List<UUID> getAllStepIdsOfASingleEvent(UUID eventId) {
    List<UUID> stepList = stepRepository.getAllStepIdsOfASingleEvent(eventId);

    return stepList;
  }

  public StepEntity createStep(Step step) {
    return stepRepository.save(step.toStepEntity());

  }

  public StepEntity updateStep(UUID stepId, Step updatedStep) {
    stepRepository.findById(stepId)
        .orElseThrow(() -> new StepNotFoundException(exceptionStep + stepId));

    if (!stepId.equals(updatedStep.id())) {
      throw new StepNotFoundException(String.format(exceptionMismatchedIds, stepId, updatedStep.id()));
    }

    return stepRepository.save(updatedStep.toStepEntity());
  }

  public boolean deleteStepById(UUID stepId) {
    if (!stepRepository.existsById(stepId)) {
      throw new StepNotFoundException(exceptionStep + stepId);
    }
    stepRepository.deleteById(stepId);
    return true;
  }

}
