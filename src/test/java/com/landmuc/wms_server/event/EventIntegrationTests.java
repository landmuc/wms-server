package com.landmuc.wms_server.event;

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
import com.landmuc.wms_server.event.EventEntity;
import com.landmuc.wms_server.event.EventStatus;

import net.minidev.json.JSONArray;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EventIntegrationTests {

  @Autowired
  TestRestTemplate restTemplate;

  @Test
  void shouldReturnAnEventWithAKnownId() {
    ResponseEntity<String> response = restTemplate
        .withBasicAuth("userC", "c@666") // to show that you can access the event
        // even if you are not the owner
        .getForEntity("/events/123", String.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    DocumentContext documentContext = JsonPath.parse(response.getBody());
    Number id = documentContext.read("$.id");
    assertThat(id).isEqualTo(123);
    String ownerUsername = documentContext.read("$.ownerUsername");
    assertThat(ownerUsername).isEqualTo("userA");
    String title = documentContext.read("$.title");
    assertThat(title).isEqualTo("First Title");
    String description = documentContext.read("$.description");
    assertThat(description).isEqualTo("First Description");
    String dateCreated = documentContext.read("$.dateCreated");
    assertThat(dateCreated).isEqualTo("2024-10-12");
    String timeCreated = documentContext.read("$.timeCreated");
    assertThat(timeCreated).isEqualTo("10:38:00");
    String eventDate = documentContext.read("$.eventDate");
    assertThat(eventDate).isEqualTo("2024-12-24");
    String eventTime = documentContext.read("$.eventTime");
    assertThat(eventTime).isEqualTo("14:30:00");
    String eventEndDate = documentContext.read("$.eventEndDate");
    assertThat(eventEndDate).isEqualTo("2025-11-17");
    String eventEndTime = documentContext.read("$.eventEndTime");
    assertThat(eventEndTime).isEqualTo("23:59:00");
  }

  @Test
  void shouldNotReturnAnEventWithAnUnknownId() {
    ResponseEntity<String> response = restTemplate
        .withBasicAuth("userA", "a@123")
        .getForEntity("/events/9999", String.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }

  @Test
  void shouldNotReturnAnEventWhenAccessingWithAnUnauthorizedRole() {
    ResponseEntity<String> response = restTemplate
        .withBasicAuth("userB", "b@344")
        .getForEntity("/events/123", String.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
  }

  @Test
  void shouldNotReturnAnEventWhenUsingWrongCredentials() {
    ResponseEntity<String> responseWrongUsername = restTemplate
        .withBasicAuth("unkown_user", "a@123") // wrong username
        .getForEntity("/events/123", String.class);
    assertThat(responseWrongUsername.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);

    ResponseEntity<String> responseWrongPassword = restTemplate
        .withBasicAuth("userA", "wrong_password!") // wrong password
        .getForEntity("/events/123", String.class);
    assertThat(responseWrongPassword.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
  }

  @Test
  void shouldReturnAllEventsWhenAListIsRequested() {
    ResponseEntity<String> response = restTemplate
        .withBasicAuth("userA", "a@123")
        .getForEntity("/events", String.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    DocumentContext documentContext = JsonPath.parse(response.getBody());
    int eventCount = documentContext.read("$.length()");
    assertThat(eventCount).isEqualTo(3);
    JSONArray ids = documentContext.read("$..id");
    assertThat(ids).containsExactlyInAnyOrder(123, 344, 666);
    JSONArray titles = documentContext.read("$..title");
    assertThat(titles).containsExactlyInAnyOrder("First Title", "Second Title",
        "Third Title");
  }

  @Test
  void shouldNotReturnEventsWhenAListIsRequestedWithAnUnauthorizedRole() {
    ResponseEntity<String> response = restTemplate
        .withBasicAuth("userB", "b@344")
        .getForEntity("/events", String.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
  }

  @Test
  void shouldNotReturnEventsWhenAListIsRequestedAndUsingWrongCredentials() {
    ResponseEntity<String> responseWrongUsername = restTemplate
        .withBasicAuth("unkown_user", "a@123") // wrong username
        .getForEntity("/events", String.class);
    assertThat(responseWrongUsername.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);

    ResponseEntity<String> responseWrongPassword = restTemplate
        .withBasicAuth("userA", "wrong_password!") // wrong password
        .getForEntity("/events", String.class);
    assertThat(responseWrongPassword.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
  }

  @Test
  void shouldReturnAPageOfEvents() {
    ResponseEntity<String> response = restTemplate
        .withBasicAuth("userA", "a@123")
        .getForEntity("/events?page=0&size=1", String.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    DocumentContext documentContext = JsonPath.parse(response.getBody());
    JSONArray page = documentContext.read("$[*]");
    assertThat(page.size()).isEqualTo(1);
  }

  @Test
  @DirtiesContext
  void shouldCreateANewEvent() {
    EventEntity eventEntity = new EventEntity(
        "userA",
        "New Created",
        "New Description",
        LocalDate.of(2025, 9, 14), // eventDate
        LocalTime.of(14, 30), // eventTime
        LocalDate.of(2025, 11, 27), // eventEndDate
        LocalTime.of(23, 59), // eventEndTime
        EventStatus.UPCOMING,
        true);
    ResponseEntity<Void> response = restTemplate
        .withBasicAuth("userA", "a@123")
        .postForEntity("/events", eventEntity, Void.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

    URI locationOfNewEvent = response.getHeaders().getLocation();
    ResponseEntity<String> getResponse = restTemplate
        .withBasicAuth("userA", "a@123")
        .getForEntity(locationOfNewEvent, String.class);
    assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

    DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
    Number id = documentContext.read("$.id");
    assertThat(id).isNotNull();
    String ownerUsername = documentContext.read("$.ownerUsername");
    assertThat(ownerUsername).isEqualTo("userA");
    String title = documentContext.read("$.title");
    assertThat(title).isEqualTo("New Created");
    String description = documentContext.read("$.description");
    assertThat(description).isEqualTo("New Description");
    String eventDate = documentContext.read("$.eventDate");
    assertThat(eventDate).isEqualTo("2025-09-14");
    String eventTime = documentContext.read("$.eventTime");
    assertThat(eventTime).isEqualTo("14:30:00");
    String eventEndDate = documentContext.read("$.eventEndDate");
    assertThat(eventEndDate).isEqualTo("2025-11-27");
    String eventEndTime = documentContext.read("$.eventEndTime");
    assertThat(eventEndTime).isEqualTo("23:59:00");

  }

  @Test
  void shouldNotCreateANewEventWithAnUnauthorizedRole() {
    EventEntity eventEntity = new EventEntity(
        "userA",
        "New Created",
        "New Description",
        LocalDate.of(2025, 9, 14), // eventDate
        LocalTime.of(14, 30), // eventTime
        LocalDate.of(2025, 11, 27), // eventEndDate
        LocalTime.of(23, 59), // eventEndTime
        EventStatus.UPCOMING,
        true);
    ResponseEntity<Void> response = restTemplate
        .withBasicAuth("userB", "b@344")
        .postForEntity("/events", eventEntity, Void.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
  }

  @Test
  void shouldNotCreateANewEventWhenUsingWrongCredentials() {
    EventEntity eventEntity = new EventEntity(
        "userA",
        "New Created",
        "New Description",
        LocalDate.of(2025, 9, 14), // eventDate
        LocalTime.of(14, 30), // eventTime
        LocalDate.of(2025, 11, 27), // eventEndDate
        LocalTime.of(23, 59), // eventEndTime
        EventStatus.UPCOMING,
        true);

    ResponseEntity<Void> responseWrongUsername = restTemplate
        .withBasicAuth("unkown_user", "b@344")
        .postForEntity("/events", eventEntity, Void.class);
    assertThat(responseWrongUsername.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);

    ResponseEntity<Void> responseWrongPassword = restTemplate
        .withBasicAuth("userB", "wrong_password!")
        .postForEntity("/events", eventEntity, Void.class);
    assertThat(responseWrongPassword.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
  }

  @Test
  @DirtiesContext
  void shouldDeleteAnExistingEvent() {
    ResponseEntity<Void> response = restTemplate
        .withBasicAuth("userA", "a@123")
        .exchange("/events/123", HttpMethod.DELETE, null, Void.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    ResponseEntity<String> getResponse = restTemplate
        .withBasicAuth("userA", "a@123")
        .getForEntity("/events/123", String.class);
    assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }

  @Test
  void shouldNotDeleteAnExistingEventWithAnUnauthorizedRole() {
    ResponseEntity<Void> response = restTemplate
        .withBasicAuth("userB", "b@344")
        .exchange("/events/123", HttpMethod.DELETE, null, Void.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
  }

  @Test
  void shouldNotDeleteAnExistingEventWhenUsingWrongCredentials() {
    ResponseEntity<Void> responseWrongUsername = restTemplate
        .withBasicAuth("unkown_user", "b@344")
        .exchange("/events/123", HttpMethod.DELETE, null, Void.class);
    assertThat(responseWrongUsername.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);

    ResponseEntity<Void> responseWrongPassword = restTemplate
        .withBasicAuth("userB", "wrong_password!")
        .exchange("/events/123", HttpMethod.DELETE, null, Void.class);
    assertThat(responseWrongPassword.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
  }

  @Test
  @DirtiesContext
  void shouldUpdateAnExistingEvent() {
    EventEntity eventEntityUpdate = new EventEntity(
        "Updated Title",
        "Updated Description",
        LocalDate.of(2025, 9, 14), // eventDate
        LocalTime.of(14, 30), // eventTime
        LocalDate.of(2025, 11, 27), // eventEndDate
        LocalTime.of(23, 59), // eventEndTime
        EventStatus.UPCOMING,
        true);
    HttpEntity<EventEntity> request = new HttpEntity<>(eventEntityUpdate);

    ResponseEntity<Void> response = restTemplate
        .withBasicAuth("userA", "a@123")
        .exchange("/events/123", HttpMethod.PUT, request, Void.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    ResponseEntity<String> getResponse = restTemplate
        .withBasicAuth("userA", "a@123")
        .getForEntity("/events/123", String.class);
    assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

    DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
    Number id = documentContext.read("$.id");
    assertThat(id).isEqualTo(123);
    String ownerUsername = documentContext.read("$.ownerUsername");
    assertThat(ownerUsername).isEqualTo("userA");
    String title = documentContext.read("$.title");
    assertThat(title).isEqualTo("Updated Title");
    String description = documentContext.read("$.description");
    assertThat(description).isEqualTo("Updated Description");
    String eventDate = documentContext.read("$.eventDate");
    assertThat(eventDate).isEqualTo("2025-09-14");
    String eventTime = documentContext.read("$.eventTime");
    assertThat(eventTime).isEqualTo("14:30:00");
    String eventEndDate = documentContext.read("$.eventEndDate");
    assertThat(eventEndDate).isEqualTo("2025-11-27");
    String eventEndTime = documentContext.read("$.eventEndTime");
    assertThat(eventEndTime).isEqualTo("23:59:00");
  }

  @Test
  void shouldNotUpdateAnEventWithAnUnauthorizedRole() {
    EventEntity eventEntityUpdate = new EventEntity(
        "Updated Title",
        "Updated Description",
        LocalDate.of(2025, 9, 14), // eventDate
        LocalTime.of(14, 30), // eventTime
        LocalDate.of(2025, 11, 27), // eventEndDate
        LocalTime.of(23, 59), // eventEndTime
        EventStatus.UPCOMING,
        true);
    HttpEntity<EventEntity> request = new HttpEntity<>(eventEntityUpdate);

    ResponseEntity<Void> response = restTemplate
        .withBasicAuth("userB", "b@344")
        .exchange("/events/123", HttpMethod.PUT, request, Void.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
  }

  @Test
  void shouldNotUpdateAnEventWhenUsingWrongCredentials() {
    EventEntity eventEntityUpdate = new EventEntity(
        "Updated Title",
        "Updated Description",
        LocalDate.of(2025, 9, 14), // eventDate
        LocalTime.of(14, 30), // eventTime
        LocalDate.of(2025, 11, 27), // eventEndDate
        LocalTime.of(23, 59), // eventEndTime
        EventStatus.UPCOMING,
        true);
    HttpEntity<EventEntity> request = new HttpEntity<>(eventEntityUpdate);

    ResponseEntity<Void> responseWrongUsername = restTemplate
        .withBasicAuth("unkown_user", "a@123")
        .exchange("/events/123", HttpMethod.PUT, request, Void.class);
    assertThat(responseWrongUsername.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);

    ResponseEntity<Void> responseWrongPassword = restTemplate
        .withBasicAuth("userA", "wrong_password!")
        .exchange("/events/123", HttpMethod.PUT, request, Void.class);
    assertThat(responseWrongPassword.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
  }

  @Test
  void shouldNotUpdateAnEventThatDoesNotExist() {
    EventEntity eventEntityUpdate = new EventEntity(
        "Updated Title",
        "Updated Description",
        LocalDate.of(2025, 9, 14), // eventDate
        LocalTime.of(14, 30), // eventTime
        LocalDate.of(2025, 11, 27), // eventEndDate
        LocalTime.of(23, 59), // eventEndTime
        EventStatus.UPCOMING,
        true);
    HttpEntity<EventEntity> request = new HttpEntity<>(eventEntityUpdate);
    ResponseEntity<Void> response = restTemplate
        .withBasicAuth("userA", "a@123")
        .exchange("/events/9999", HttpMethod.PUT, request, Void.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }
}
