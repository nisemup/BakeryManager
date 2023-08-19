DELETE FROM drivers;
DELETE FROM users;
DELETE FROM trucks;

INSERT INTO users(id, avatar, birthday, created_at, email, first_name, last_name, password, role, status)
VALUES(1, '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'test1@mail.test',
        'Name1', 'LastName1', '$2y$10$uLbzG2Ls.2O1s0fbqoqjl.3HnEg5cIwVW.yzEEUG4g2BtgTVxDO2m', 'USER', 'ACTIVE'),
      (2, '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'test2@mail.test',
       'Name2', 'LastName2', '$2y$10$uLbzG2Ls.2O1s0fbqoqjl.3HnEg5cIwVW.yzEEUG4g2BtgTVxDO2m', 'USER', 'ACTIVE'),
      (3, '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'test3@mail.test',
       'Name3', 'LastName3', '$2y$10$uLbzG2Ls.2O1s0fbqoqjl.3HnEg5cIwVW.yzEEUG4g2BtgTVxDO2m', 'USER', 'ACTIVE');

INSERT INTO trucks(id, brand, color, model, number_plate)
VALUES(1, 'Test brand 1', 'Test color 1', 'Test model 1', 'Test plate 1'),
      (2, 'Test brand 2', 'Test color 2', 'Test model 2', 'Test plate 2'),
      (3, 'Test brand 3', 'Test color 3', 'Test model 3', 'Test plate 3');

INSERT INTO drivers(id, license, truck_id, user_id) VALUES(1, 'Test license 1', 1, 1), (2, 'Test license 2', 2, 2);