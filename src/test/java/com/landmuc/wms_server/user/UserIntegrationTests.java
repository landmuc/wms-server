package com.landmuc.wms_server.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserIntegrationTests {

  @Autowired
  TestRestTemplate restTemplate;

  @Test
  void shouldReturnAnUserWithAKnownId() {

    ResponseEntity<String> response = restTemplate
        .withBasicAuth("userA", "a@123")
        .getForEntity("/users/5555", String.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

  }
}
