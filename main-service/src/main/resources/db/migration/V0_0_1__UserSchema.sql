CREATE TABLE IF NOT EXISTS users_table(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    registered_at BIGINT NOT NULL
);