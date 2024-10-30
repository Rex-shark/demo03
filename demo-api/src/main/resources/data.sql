INSERT INTO rex.user_base (created_at, id, last_login, last_logout, updated_at, account, password)
VALUES ('2024-10-30 01:11:05', 1, null, null, '2024-10-30 01:11:05', 'rex', 'E10ADC3949BA59ABBE56E057F20F883E')
    ON DUPLICATE KEY UPDATE id=id;