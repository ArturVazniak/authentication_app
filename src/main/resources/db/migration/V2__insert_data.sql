INSERT INTO roles (name)
VALUES
('ROLE_ADMIN'),
('ROLE_GUEST'),
('ROLE_OPERATOR');


INSERT INTO users (name, password, email, enabled)
VALUES
('Admin',         '$2y$12$LQEToPGaUMTaYZZBUZ.tWu2JBtRZblwvX8TnrWAlHEmJZYv5LnYqa',    'admin@mail.ru',        true),
('Guest',         '$2y$12$kEszbZgfbqD/24/4fVWo1eVjXUA9SemyTbEIdCyUB7CxAwwqC0kCe',    'guest@mail.ru',        true),
('TourOperator',  '$2y$12$71rwVJ9QdiTwrQLakNLRHunw8s4FHR6USorLp6eYraE.gcTG0IqTC',    'tourOperator@mail.ru', true);

INSERT INTO user_roles (user_id, role_id)
VALUES
(1, 1),
(2, 2),
(3, 3);