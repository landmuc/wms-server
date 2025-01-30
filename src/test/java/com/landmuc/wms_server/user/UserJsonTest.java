package com.landmuc.wms_server.user;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import com.landmuc.wms_server.security.AuthorityRole;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

@JsonTest
class UserJsonTest {

  @Autowired
  private JacksonTester<UserEntity> json;

  @Autowired
  private JacksonTester<UserEntity[]> jsonList;

  private UserEntity[] users;

  @BeforeEach
  void setUp() {
    users = Arrays.array(
        new UserEntity(
            UUID.fromString("550e8400-e29b-41d4-a716-446655440000"),
            "userA",
            AuthorityRole.USER,
            LocalDate.of(2025, 1, 10)),
        new UserEntity(
            UUID.fromString("e711568c-5f32-4e9c-b631-9404888c854f"),
            "userB",
            AuthorityRole.NON_USER,
            LocalDate.of(2025, 1, 11)),
        new UserEntity(
            UUID.fromString("b90a3897-a6d8-4c83-8971-015234565432"),
            "userC",
            AuthorityRole.USER,
            LocalDate.of(2025, 1, 12)));
  }

  @Test
  void userSerializationTest() throws IOException {
    UserEntity user = users[0];
    assertThat(json.write(user)).isStrictlyEqualToJson("singleUser.json");
    assertThat(json.write(user)).hasJsonPathStringValue("@.id");
    assertThat(json.write(user)).extractingJsonPathStringValue("@.id")
        .isEqualTo("550e8400-e29b-41d4-a716-446655440000");
    assertThat(json.write(user)).hasJsonPathStringValue("@.username");
    assertThat(json.write(user)).extractingJsonPathStringValue("@.username")
        .isEqualTo("userA");
    assertThat(json.write(user)).hasJsonPathStringValue("@.authorityRole");
    assertThat(json.write(user)).extractingJsonPathStringValue("@.authorityRole")
        .isEqualTo("USER");
    assertThat(json.write(user)).hasJsonPathStringValue("@.dateCreated");
    assertThat(json.write(user)).extractingJsonPathStringValue("@.dateCreated")
        .isEqualTo("2025-01-10");
  }

  @Test
  void userListSerializationTest() throws IOException {
    assertThat(jsonList.write(users)).isStrictlyEqualToJson("listOfUsers.json");
  }

  @Test
  void userDeserializationTest() throws IOException {
    String expected = """
        {
         "id": "550e8400-e29b-41d4-a716-446655440000",
         "username": "userA",
         "authorityRole": "USER",
         "dateCreated": "2025-01-10"
        }
        """;

    UserEntity user = new UserEntity(
        UUID.fromString("550e8400-e29b-41d4-a716-446655440000"),
        "userA",
        AuthorityRole.USER,
        LocalDate.of(2025, 1, 10));

    assertThat(json.parse(expected)).isEqualTo(user);
    assertThat(json.parseObject(expected).getId()).isEqualTo(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"));
    assertThat(json.parseObject(expected).getUsername()).isEqualTo("userA");
    assertThat(json.parseObject(expected).getAuthorityRole()).isEqualTo("USER");
    assertThat(json.parseObject(expected).getDateCreated()).isEqualTo(LocalDate.of(2025, 1, 10));
  }

  @Test
  void userListDeserializationTest() throws IOException {
    String expected = """
        [
           {
             "id": "550e8400-e29b-41d4-a716-446655440000",
             "username": "userA",
             "authorityRole": "USER",
             "dateCreated": "2025-01-10"
           },
           {
             "id": "e711568c-5f32-4e9c-b631-9404888c854f",
             "username": "userB",
             "authorityRole": "NON_USER",
             "dateCreated": "2025-01-11"
           },
           {
             "id": "b90a3897-a6d8-4c83-8971-015234565432",
             "username": "userC",
             "authorityRole": "USER",
             "dateCreated": "2025-01-12"
           }
         ]
        """;
    assertThat(jsonList.parse(expected)).isEqualTo(users);
  }
}
