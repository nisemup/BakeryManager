DELETE FROM orders;
DELETE FROM customers;
DELETE FROM users;
DELETE FROM payment_method;

INSERT INTO customers(customer_id, address, company_name, created_at, contact_name, phone_number)
VALUES(1, 'Test Address 1', 'Test Company 1', CURRENT_TIMESTAMP, 'Test Name 1', 95734434),
      (2, 'Test Address 2', 'Test Company 2', CURRENT_TIMESTAMP, 'Test Name 2', 65126311);

INSERT INTO users(id, avatar, birthday, created_at, email, first_name, last_name, password, role, status)
VALUES(1, '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'test1@mail.test',
       'Name1', 'LastName1', '$2y$10$uLbzG2Ls.2O1s0fbqoqjl.3HnEg5cIwVW.yzEEUG4g2BtgTVxDO2m', 'USER', 'ACTIVE'),
      (2, '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'test2@mail.test',
       'Name2', 'LastName2', '$2y$10$uLbzG2Ls.2O1s0fbqoqjl.3HnEg5cIwVW.yzEEUG4g2BtgTVxDO2m', 'USER', 'ACTIVE');

INSERT INTO payment_method(id, allowed, type) VALUES(1, true, 'Test payment 1'), (2, true, 'Test payment 2');

INSERT INTO orders(id, delivered, delivered_date, order_date, required_date, transaction_status, customer_id, payment_id, user_id)
VALUES(1, false, NULL, '2021-1-1 12:30', '2021-1-1', 'INPROGRESS', 1, 1, 1),
      (2, true, '2022-2-2 12:30:00', '2022-2-2 12:35', '2022-2-2', 'INPROGRESS', 2, 2, 2);