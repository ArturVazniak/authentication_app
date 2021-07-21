INSERT INTO roles (name)
VALUES
('ROLE_ADMIN'),
('ROLE_USER'),
('ROLE_OPERATOR');


INSERT INTO users (name, password, email, deleted)
VALUES
('Admin',         '$2y$12$LQEToPGaUMTaYZZBUZ.tWu2JBtRZblwvX8TnrWAlHEmJZYv5LnYqa',    'admin@mail.ru',        false ),
('Guest',         '$2y$12$kEszbZgfbqD/24/4fVWo1eVjXUA9SemyTbEIdCyUB7CxAwwqC0kCe',    'guest@mail.ru',        false ),
('TourOperator',  '$2y$12$71rwVJ9QdiTwrQLakNLRHunw8s4FHR6USorLp6eYraE.gcTG0IqTC',    'tourOperator@mail.ru', false );

INSERT INTO user_roles (user_id, role_id)
VALUES
(1, 1),
(2, 2),
(3, 3);