CREATE TABLE shards (
    id UUID PRIMARY KEY,
    shard_name VARCHAR(100) NOT NULL,
    status VARCHAR(20) NOT NULL,
    database_url VARCHAR(500) NOT NULL,
    created_at TIMESTAMP NOT NULL
);

ALTER TABLE shards
ALTER COLUMN status TYPE VARCHAR(50);