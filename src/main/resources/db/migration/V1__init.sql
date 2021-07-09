CREATE TABLE roles
(
    id                  SERIAL                  PRIMARY KEY,
    name           VARCHAR(20)             NOT NULL
);

CREATE TABLE users
(
    id                  BIGSERIAL               PRIMARY KEY,
    name                VARCHAR(50)             NOT NULL                 UNIQUE,
    password            VARCHAR(255)            NOT NULL,
    email               VARCHAR(50)             NOT NULL                 UNIQUE,
    enabled             BOOLEAN                 NOT NULL                 DEFAULT FALSE
);

CREATE TABLE user_roles
(
    user_id             BIGINT                  NOT NULL,
    role_id             INT                     NOT NULL,

    PRIMARY KEY(user_id, role_id),

    FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY(role_id) REFERENCES roles(id)
);

CREATE TABLE refreshtoken
(
    id                 BIGSERIAL                   PRIMARY KEY,
    user_id               BIGINT,
    token               VARCHAR(255),
    expiry_date          DATE,

        FOREIGN KEY(user_id) REFERENCES users(id)
);
