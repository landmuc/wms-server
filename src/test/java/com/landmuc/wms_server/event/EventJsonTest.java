package com.landmuc.wms_server.event;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

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
        new Event(
            123L,
            "userA",
            "First Title",
            "First Description",
            LocalDate.of(2024, 10, 12), // dateCreated
            LocalTime.of(10, 38), // timeCreated
            LocalDate.of(2024, 12, 24), // eventDate
            LocalTime.of(14, 30), // eventTime
            LocalDate.of(2025, 11, 17), // eventEndDate
            LocalTime.of(23, 59), // eventEndTime
            EventStatus.ONGOING,
            true),
        new Event(
            344L,
            "userA",
            "Second Title",
            "Second Description",
            LocalDate.of(2024, 8, 13), // dateCreated
            LocalTime.of(10, 44), // timeCreated
            LocalDate.of(2024, 10, 7), // eventDate
            LocalTime.of(20, 0), // eventTime
            LocalDate.of(2024, 10, 8), // eventEndDate
            LocalTime.of(11, 59), // eventEndTime
            EventStatus.OVER,
            false),
        new Event(
            666L,
            "userC",
            "Third Title",
            "Third Description",
            LocalDate.of(2024, 10, 30), // dateCreated
            LocalTime.of(8, 30), // timeCreated
            LocalDate.of(2025, 12, 15), // eventDate
            LocalTime.of(12, 0), // eventTime
            LocalDate.of(2025, 12, 24), // eventEndDate
            LocalTime.of(17, 00), // eventEndTime
            EventStatus.UPCOMING,
            true));

  }

  @Test
  void eventSerializationTest() throws IOException {
    Event event = events[0];
    assertThat(json.write(event)).isStrictlyEqualToJson("singleEvent.json");
    assertThat(json.write(event)).hasJsonPathNumberValue("@.id");
    assertThat(json.write(event)).extractingJsonPathNumberValue("@.id").isEqualTo(123);
    assertThat(json.write(event)).hasJsonPathStringValue("@.ownerUsername");
    assertThat(json.write(event)).extractingJsonPathStringValue("@.ownerUsername").isEqualTo("userA");
    assertThat(json.write(event)).hasJsonPathStringValue("@.title");
    assertThat(json.write(event)).extractingJsonPathStringValue("@.title").isEqualTo("First Title");
    assertThat(json.write(event)).hasJsonPathStringValue("@.description");
    assertThat(json.write(event)).extractingJsonPathStringValue("@.description").isEqualTo("First Description");
    assertThat(json.write(event)).hasJsonPathStringValue("@.dateCreated");
    assertThat(json.write(event)).extractingJsonPathStringValue("@.dateCreated").isEqualTo("2024-10-12");
    assertThat(json.write(event)).hasJsonPathStringValue("@.timeCreated");
    assertThat(json.write(event)).extractingJsonPathStringValue("@.timeCreated").isEqualTo("10:38:00");
    assertThat(json.write(event)).hasJsonPathStringValue("@.eventDate");
    assertThat(json.write(event)).extractingJsonPathStringValue("@.eventDate").isEqualTo("2024-12-24");
    assertThat(json.write(event)).hasJsonPathStringValue("@.eventTime");
    assertThat(json.write(event)).extractingJsonPathStringValue("@.eventTime").isEqualTo("14:30:00");
    assertThat(json.write(event)).hasJsonPathStringValue("@.eventEndDate");
    assertThat(json.write(event)).extractingJsonPathStringValue("@.eventEndDate").isEqualTo("2025-11-17");
    assertThat(json.write(event)).hasJsonPathStringValue("@.eventEndTime");
    assertThat(json.write(event)).extractingJsonPathStringValue("@.eventEndTime").isEqualTo("23:59:00");
    assertThat(json.write(event)).hasJsonPathStringValue("@.eventStatus");
    assertThat(json.write(event)).extractingJsonPathStringValue("@.eventStatus").isEqualTo("ONGOING");
    assertThat(json.write(event)).hasJsonPathBooleanValue("@.isFollowed");
    assertThat(json.write(event)).extractingJsonPathBooleanValue("@.isFollowed").isEqualTo(true);
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
        "ownerUsername": "userA",
        "title": "First Title",
        "description": "First Description",
        "dateCreated": "2024-10-12",
        "timeCreated": "10:38:00",
        "eventDate": "2024-12-24",
        "eventTime": "14:30:00",
        "eventEndDate": "2025-11-17",
        "eventEndTime": "23:59:00",
        "eventStatus": "ONGOING",
        "isFollowed": true
        }
        """;

    Event event = new Event(
        123L,
        "userA",
        "First Title",
        "First Description",
        LocalDate.of(2024, 10, 12), // dateCreated
        LocalTime.of(10, 38), // timeCreated
        LocalDate.of(2024, 12, 24), // eventDate
        LocalTime.of(14, 30), // eventTime
        LocalDate.of(2025, 11, 17), // eventEndDate
        LocalTime.of(23, 59), // eventEndTime
        EventStatus.ONGOING,
        true);
    assertThat(json.parse(expected)).isEqualTo(event);
    assertThat(json.parseObject(expected).id()).isEqualTo(123);
    assertThat(json.parseObject(expected).ownerUsername()).isEqualTo("userA");
    assertThat(json.parseObject(expected).title()).isEqualTo("First Title");
    assertThat(json.parseObject(expected).description()).isEqualTo("First Description");
    assertThat(json.parseObject(expected).dateCreated()).isEqualTo(LocalDate.of(2024, 10, 12));
    assertThat(json.parseObject(expected).timeCreated()).isEqualTo(LocalTime.of(10, 38));
    assertThat(json.parseObject(expected).eventDate()).isEqualTo("2024-12-24");
    assertThat(json.parseObject(expected).eventTime()).isEqualTo("14:30:00");
    assertThat(json.parseObject(expected).eventEndDate()).isEqualTo("2025-11-17");
    assertThat(json.parseObject(expected).eventEndTime()).isEqualTo("23:59:00");
    assertThat(json.parseObject(expected).eventStatus()).isEqualTo(EventStatus.ONGOING);
    assertThat(json.parseObject(expected).isFollowed()).isEqualTo(true);
  }

  @Test
  void eventListDeserialitationTest() throws IOException {
    String expected = """
                [
                  {
          "id": 123,
          "ownerUsername": "userA",
          "title": "First Title",
          "description": "First Description",
          "dateCreated": "2024-10-12",
          "timeCreated": "10:38:00",
          "eventDate": "2024-12-24",
          "eventTime": "14:30:00",
          "eventEndDate": "2025-11-17",
          "eventEndTime": "23:59:00",
          "eventStatus": "ONGOING",
          "isFollowed": true
        },
        {
          "id": 344,
          "ownerUsername": "userA",
          "title": "Second Title",
          "description": "Second Description",
          "dateCreated": "2024-08-13",
          "timeCreated": "10:44:00",
          "eventDate": "2024-10-07",
          "eventTime": "20:00:00",
          "eventEndDate": "2024-10-08",
          "eventEndTime": "11:59:00",
          "eventStatus": "OVER",
          "isFollowed": false
        },
        {
          "id": 666,
          "ownerUsername": "userC",
          "title": "Third Title",
          "description": "Third Description",
          "dateCreated": "2024-10-30",
          "timeCreated": "08:30:00",
          "eventDate": "2025-12-15",
          "eventTime": "12:00:00",
          "eventEndDate": "2025-12-24",
          "eventEndTime": "17:00:00",
          "eventStatus": "UPCOMING",
          "isFollowed": true
        }
                ]
                    """;
    assertThat(jsonList.parse(expected)).isEqualTo(events);
  }

}
