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

  public StepEntity updateStep(Step updatedStep) {
    // checks if the step which you want to update exists
    stepRepository.findById(updatedStep.id())
        .orElseThrow(() -> new StepNotFoundException(exceptionStep + updatedStep.getId()));

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
