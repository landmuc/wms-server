package com.landmuc.wms_server.step;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class StepJsonTest {

  @Autowired
  private JacksonTester<StepEntity> json;

  @Autowired
  private JacksonTester<StepEntity[]> jsonList;

  private StepEntity[] steps;

  @BeforeEach
  void steUp() {
    steps = Arrays.array(
        new StepEntity(
            UUID.fromString("e47bc10a-48cc-4372-a567-0e02b2c3d479"),
            "First Step Title",
            "First Step Description",
            LocalDate.of(2024, 10, 10), // dateCreated
            LocalTime.of(9, 15), // timeCreated
            LocalDate.of(2024, 12, 24), // eventDate
            LocalTime.of(16, 30), // eventTime
            LocalDate.of(2024, 12, 26), // eventEndDate
            LocalTime.of(18, 45), // eventEndTime
            UUID.fromString("f47ac10b-58cc-4372-a567-0e02b2c3d479")),
        new StepEntity(
            UUID.fromString("28400000-7cf0-11bd-b23e-10b96e4ef00d"),
            "Second Step Title",
            "Second Step Description",
            LocalDate.of(2024, 8, 10), // dateCreated
            LocalTime.of(8, 30), // timeCreated
            LocalDate.of(2024, 10, 7), // eventDate
            LocalTime.of(21, 30), // eventTime
            LocalDate.of(2024, 10, 8), // eventEndDate
            LocalTime.of(3, 0), // eventEndTime
            UUID.fromString("f47ac10b-58cc-4372-a567-0e02b2c3d479")),
        new StepEntity(
            UUID.fromString("922c9092-4983-4111-b11e-6bf41c53a22c"),
            "Third Step Title",
            "Third Step Description",
            LocalDate.of(2024, 10, 28), // dateCreated
            LocalTime.of(7, 15), // timeCreated
            LocalDate.of(2025, 12, 15), // eventDate
            LocalTime.of(14, 30), // eventTime
            LocalDate.of(2025, 12, 18), // eventEndDate
            LocalTime.of(12, 00), // eventEndTime
            UUID.fromString("a22c9092-5983-4111-b11e-6bf41c53a22c")));
  }

  @Test
  void stepSerializationTest() throws IOException {
    StepEntity step = steps[0];
    assertThat(json.write(step)).isStrictlyEqualToJson("singleStep.json");
    assertThat(json.write(step)).hasJsonPathStringValue("@.id");
    assertThat(json.write(step)).extractingJsonPathStringValue("@.id")
        .isEqualTo("e47bc10a-48cc-4372-a567-0e02b2c3d479");
    assertThat(json.write(step)).hasJsonPathStringValue("@.title");
    assertThat(json.write(step)).extractingJsonPathStringValue("@.title").isEqualTo("First Step Title");
    assertThat(json.write(step)).hasJsonPathStringValue("@.description");
    assertThat(json.write(step)).extractingJsonPathStringValue("@.description").isEqualTo("First Step Description");
    assertThat(json.write(step)).hasJsonPathStringValue("@.dateCreated");
    assertThat(json.write(step)).extractingJsonPathStringValue("@.dateCreated").isEqualTo("2024-10-10");
    assertThat(json.write(step)).hasJsonPathStringValue("@.timeCreated");
    assertThat(json.write(step)).extractingJsonPathStringValue("@.timeCreated").isEqualTo("09:15:00");
    assertThat(json.write(step)).hasJsonPathStringValue("@.stepDate");
    assertThat(json.write(step)).extractingJsonPathStringValue("@.stepDate").isEqualTo("2024-12-24");
    assertThat(json.write(step)).hasJsonPathStringValue("@.stepTime");
    assertThat(json.write(step)).extractingJsonPathStringValue("@.stepTime").isEqualTo("16:30:00");
    assertThat(json.write(step)).hasJsonPathStringValue("@.stepEndDate");
    assertThat(json.write(step)).extractingJsonPathStringValue("@.stepEndDate").isEqualTo("2024-12-26");
    assertThat(json.write(step)).hasJsonPathStringValue("@.stepEndTime");
    assertThat(json.write(step)).extractingJsonPathStringValue("@.stepEndTime").isEqualTo("18:45:00");
    assertThat(json.write(step)).hasJsonPathStringValue("@.eventId");
    assertThat(json.write(step)).extractingJsonPathStringValue("@.eventId")
        .isEqualTo("f47ac10b-58cc-4372-a567-0e02b2c3d479");
  }

  @Test
  void stepListSerializationTest() throws IOException {
    assertThat(jsonList.write(steps)).isStrictlyEqualToJson("listOfSteps.json");
  }

  @Test
  void stepDeserializationTest() throws IOException {
    String expected = """
        {
        "id": "e47bc10a-48cc-4372-a567-0e02b2c3d479",
        "title": "First Step Title",
        "description": "First Step Description",
        "dateCreated": "2024-10-10",
        "timeCreated": "09:15:00",
        "stepDate": "2024-12-24",
        "stepTime": "16:30:00",
        "stepEndDate": "2024-12-26",
        "stepEndTime": "18:45:00",
        "eventId": "f47ac10b-58cc-4372-a567-0e02b2c3d479"
        }
        """;

    StepEntity step = new StepEntity(
        UUID.fromString("e47bc10a-48cc-4372-a567-0e02b2c3d479"),
        "First Step Title",
        "First Step Description",
        LocalDate.of(2024, 10, 10), // dateCreated
        LocalTime.of(9, 15), // timeCreated
        LocalDate.of(2024, 12, 24), // stepDate
        LocalTime.of(16, 30), // stepTime
        LocalDate.of(2024, 12, 26), // stepEndDate
        LocalTime.of(18, 45), // stepEndTime
        UUID.fromString("f47ac10b-58cc-4372-a567-0e02b2c3d479"));

    assertThat(json.parse(expected)).isEqualTo(step);
    assertThat(json.parseObject(expected).getId()).isEqualTo(UUID.fromString("e47bc10a-48cc-4372-a567-0e02b2c3d479"));
    assertThat(json.parseObject(expected).getTitle()).isEqualTo("First Step Title");
    assertThat(json.parseObject(expected).getDescription()).isEqualTo("First Step Description");
    assertThat(json.parseObject(expected).getDateCreated()).isEqualTo(LocalDate.of(2024, 10, 10));
    assertThat(json.parseObject(expected).getTimeCreated()).isEqualTo(LocalTime.of(9, 15));
    assertThat(json.parseObject(expected).getStepDate()).isEqualTo(LocalDate.of(2024, 12, 24));
    assertThat(json.parseObject(expected).getStepTime()).isEqualTo(LocalTime.of(16, 30));
    assertThat(json.parseObject(expected).getStepEndDate()).isEqualTo(LocalDate.of(2024, 12, 26));
    assertThat(json.parseObject(expected).getStepEndTime()).isEqualTo(LocalTime.of(18, 45));
    assertThat(json.parseObject(expected).getEventId())
        .isEqualTo(UUID.fromString("f47ac10b-58cc-4372-a567-0e02b2c3d479"));
  }

  @Test
  void stepListDeserializationTest() throws IOException {
    String expected = """
                [
                  {
          "id": "e47bc10a-48cc-4372-a567-0e02b2c3d479",
          "title": "First Step Title",
          "description": "First Step Description",
          "dateCreated": "2024-10-10",
          "timeCreated": "09:15:00",
          "stepDate": "2024-12-24",
          "stepTime": "16:30:00",
          "stepEndDate": "2024-12-26",
          "stepEndTime": "18:45:00",
          "eventId": "f47ac10b-58cc-4372-a567-0e02b2c3d479"
        },
        {
          "id": "28400000-7cf0-11bd-b23e-10b96e4ef00d",
          "title": "Second Step Title",
          "description": "Second Step Description",
          "dateCreated": "2024-08-10",
          "timeCreated": "08:30:00",
          "stepDate": "2024-10-07",
          "stepTime": "21:30:00",
          "stepEndDate": "2024-10-08",
          "stepEndTime": "03:00:00",
          "eventId": "f47ac10b-58cc-4372-a567-0e02b2c3d479"
        },
        {
          "id": "922c9092-4983-4111-b11e-6bf41c53a22c",
          "title": "Third Step Title",
          "description": "Third Step Description",
          "dateCreated": "2024-10-28",
          "timeCreated": "07:15:00",
          "stepDate": "2025-12-15",
          "stepTime": "14:30:00",
          "stepEndDate": "2025-12-18",
          "stepEndTime": "12:00:00",
          "eventId": "a22c9092-5983-4111-b11e-6bf41c53a22c"
        }
                ]
                    """;
    assertThat(jsonList.parse(expected)).isEqualTo(steps);
  }
}
