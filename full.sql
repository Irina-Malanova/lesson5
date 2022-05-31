BEGIN;

DROP TABLE IF EXISTS products CASCADE;
CREATE TABLE products (id bigserial PRIMARY KEY, title VARCHAR(255), cost int);
INSERT INTO products (title, cost) VALUES
('Батон нарезной', 56),
('Сыр костромской', 567),
('Свинина лопатка', 340),
('Фарш домашний', 299),
('Яблоки сезонные', 89);

COMMIT;