DELETE FROM categories;

INSERT INTO categories(id, active, description, name, picture)
VALUES(1, true, 'Test Description 1', 'Test Name 1', ''), (2, true, 'Test Description 2', 'Test Name 2', '');