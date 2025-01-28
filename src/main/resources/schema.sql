CREATE TABLE IF NOT EXISTS events (
    id UUID PRIMARY KEY,
    owner_username VARCHAR(255),
    title VARCHAR(255),
    description VARCHAR(255),
    date_created DATE DEFAULT CURRENT_DATE,
    time_created TIME DEFAULT CURRENT_TIME,
    event_date DATE,
    event_time TIME,
    event_end_date DATE,
    event_end_time TIME,
    event_status VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS users (
    id UUID PRIMARY KEY,
    username VARCHAR(255),
    authority_role VARCHAR(255),
    date_created DATE
);

CREATE TABLE IF NOT EXISTS followed_events (
    user_id UUID,
    event_id UUID,
    PRIMARY KEY (user_id, event_id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (event_id) REFERENCES events (id) ON DELETE CASCADE
)
