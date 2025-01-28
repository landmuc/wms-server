package com.landmuc.wms_server.followed_events;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import net.minidev.json.JSONArray;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FollowedEventsIntegrationTests {

  @Autowired
  TestRestTemplate restTemplate;

  @Test
  void shouldReturnAllFollowedEventIdsOfAKnownUserId() {

    ResponseEntity<String> response = restTemplate
        .withBasicAuth("userA", "a@123")
        .getForEntity("/follows/events/550e8400-e29b-41d4-a716-446655440000", String.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    DocumentContext documentContext = JsonPath.parse(response.getBody());
    int eventCount = documentContext.read("$.length()");
    assertThat(eventCount).isEqualTo(3);
    JSONArray eventIds = documentContext.read("$");
    assertThat(eventIds).containsExactlyInAnyOrder("f47ac10b-58cc-4372-a567-0e02b2c3d479",
        "38400000-8cf0-11bd-b23e-10b96e4ef00d", "a22c9092-5983-4111-b11e-6bf41c53a22c");
  }

  @Test
  void shouldReturnAllFollowedUserIdsOfAKnownEventId() {

    ResponseEntity<String> response = restTemplate
        .withBasicAuth("userA", "a@123")
        .getForEntity("/follows/users/f47ac10b-58cc-4372-a567-0e02b2c3d479", String.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    DocumentContext documentContext = JsonPath.parse(response.getBody());
    int eventCount = documentContext.read("$.length()");
    assertThat(eventCount).isEqualTo(2);
    JSONArray userIds = documentContext.read("$");
    assertThat(userIds).containsExactlyInAnyOrder("550e8400-e29b-41d4-a716-446655440000",
        "b90a3897-a6d8-4c83-8971-015234565432");
  }

}
