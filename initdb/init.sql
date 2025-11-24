CREATE TABLE IF NOT EXISTS order_table (
    id SERIAL PRIMARY KEY,
    customer_name VARCHAR(100) NOT NULL,
    item INT NOT NULL,
    quantity INT NOT NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    order_status VARCHAR(50) NOT NULL
);

INSERT INTO order_table (customer_name, item, quantity, order_status) VALUES
('Alice', 1, 1, 'Processing'),
('Bob', 2, 2, 'Shipped'),
('Charlie', 3, 3, 'Delivered');

CREATE TABLE IF NOT EXISTS payment_table (
    id SERIAL PRIMARY KEY,
    order_id INT NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    payment_status VARCHAR(50) NOT NULL
);

INSERT INTO payment_table (order_id, amount, payment_status) VALUES
(1, 999.99, 'Completed'),
(2, 499.98, 'Pending'),
(3, 149.97, 'Completed');

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