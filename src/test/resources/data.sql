INSERT INTO events (
    id,
    owner_username,
    title,
    description,
    date_created,
    time_created,
    event_date,
    event_time,
    event_end_date,
    event_end_time,
    event_status
) VALUES
(
    123,
    'userA',
    'First Title',
    'First Description',
    '2024-10-12',
    '10:38:00',
    '2024-12-24',
    '14:30:00',
    '2025-11-17',
    '23:59:00',
    'ONGOING'
),
(
    344,
    'userA',
    'Second Title',
    'Second Description',
    '2024-08-13',
    '10:44:00',
    '2024-10-07',
    '20:00:00',
    '2024-10-08',
    '11:59:00',
    'OVER'
),
(
    666,
    'userC',
    'Third Title',
    'Third Description',
    '2024-10-30',
    '08:30:00',
    '2025-12-15',
    '12:00:00',
    '2025-12-24',
    '17:00:00',
    'UPCOMING'
);

--add followed_events
INSERT INTO users (id, username, authority_role, date_created)
VALUES (
    '550e8400-e29b-41d4-a716-446655440000',
    'john_doe',
    'USER',
    '2025-01-10'
);
