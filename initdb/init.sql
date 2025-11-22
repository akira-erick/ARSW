CREATE TABLE IF NOT EXISTS order (
    id SERIAL PRIMARY KEY,
    customer_name VARCHAR(100) NOT NULL,
    item VARCHAR(100) NOT NULL,
    quantity INT NOT NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO order (customer_name, item, quantity) VALUES
('Alice', 'Laptop', 1),
('Bob', 'Smartphone', 2),
('Charlie', 'Headphones', 3);

CREATE TABLE IF NOT EXISTS payment (
    id SERIAL PRIMARY KEY,
    order_id INT NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    status VARCHAR(50) NOT NULL
);

INSERT INTO payment (order_id, amount, status) VALUES
(1, 999.99, 'Completed'),
(2, 499.98, 'Pending'),
(3, 149.97, 'Completed');

CREATE TABLE IF NOT EXISTS stock (
    id: SERIAL PRIMARY KEY,
    item VARCHAR(100) NOT NULL,
    quantity INT NOT NULL
);

INSERT INTO stock (item, quantity) VALUES
('Laptop', 50),
('Smartphone', 100),
('Headphones', 200);