package com.landmuc.wms_server.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserIntegrationTests {

  @Autowired
  TestRestTemplate restTemplate;

  @Test
  void shouldReturnAnUserWithAKnownId() {

    ResponseEntity<String> response = restTemplate
        .withBasicAuth("userA", "a@123")
        .getForEntity("/users/550e8400-e29b-41d4-a716-446655440000", String.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    DocumentContext documentContext = JsonPath.parse(response.getBody());
    String id = documentContext.read("$.id");
    assertThat(id).isEqualTo("550e8400-e29b-41d4-a716-446655440000");
    String username = documentContext.read("$.username");
    assertThat(username).isEqualTo("userA");
    String authorityRole = documentContext.read("$.authorityRole");
    assertThat(authorityRole).isEqualTo("USER");
    String dateCreated = documentContext.read("$.dateCreated");
    assertThat(dateCreated).isEqualTo("2025-01-10");
  }
}
