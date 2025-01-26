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
    'f47ac10b-58cc-4372-a567-0e02b2c3d479',
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
    '38400000-8cf0-11bd-b23e-10b96e4ef00d',
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
    'a22c9092-5983-4111-b11e-6bf41c53a22c',
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

INSERT INTO users (id, username, authority_role, date_created)
VALUES (
    '550e8400-e29b-41d4-a716-446655440000',
    'john_doe',
    'USER',
    '2025-01-10'
);

INSERT INTO followed_events (user_id, event_id)
VALUES
(
    '550e8400-e29b-41d4-a716-446655440000',
    'f47ac10b-58cc-4372-a567-0e02b2c3d479'
),
(
    '550e8400-e29b-41d4-a716-446655440000',
    '38400000-8cf0-11bd-b23e-10b96e4ef00d'
),
(
    '550e8400-e29b-41d4-a716-446655440000',
    'a22c9092-5983-4111-b11e-6bf41c53a22c'
);
