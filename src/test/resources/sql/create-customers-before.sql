DELETE FROM customers;

INSERT INTO customers(customer_id, address, company_name, created_at, contact_name, phone_number)
VALUES(1, 'Test Address 1', 'Test Company 1', CURRENT_TIMESTAMP, 'Test Name 1', 95734434),
      (2, 'Test Address 2', 'Test Company 2', CURRENT_TIMESTAMP, 'Test Name 2', 65126311);