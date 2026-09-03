CREATE TABLE IF NOT EXISTS products (
    id          VARCHAR(36) PRIMARY KEY,
    name        VARCHAR(200) NOT NULL,
    description CLOB,
    price       DECIMAL(10, 2) NOT NULL
);

CREATE TABLE IF NOT EXISTS orders (
    id           VARCHAR(36) PRIMARY KEY,
    customer_id  VARCHAR(64) NOT NULL,
    status       VARCHAR(20) NOT NULL,
    total_amount DECIMAL(10, 2),
    created_at   TIMESTAMP NOT NULL
);
