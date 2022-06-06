BEGIN;
DROP TABLE IF EXISTS orders CASCADE;
DROP TABLE IF EXISTS products CASCADE;
DROP TABLE IF EXISTS customers_details CASCADE;
DROP TABLE IF EXISTS customers CASCADE;


CREATE TABLE products (id bigserial PRIMARY KEY, title VARCHAR(255), cost int);
INSERT INTO products (title, cost) VALUES
('Батон нарезной', 56),
('Сыр костромской', 567),
('Свинина лопатка', 340),
('Фарш домашний', 299),
('Яблоки сезонные', 89);

CREATE TABLE customers_details (id bigserial PRIMARY KEY, email VARCHAR(255), city varchar(255));
INSERT INTO customers_details (email, city) VALUES
('terminator@gmail.com', 'California'),
('rembo@gmail.com', 'Atlanta'),
('corben_dallas@gmail.com', 'New York');

CREATE TABLE customers (id bigserial PRIMARY KEY, name VARCHAR(255), details_id bigint);
INSERT INTO customers (name, details_id) VALUES
('Customer1', 1),
('Customer2', 2),
('Customer3', 3);

CREATE TABLE orders (id bigserial PRIMARY KEY,  order_nummer int, customer_id bigint, product_id bigint, price int);
INSERT INTO orders (order_nummer, customer_id, product_id, price) VALUES
(1,1,1,56),
(1,1,2,567),
(2,1,2,567),
(2,1,3,340),
(1,2,3,340),
(1,2,4,299),
(1,3,4,299),
(1,3,5,99),
(1,3,1,56);
COMMIT;

