package com.landmuc.wms_server.step;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import net.minidev.json.JSONArray;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StepIntegrationTests {

  @Autowired
  TestRestTemplate restTemplate;

  @Test
  void shouldReturnAStepWithAKnownId() {
    ResponseEntity<String> response = restTemplate
        .withBasicAuth("userC", "c@666")
        .getForEntity("/steps/e47bc10a-48cc-4372-a567-0e02b2c3d479", String.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    DocumentContext documentContext = JsonPath.parse(response.getBody());
    String id = documentContext.read("$.id");
    assertThat(id).isEqualTo("e47bc10a-48cc-4372-a567-0e02b2c3d479");
    String title = documentContext.read("$.title");
    assertThat(title).isEqualTo("First Step Title");
    String description = documentContext.read("$.description");
    assertThat(description).isEqualTo("First Step Description");
    String dateCreated = documentContext.read("$.dateCreated");
    assertThat(dateCreated).isEqualTo("2024-10-10");
    String timeCreated = documentContext.read("$.timeCreated");
    assertThat(timeCreated).isEqualTo("09:15:00");
    String stepDate = documentContext.read("$.stepDate");
    assertThat(stepDate).isEqualTo("2024-12-24");
    String stepTime = documentContext.read("$.stepTime");
    assertThat(stepTime).isEqualTo("16:30:00");
    String stepEndDate = documentContext.read("$.stepEndDate");
    assertThat(stepEndDate).isEqualTo("2024-12-26");
    String stepEndTime = documentContext.read("$.stepEndTime");
    assertThat(stepEndTime).isEqualTo("18:45:00");
    String eventId = documentContext.read("$.eventId");
    assertThat(eventId).isEqualTo("f47ac10b-58cc-4372-a567-0e02b2c3d479");
  }

  @Test
  void shouldNotReturnAStepWithAnUnknownId() {
    ResponseEntity<String> response = restTemplate
        .withBasicAuth("userA", "a@123")
        .getForEntity("/steps/9d8f5715-2e7c-4e64-8e34-35f510c12e66", String.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }

  @Test
  void shouldNotReturnAStepWhenAccessingWithAnUnauthorizedRole() {
    ResponseEntity<String> response = restTemplate
        .withBasicAuth("userB", "b@344")
        .getForEntity("/steps/e47bc10a-48cc-4372-a567-0e02b2c3d479", String.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
  }

  @Test
  void shouldNotReturnAStepWhenUsingWrongCredentials() {
    ResponseEntity<String> responseWrongUsername = restTemplate
        .withBasicAuth("unknown_user", "a@123")
        .getForEntity("/steps/e47bc10a-48cc-4372-a567-0e02b2c3d479", String.class);
    assertThat(responseWrongUsername.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);

    ResponseEntity<String> responseWrongPassword = restTemplate
        .withBasicAuth("userA", "wrong_password!")
        .getForEntity("/steps/e47bc10a-48cc-4372-a567-0e02b2c3d479", String.class);
    assertThat(responseWrongPassword.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
  }

  @Test
  void shouldReturnAllStepIdsOfASingleEvent() {
    ResponseEntity<String> response = restTemplate
        .withBasicAuth("userA", "a@123")
        .getForEntity("/steps/event/f47ac10b-58cc-4372-a567-0e02b2c3d479", String.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    DocumentContext documentContext = JsonPath.parse(response.getBody());
    int stepCount = documentContext.read("$.length()");
    assertThat(stepCount).isEqualTo(2);
    JSONArray stepIds = documentContext.read("$");
    assertThat(stepIds).containsExactlyInAnyOrder(
        "e47bc10a-48cc-4372-a567-0e02b2c3d479",
        "28400000-7cf0-11bd-b23e-10b96e4ef00d");
  }

  @Test
  void shouldNotReturnStepIdsOfAnEventThatDoesNotExist() {
    ResponseEntity<String> response = restTemplate
        .withBasicAuth("userA", "a@123")
        .getForEntity("/steps/event/9d8f5715-2e7c-4e64-8e34-35f510c12e66", String.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }

  @Test
  void shouldNotReturnStepIdsWhenUsingWrongCredentials() {
    ResponseEntity<String> responseWrongUsername = restTemplate
        .withBasicAuth("unknown_user", "a@123")
        .getForEntity("/steps/event/f47ac10b-58cc-4372-a567-0e02b2c3d479", String.class);
    assertThat(responseWrongUsername.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);

    ResponseEntity<String> responseWrongPassword = restTemplate
        .withBasicAuth("userA", "wrong_password!")
        .getForEntity("/steps/event/f47ac10b-58cc-4372-a567-0e02b2c3d479", String.class);
    assertThat(responseWrongPassword.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
  }

  @Test
  @DirtiesContext
  void shouldCreateANewStep() {
    StepEntity stepEntity = new StepEntity(
        null, // id will be generated
        "New Step Title",
        "New Step Description",
        LocalDate.now(),
        LocalTime.now(),
        LocalDate.of(2024, 12, 24),
        LocalTime.of(16, 30),
        LocalDate.of(2024, 12, 26),
        LocalTime.of(18, 45),
        UUID.fromString("f47ac10b-58cc-4372-a567-0e02b2c3d479"));

    ResponseEntity<Void> response = restTemplate
        .withBasicAuth("userA", "a@123")
        .postForEntity("/steps", stepEntity, Void.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

    URI locationOfNewStep = response.getHeaders().getLocation();
    ResponseEntity<String> getResponse = restTemplate
        .withBasicAuth("userA", "a@123")
        .getForEntity(locationOfNewStep, String.class);
    assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

    DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
    String id = documentContext.read("$.id");
    assertThat(id).isNotNull();
    String title = documentContext.read("$.title");
    assertThat(title).isEqualTo("New Step Title");
    String description = documentContext.read("$.description");
    assertThat(description).isEqualTo("New Step Description");
    String stepDate = documentContext.read("$.stepDate");
    assertThat(stepDate).isEqualTo("2024-12-24");
    String stepTime = documentContext.read("$.stepTime");
    assertThat(stepTime).isEqualTo("16:30:00");
    String stepEndDate = documentContext.read("$.stepEndDate");
    assertThat(stepEndDate).isEqualTo("2024-12-26");
    String stepEndTime = documentContext.read("$.stepEndTime");
    assertThat(stepEndTime).isEqualTo("18:45:00");
    String eventId = documentContext.read("$.eventId");
    assertThat(eventId).isEqualTo("f47ac10b-58cc-4372-a567-0e02b2c3d479");
  }

  @Test
  void shouldNotCreateANewStepWithAnUnauthorizedRole() {
    StepEntity stepEntity = new StepEntity(
        null,
        "New Step Title",
        "New Step Description",
        LocalDate.now(),
        LocalTime.now(),
        LocalDate.of(2024, 12, 24),
        LocalTime.of(16, 30),
        LocalDate.of(2024, 12, 26),
        LocalTime.of(18, 45),
        UUID.fromString("f47ac10b-58cc-4372-a567-0e02b2c3d479"));

    ResponseEntity<Void> response = restTemplate
        .withBasicAuth("userB", "b@344")
        .postForEntity("/steps", stepEntity, Void.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
  }

  @Test
  void shouldNotCreateANewStepWhenUsingWrongCredentials() {
    StepEntity stepEntity = new StepEntity(
        null,
        "New Step Title",
        "New Step Description",
        LocalDate.now(),
        LocalTime.now(),
        LocalDate.of(2024, 12, 24),
        LocalTime.of(16, 30),
        LocalDate.of(2024, 12, 26),
        LocalTime.of(18, 45),
        UUID.fromString("f47ac10b-58cc-4372-a567-0e02b2c3d479"));

    ResponseEntity<Void> responseWrongUsername = restTemplate
        .withBasicAuth("unknown_user", "a@123")
        .postForEntity("/steps", stepEntity, Void.class);
    assertThat(responseWrongUsername.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);

    ResponseEntity<Void> responseWrongPassword = restTemplate
        .withBasicAuth("userA", "wrong_password!")
        .postForEntity("/steps", stepEntity, Void.class);
    assertThat(responseWrongPassword.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
  }

  @Test
  @DirtiesContext
  void shouldUpdateAnExistingStep() {
    StepEntity stepEntityUpdate = new StepEntity(
        UUID.fromString("e47bc10a-48cc-4372-a567-0e02b2c3d479"),
        "Updated Step Title",
        "Updated Step Description",
        LocalDate.now(),
        LocalTime.now(),
        LocalDate.of(2024, 12, 25),
        LocalTime.of(17, 30),
        LocalDate.of(2024, 12, 27),
        LocalTime.of(19, 45),
        UUID.fromString("f47ac10b-58cc-4372-a567-0e02b2c3d479"));

    HttpEntity<StepEntity> request = new HttpEntity<>(stepEntityUpdate);

    ResponseEntity<Void> response = restTemplate
        .withBasicAuth("userA", "a@123")
        .exchange("/steps/e47bc10a-48cc-4372-a567-0e02b2c3d479", HttpMethod.PUT, request, Void.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    ResponseEntity<String> getResponse = restTemplate
        .withBasicAuth("userA", "a@123")
        .getForEntity("/steps/e47bc10a-48cc-4372-a567-0e02b2c3d479", String.class);
    assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

    DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
    String id = documentContext.read("$.id");
    assertThat(id).isEqualTo("e47bc10a-48cc-4372-a567-0e02b2c3d479");
    String title = documentContext.read("$.title");
    assertThat(title).isEqualTo("Updated Step Title");
    String description = documentContext.read("$.description");
    assertThat(description).isEqualTo("Updated Step Description");
    String stepDate = documentContext.read("$.stepDate");
    assertThat(stepDate).isEqualTo("2024-12-25");
    String stepTime = documentContext.read("$.stepTime");
    assertThat(stepTime).isEqualTo("17:30:00");
    String stepEndDate = documentContext.read("$.stepEndDate");
    assertThat(stepEndDate).isEqualTo("2024-12-27");
    String stepEndTime = documentContext.read("$.stepEndTime");
    assertThat(stepEndTime).isEqualTo("19:45:00");
    String eventId = documentContext.read("$.eventId");
    assertThat(eventId).isEqualTo("f47ac10b-58cc-4372-a567-0e02b2c3d479");
  }

  @Test
  void shouldNotUpdateAStepThatDoesNotExist() {
    StepEntity stepEntityUpdate = new StepEntity(
        UUID.fromString("9d8f5715-2e7c-4e64-8e34-35f510c12e66"),
        "Updated Step Title",
        "Updated Step Description",
        LocalDate.now(),
        LocalTime.now(),
        LocalDate.of(2024, 12, 25),
        LocalTime.of(17, 30),
        LocalDate.of(2024, 12, 27),
        LocalTime.of(19, 45),
        UUID.fromString("f47ac10b-58cc-4372-a567-0e02b2c3d479"));

    HttpEntity<StepEntity> request = new HttpEntity<>(stepEntityUpdate);
    ResponseEntity<String> response = restTemplate
        .withBasicAuth("userA", "a@123")
        .exchange("/steps/9d8f5715-2e7c-4e64-8e34-35f510c12e66", HttpMethod.PUT, request, String.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }

  @Test
  void shouldNotUpdateAStepWithAnUnauthorizedRole() {
    StepEntity stepEntityUpdate = new StepEntity(
        UUID.fromString("e47bc10a-48cc-4372-a567-0e02b2c3d479"),
        "Updated Step Title",
        "Updated Step Description",
        LocalDate.now(),
        LocalTime.now(),
        LocalDate.of(2024, 12, 25),
        LocalTime.of(17, 30),
        LocalDate.of(2024, 12, 27),
        LocalTime.of(19, 45),
        UUID.fromString("f47ac10b-58cc-4372-a567-0e02b2c3d479"));

    HttpEntity<StepEntity> request = new HttpEntity<>(stepEntityUpdate);
    ResponseEntity<Void> response = restTemplate
        .withBasicAuth("userB", "b@344")
        .exchange("/steps/e47bc10a-48cc-4372-a567-0e02b2c3d479", HttpMethod.PUT, request, Void.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
  }

  @Test
  void shouldNotUpdateAStepWhenUsingWrongCredentials() {
    StepEntity stepEntityUpdate = new StepEntity(
        UUID.fromString("e47bc10a-48cc-4372-a567-0e02b2c3d479"),
        "Updated Step Title",
        "Updated Step Description",
        LocalDate.now(),
        LocalTime.now(),
        LocalDate.of(2024, 12, 25),
        LocalTime.of(17, 30),
        LocalDate.of(2024, 12, 27),
        LocalTime.of(19, 45),
        UUID.fromString("f47ac10b-58cc-4372-a567-0e02b2c3d479"));

    HttpEntity<StepEntity> request = new HttpEntity<>(stepEntityUpdate);

    ResponseEntity<Void> responseWrongUsername = restTemplate
        .withBasicAuth("unknown_user", "a@123")
        .exchange("/steps/e47bc10a-48cc-4372-a567-0e02b2c3d479", HttpMethod.PUT, request, Void.class);
    assertThat(responseWrongUsername.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);

    ResponseEntity<Void> responseWrongPassword = restTemplate
        .withBasicAuth("userA", "wrong_password!")
        .exchange("/steps/e47bc10a-48cc-4372-a567-0e02b2c3d479", HttpMethod.PUT, request, Void.class);
    assertThat(responseWrongPassword.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
  }

  @Test
  @DirtiesContext
  void shouldDeleteAnExistingStep() {
    ResponseEntity<Void> response = restTemplate
        .withBasicAuth("userA", "a@123")
        .exchange("/steps/e47bc10a-48cc-4372-a567-0e02b2c3d479", HttpMethod.DELETE, null, Void.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    ResponseEntity<String> getResponse = restTemplate
        .withBasicAuth("userA", "a@123")
        .getForEntity("/steps/e47bc10a-48cc-4372-a567-0e02b2c3d479", String.class);
    assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }

  @Test
  void shouldNotDeleteAStepThatDoesNotExist() {
    ResponseEntity<Void> response = restTemplate
        .withBasicAuth("userA", "a@123")
        .exchange("/steps/9d8f5715-2e7c-4e64-8e34-35f510c12e66", HttpMethod.DELETE, null, Void.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }

  @Test
  void shouldNotDeleteAStepWithAnUnauthorizedRole() {
    ResponseEntity<Void> response = restTemplate
        .withBasicAuth("userB", "b@344")
        .exchange("/steps/e47bc10a-48cc-4372-a567-0e02b2c3d479", HttpMethod.DELETE, null, Void.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
  }

  @Test
  void shouldNotDeleteAStepWhenUsingWrongCredentials() {
    ResponseEntity<Void> responseWrongUsername = restTemplate
        .withBasicAuth("unknown_user", "a@123")
        .exchange("/steps/e47bc10a-48cc-4372-a567-0e02b2c3d479", HttpMethod.DELETE, null, Void.class);
    assertThat(responseWrongUsername.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);

    ResponseEntity<Void> responseWrongPassword = restTemplate
        .withBasicAuth("userA", "wrong_password!")
        .exchange("/steps/e47bc10a-48cc-4372-a567-0e02b2c3d479", HttpMethod.DELETE, null, Void.class);
    assertThat(responseWrongPassword.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
  }
}
