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

INSERT INTO users (id, hashed_password, username, authority_role, date_created)
VALUES
(
    '550e8400-e29b-41d4-a716-446655440000',
    '$2a$10$6h0uwSXGUKGZgq1OeKdmzOW.NsAGpVwxpx5XCZoYTE/fC.YlPrM2O', --passwordA
    'userA',
    'USER',
    '2025-01-10'
),
(
    'e711568c-5f32-4e9c-b631-9404888c854f',
    '$2a$10$xJ4ZxpLpxAw0BwqUPw5A5uyYgpkHJZ3revK4TvWcqF8mKrEBJHXuq', --passwordB
    'userB',
    'NON-OWNER',
    '2025-01-11'
),
(
    'b90a3897-a6d8-4c83-8971-015234565432',
    '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', --passwordC
    'userC',
    'USER',
    '2025-01-12'
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
),
(
    'b90a3897-a6d8-4c83-8971-015234565432',
    'f47ac10b-58cc-4372-a567-0e02b2c3d479'
);

INSERT INTO steps (
    id,
    title,
    description,
    date_created,
    time_created,
    step_date,
    step_time,
    step_end_date,
    step_end_time,
    event_id
) VALUES
(
    'e47bc10a-48cc-4372-a567-0e02b2c3d479',
    'First Step Title',
    'First Step Description',
    '2024-10-10',
    '09:15:00',
    '2024-12-24',
    '16:30:00',
    '2024-12-26',
    '18:45:00',
    'f47ac10b-58cc-4372-a567-0e02b2c3d479'
),
(
    '28400000-7cf0-11bd-b23e-10b96e4ef00d',
    'Second Step Title',
    'Second Step Description',
    '2024-08-10',
    '08:30:00',
    '2024-10-07',
    '21:30:00',
    '2024-10-08',
    '03:00:00',
    'f47ac10b-58cc-4372-a567-0e02b2c3d479'
),
(
    '922c9092-4983-4111-b11e-6bf41c53a22c',
    'Third Step Title',
    'Third Step Description',
    '2024-10-28',
    '07:15:00',
    '2025-12-15',
    '14:30:00',
    '2025-12-18',
    '12:00:00',
    'a22c9092-5983-4111-b11e-6bf41c53a22c'
);
