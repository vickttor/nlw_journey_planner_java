CREATE TABLE links(
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    url VARCHAR(500) NOT NULL,
    trip_id UUID,
    FOREIGN KEY (trip_id) REFERENCES trips(id) ON DELETE CASCADE
);

-- H2 database
--CREATE TABLE links(
--  id UUID DEFAULT RANDOM_UUID() PRIMARY KEY,
--  title VARCHAR(255) NOT NULL,
--  url VARCHAR(500) NOT NULL,
--  trip_id UUID,
--  FOREIGN KEY (trip_id) REFERENCES trips(id) ON DELETE CASCADE
--);