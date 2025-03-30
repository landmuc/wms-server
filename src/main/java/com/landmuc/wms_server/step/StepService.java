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

  public List<Step> getAllStepsOfASingleEvent(UUID eventId) {
    List<Step> stepList = stepRepository.getAllStepsOfASingleEvent(eventId)
        .stream()
        .map(StepEntity::toStep)
        .toList();

    return stepList;
  }

  public StepEntity createStep(Step step) {
    return stepRepository.save(step.toStepEntity());

  }

  public StepEntity updateStep(UUID stepId, Step updatedStep) {
    // checks if the step which you want to update exists
    stepRepository.findById(stepId)
        .orElseThrow(() -> new StepNotFoundException(exceptionStep + stepId));

    // checks if the id provided in the URI (stepId) and the id of the updated step
    // you provided are actually the same
    if (stepId != updatedStep.id()) {
      throw new StepNotFoundException(String.format(exceptionMismatchedIds, stepId, updatedStep.id()));
    }

    // saves and returns the updated StepEntity
    return stepRepository.save(updatedStep.toStepEntity());
  }

  public boolean deleteStepById(UUID stepId) {
    if (stepRepository.existsById(stepId)) {
      stepRepository.deleteById(stepId);
      return true;
    }

    return false;
  }

}
