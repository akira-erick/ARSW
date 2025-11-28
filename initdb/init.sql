CREATE TABLE IF NOT EXISTS order_table (
    id SERIAL PRIMARY KEY,
    customer_name VARCHAR(100) NOT NULL,
    item INT NOT NULL,
    quantity INT NOT NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    order_status VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS payment_table (
    id SERIAL PRIMARY KEY,
    order_id INT NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    payment_status VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS stock_table (
    id SERIAL PRIMARY KEY,
    item VARCHAR(100) NOT NULL,
    quantity INT NOT NULL
);

ALTER TABLE stock_table
ADD CONSTRAINT quantity_non_negative CHECK (quantity >= 0);

INSERT INTO stock_table (item, quantity) VALUES
('Laptop', 50),
('Smartphone', 100),
('Headphones', 200);