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
    '2024-12-01',           
    '09:15:22',
    '2024-12-24',           
    '14:30:00',
    '2024-12-24',
    '16:30:00',
    'Upcoming',
    true
),
(
    344,
    'Second Title',
    'Second Description',
    '2024-12-15',           
    '14:22:45',
    '2024-12-31',           
    '20:00:00',
    '2025-01-01',
    '02:00:00',
    'Upcoming',
    false
),
(
    666,
    'Third Title',
    'Third Description',
    '2024-12-23',           
    '08:30:17',
    '2025-01-15',           
    '09:00:00',
    '2025-01-15',
    '17:00:00',
    'Upcoming',
    false
);
