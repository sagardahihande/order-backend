CREATE TABLE IF NOT EXISTS orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(255) NOT NULL,
    items JSON NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL,
    order_time TIMESTAMP NOT NULL,
    processed_time TIMESTAMP NULL,
    status VARCHAR(50) NOT NULL
);
