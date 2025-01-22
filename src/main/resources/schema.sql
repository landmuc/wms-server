CREATE TABLE IF NOT EXISTS events (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    owner_username VARCHAR(255),
    title VARCHAR(255),
    description VARCHAR(255),
    date_created DATE DEFAULT CURRENT_DATE,
    time_created TIME DEFAULT CURRENT_TIME,
    event_date DATE,
    event_time TIME,
    event_end_date DATE,
    event_end_time TIME,
    event_status VARCHAR(255),
    is_followed BOOLEAN DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS users (
    id Long PRIMARY KEY,
    username VARCHAR(255),
    authority_role VARCHAR(255),
    date_created DATE,
    followed_events UUID ARRAY -- This works despite of the error
);
