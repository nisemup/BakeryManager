DELETE FROM products;
DELETE FROM categories;

INSERT INTO categories(id, active, description, name, picture) VALUES(1, true, '', 'Test Name 1', '');

INSERT INTO products(id, description, discount, discount_available, name, price, category_id)
VALUES(1, '', 0.0, false, 'Test Product 1', 15.0, 1), (2, '', 0.0, false, 'Test Product 2', 5.0, 1);