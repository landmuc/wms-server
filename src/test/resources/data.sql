INSERT INTO events (
    id, 
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
