CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE trips (
    id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    destination VARCHAR(255) NOT NULL,
    starts_at TIMESTAMP NOT NULL,
    ends_at TIMESTAMP NOT NULL,
    is_confirmed BOOLEAN NOT NULL,
    owner_name VARCHAR(255) NOT NULL,
    owner_email VARCHAR(255) NOT NULL
);

-- H2 Database
-- CREATE TABLE trips (
--    id UUID DEFAULT RANDOM_UUID() PRIMARY KEY,
--    destination VARCHAR(255) NOT NULL,
--    starts_at TIMESTAMP NOT NULL,
--    ends_at TIMESTAMP NOT NULL,
--    is_confirmed BOOLEAN NOT NULL,
--    owner_name VARCHAR(255) NOT NULL,
--    owner_email VARCHAR(255) NOT NULL
--);