package com.landmuc.wms_server;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.jayway.jsonpath.DocumentContext;
import com.landmuc.wms_server.event.EventEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WmsServerApplicationTests {

  @Autowired
  TestRestTemplate restTemplate;

  @Test
  void shouldReturnEventWithKnownId() {
    ResponseEntity<String> response = restTemplate
        .getForEntity("/events/123", String.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

  }

  // TODO: Create event test
  @Test
  void shouldCreateANewEvent() {
    EventEntity eventEntity = new EventEntity(null, "Freshly Created", "New Description");
    ResponseEntity<Void> response = restTemplate
        .postForEntity("/events", eventEntity, Void.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

  }

  // TODO: Delete event test

  // TODO: Update event test

}
