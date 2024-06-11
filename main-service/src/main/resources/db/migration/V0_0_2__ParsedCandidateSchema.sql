CREATE TABLE IF NOT EXISTS parsed_candidates_table(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL,
    fido VARCHAR(255) UNIQUE NOT NULL,
    picture_url VARCHAR(255) NOT NULL,
    position_name VARCHAR(255) NOT NULL,
    salary INTEGER NOT NULL,
    cv_url VARCHAR(255) NOT NULL,

    FOREIGN KEY (user_id) REFERENCES users_table (id)
);