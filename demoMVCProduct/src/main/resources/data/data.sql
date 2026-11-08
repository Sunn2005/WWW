CREATE TABLE Product (
                         id VARCHAR(20) PRIMARY KEY,
                         name VARCHAR(100) NOT NULL,
                         price DOUBLE NOT NULL
);

INSERT INTO Product (id, name, price)
VALUES
    ('P001', 'iPhone 15', 22000000),
    ('P002', 'Samsung Galaxy S24', 20000000),
    ('P003', 'MacBook Air M2', 25000000),
    ('P004', 'Dell Inspiron 15', 18000000),
    ('P005', 'AirPods Pro 2', 5500000),
    ('P006', 'Sony WH-1000XM5', 8500000),
    ('P007', 'iPad Air M2', 16000000),
    ('P008', 'Xiaomi Redmi Note 13', 6500000),
    ('P009', 'Apple Watch Series 9', 10000000),
    ('P010', 'Logitech MX Master 3S', 2500000);