CREATE TABLE activities(
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    occurs_at TIMESTAMP NOT NULL,
    trip_id UUID,
    FOREIGN KEY (trip_id) REFERENCES trips(id) ON DELETE CASCADE
);

-- H2 Database
--CREATE TABLE activities(
--    id UUID DEFAULT RANDOM_UUID() PRIMARY KEY,
--    title VARCHAR(255) NOT NULL,
--    occurs_at TIMESTAMP NOT NULL,
--    trip_id UUID,
--    FOREIGN KEY (trip_id) REFERENCES trips(id) ON DELETE CASCADE
--);