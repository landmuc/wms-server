package com.landmuc.wms_server.followed_events;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FollowedEventsIntegrationTests {

  @Autowired
  TestRestTemplate restTemplate;

  @Test
  void shouldReturnAllFollowedEventsOfAKnownUserId() {

    ResponseEntity<String> response = restTemplate
        .withBasicAuth("userA", "a@123")
        .getForEntity("/followed-events/550e8400-e29b-41d4-a716-446655440000", String.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }
}
