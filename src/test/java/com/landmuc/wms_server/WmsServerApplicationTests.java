package com.landmuc.wms_server;

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

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WmsServerApplicationTests {

  @Autowired
  TestRestTemplate restTemplate;

  @Test
  void shouldReturnEventWithKnownId() {
    ResponseEntity<String> response = restTemplate
        .getForEntity("/events/123", String.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    DocumentContext documentContext = JsonPath.parse(response.getBody());
    Number id = documentContext.read("$.id");
    assertThat(id).isEqualTo(123);
    String title = documentContext.read("$.title");
    assertThat(title).isEqualTo("First Title");
    String description = documentContext.read("$.description");
    assertThat(description).isEqualTo("First Description");
  }

  @Test
  void shouldNotReturnEventWithUnknownId() {
    ResponseEntity<String> response = restTemplate
        .getForEntity("/events/9999", String.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }

  @Test
  void shouldCreateANewEvent() {
    EventEntity eventEntity = new EventEntity(null, "Freshly Created", "New Description");
    ResponseEntity<Void> response = restTemplate
        .postForEntity("/events", eventEntity, Void.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

    URI locationOfNewEvent = response.getHeaders().getLocation();
    ResponseEntity<String> getResponse = restTemplate
        .getForEntity(locationOfNewEvent, String.class);
    assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

    DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
    Number id = documentContext.read("$.id");
    assertThat(id).isNotNull();
    String title = documentContext.read("$.title");
    assertThat(title).isEqualTo("Freshly Created");
    String description = documentContext.read("$.description");
    assertThat(description).isEqualTo("New Description");

  }

  @Test
  @DirtiesContext
  void shoudlDeleteExistingEvent() {
    ResponseEntity<Void> response = restTemplate
        .exchange("/events/123", HttpMethod.DELETE, null, Void.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    ResponseEntity<String> getResponse = restTemplate
        .getForEntity("/events/123", String.class);
    assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }

  @Test
  @DirtiesContext
  void shouldUpdateAnExistingEvent() {
    EventEntity eventEntityUpdate = new EventEntity(null, "Updated title", "Updated description");
    HttpEntity<EventEntity> request = new HttpEntity<>(eventEntityUpdate);
    ResponseEntity<Void> response = restTemplate
        .exchange("/events/123", HttpMethod.PUT, request, Void.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    ResponseEntity<String> getResponse = restTemplate
        .getForEntity("/events/123", String.class);
    assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

    DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
    Number id = documentContext.read("$.id");
    assertThat(id).isEqualTo(123);
    String title = documentContext.read("$.title");
    assertThat(title).isEqualTo("Updated title");
    String description = documentContext.read("$.description");
    assertThat(description).isEqualTo("Updated description");
  }

  @Test
  void shouldNotUpdateAnEventThatDoesNotExist() {
    EventEntity eventEntityUpdate = new EventEntity(null, "Updated title", "Updated description");
    HttpEntity<EventEntity> request = new HttpEntity<>(eventEntityUpdate);
    ResponseEntity<Void> response = restTemplate
        .exchange("/events/9999", HttpMethod.PUT, request, Void.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }
}
