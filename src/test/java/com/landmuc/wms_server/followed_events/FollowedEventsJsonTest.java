package com.landmuc.wms_server.followed_events;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.UUID;

@JsonTest
class FollowedEventsJsonTest {

  @Autowired
  private JacksonTester<FollowedEventsEntity> json;

  @Autowired
  private JacksonTester<FollowedEventsEntity[]> jsonList;

  private FollowedEventsEntity[] followedEvents;

  @BeforeEach
  void setUp() {
    followedEvents = Arrays.array(
        new FollowedEventsEntity(
            UUID.fromString("550e8400-e29b-41d4-a716-446655440000"),
            UUID.fromString("f47ac10b-58cc-4372-a567-0e02b2c3d479")),
        new FollowedEventsEntity(
            UUID.fromString("550e8400-e29b-41d4-a716-446655440000"),
            UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d")),
        new FollowedEventsEntity(
            UUID.fromString("550e8400-e29b-41d4-a716-446655440000"),
            UUID.fromString("a22c9092-5983-4111-b11e-6bf41c53a22c")),
        new FollowedEventsEntity(
            UUID.fromString("b90a3897-a6d8-4c83-8971-015234565432"),
            UUID.fromString("f47ac10b-58cc-4372-a567-0e02b2c3d479")));
  }

  @Test
  void followedEventsSerializationTest() throws IOException {
    FollowedEventsEntity followedEvent = followedEvents[0];
    assertThat(json.write(followedEvent)).isStrictlyEqualToJson("singleFollowedEvent.json");
    assertThat(json.write(followedEvent)).hasJsonPathStringValue("@.userId");
    assertThat(json.write(followedEvent)).extractingJsonPathStringValue("@.userId")
        .isEqualTo("550e8400-e29b-41d4-a716-446655440000");
    assertThat(json.write(followedEvent)).hasJsonPathStringValue("@.eventId");
    assertThat(json.write(followedEvent)).extractingJsonPathStringValue("@.eventId")
        .isEqualTo("f47ac10b-58cc-4372-a567-0e02b2c3d479");
  }

  @Test
  void followedEventsListSerializationTest() throws IOException {
    assertThat(jsonList.write(followedEvents)).isStrictlyEqualToJson("listOfFollowedEvents.json");
  }

  @Test
  void followedEventsDeserializationTest() throws IOException {
    String expected = """
        {
          "userId": "550e8400-e29b-41d4-a716-446655440000",
          "eventId": "f47ac10b-58cc-4372-a567-0e02b2c3d479"
        }
        """;

    FollowedEventsEntity followedEvent = new FollowedEventsEntity(
        UUID.fromString("550e8400-e29b-41d4-a716-446655440000"),
        UUID.fromString("f47ac10b-58cc-4372-a567-0e02b2c3d479"));

    assertThat(json.parse(expected)).isEqualTo(followedEvent);
    assertThat(json.parseObject(expected).getUserId())
        .isEqualTo(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"));
    assertThat(json.parseObject(expected).getEventId())
        .isEqualTo(UUID.fromString("f47ac10b-58cc-4372-a567-0e02b2c3d479"));
  }

  @Test
  void followedEventsListDeserializationTest() throws IOException {
    String expected = """
        [
            {
              "userId": "550e8400-e29b-41d4-a716-446655440000",
              "eventId": "f47ac10b-58cc-4372-a567-0e02b2c3d479"
            },
            {
              "userId": "550e8400-e29b-41d4-a716-446655440000",
              "eventId": "38400000-8cf0-11bd-b23e-10b96e4ef00d"
            },
            {
              "userId": "550e8400-e29b-41d4-a716-446655440000",
              "eventId": "a22c9092-5983-4111-b11e-6bf41c53a22c"
            },
            {
              "userId": "b90a3897-a6d8-4c83-8971-015234565432",
              "eventId": "f47ac10b-58cc-4372-a567-0e02b2c3d479"
            }
        ]
        """;
    assertThat(jsonList.parse(expected)).isEqualTo(followedEvents);
  }
}
