package com.landmuc.wms_server.event;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

@JsonTest
class EventJsonTest {

  // Jackson is a popular JSON processing library
  // JacksonTester is used for testing JSON serialization and deserializaiton
  @Autowired
  private JacksonTester<Event> json;

  @Autowired
  private JacksonTester<Event[]> jsonList;

  private Event[] events;

  @BeforeEach
  void setUp() {
    events = Arrays.array(
        new Event(123L, "First Title", "First Description"),
        new Event(344L, "Second Title", "Second Description"),
        new Event(666L, "Third Title", "Third Description"));

  }

  @Test
  void eventSerializationTest() throws IOException {
    Event event = events[0];
    assertThat(json.write(event)).isStrictlyEqualToJson("singleEvent.json");
    assertThat(json.write(event)).hasJsonPathNumberValue("@.id");
    assertThat(json.write(event)).extractingJsonPathNumberValue("@.id").isEqualTo(123);
    assertThat(json.write(event)).hasJsonPathStringValue("@.title");
    assertThat(json.write(event)).extractingJsonPathStringValue("@.title").isEqualTo("First Title");
    assertThat(json.write(event)).hasJsonPathStringValue("@.description");
    assertThat(json.write(event)).extractingJsonPathStringValue("@.description").isEqualTo("First Description");
  }

  @Test
  void eventListSerializationTest() throws IOException {
    assertThat(jsonList.write(events)).isStrictlyEqualToJson("listOfEvents.json");
  }

  @Test
  void eventDeserializationTest() throws IOException {
    String expected = """
        {
        "id": 123,
        "title": "First Title",
        "description": "First Description"
        }
        """;

    Event event = new Event(123L, "First Title", "First Description");
    assertThat(json.parse(expected)).isEqualTo(event);
    assertThat(json.parseObject(expected).id()).isEqualTo(123);
    assertThat(json.parseObject(expected).title()).isEqualTo("First Title");
    assertThat(json.parseObject(expected).description()).isEqualTo("First Description");
  }

  @Test
  void eventListDeserialitationTest() throws IOException {
    String expected = """
        [
          {"id": 123, "title": "First Title", "description": "First Description"},
          {"id": 344, "title": "Second Title", "description": "Second Description"},
          {"id": 666, "title": "Third Title", "description": "Third Description"}
        ]
            """;
    assertThat(jsonList.parse(expected)).isEqualTo(events);
  }

}
