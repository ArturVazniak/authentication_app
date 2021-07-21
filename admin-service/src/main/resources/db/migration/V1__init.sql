CREATE TABLE admins
(
    id                  BIGSERIAL                PRIMARY KEY,
    name                VARCHAR(50)              NOT NULL                 UNIQUE,
    role                VARCHAR(20)              NOT NULL
);


CREATE TABLE tour_operator
(
    id                  BIGSERIAL                 PRIMARY KEY,
    name                VARCHAR(50)               NOT NULL                UNIQUE,
    country             VARCHAR(50)               NOT NULL,
    address             VARCHAR(50)               NOT NULL,
    role                VARCHAR(20)               NOT NULL
);

CREATE TABLE hotel
(
    id                  BIGSERIAL                 PRIMARY KEY,
    name                VARCHAR(50)               NOT NULL,
    address             VARCHAR(50)               NOT NULL,
    tour_operator_id    BIGSERIAL,

    FOREIGN KEY(tour_operator_id) REFERENCES tour_operator(id)
);

CREATE TABLE flight
(
    id                  BIGSERIAL                 PRIMARY KEY,
    departure_place     VARCHAR(50)               NOT NULL,
    arrival_place       VARCHAR(50)               NOT NULL,
    departure_date      TIMESTAMP                 NOT NULL          DEFAULT (now() AT TIME ZONE 'utc'),
    arrival_date        TIMESTAMP                 NOT NULL          DEFAULT (now() AT TIME ZONE 'utc'),
    tour_operator_id    BIGSERIAL,

    FOREIGN KEY(tour_operator_id) REFERENCES tour_operator(id)
);