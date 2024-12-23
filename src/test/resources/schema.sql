CREATE TABLE IF NOT EXISTS events (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    description VARCHAR(255),
    date_created DATE DEFAULT CURRENT_DATE,
    time_created TIME DEFAULT CURRENT_TIME,
    event_date DATE,
    event_time TIME,
    event_end_date DATE,
    event_end_Time TIME,
    eventStatus VARCHAR(255),
    is_followed BOOlEAN DEFAULT FALSE,
);
