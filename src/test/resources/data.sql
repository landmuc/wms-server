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
    event_status,
    is_followed
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
    'ONGOING',
    true
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
    'OVER',
    false
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
    'UPCOMING',
    true
);

--add followed_events
INSERT INTO users (id, username, authority_role, date_created)
VALUES (
    '550e8400-e29b-41d4-a716-446655440000',
    -- 5555,
    'john_doe',
    'USER',
    CURRENT_DATE
    -- ARRAY[
    --     '123e4567-e89b-12d3-a456-426614174000',
    --     '987fcdeb-51d2-3a45-b789-012345678901',
    --     'abc12345-6789-def0-1234-567890123456'
    -- ] -- This works despite of the error
);
