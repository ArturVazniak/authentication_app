INSERT INTO admins (name, role)
VALUES
('Admin', 'ROLE_ADMIN');

INSERT INTO tour_operator (name, country, address, role)
VALUES
('Egor', 'Minsk', 'Frunze 22-1-1', 'ROLE_OPERATOR');

INSERT INTO hotel (name, address, tour_operator_id)
VALUES
('Laguna Hotel', 'Egypt', 1);

INSERT INTO flight (departure_place, arrival_place, tour_operator_id)
VALUES
('Minsk', 'Egypt', 1);

