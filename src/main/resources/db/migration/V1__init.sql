CREATE TABLE roles
(
    id                  SERIAL                   PRIMARY KEY,
    name                VARCHAR(20)              NOT NULL
);

CREATE TABLE users
(
    id                  BIGSERIAL                PRIMARY KEY,
    name                VARCHAR(50)              NOT NULL                 UNIQUE,
    password            VARCHAR(255)             NOT NULL,
    email               VARCHAR(50)              NOT NULL                 UNIQUE,
    enabled             BOOLEAN                  NOT NULL                 DEFAULT FALSE,
    mfa                 BOOLEAN                  NOT NULL                 DEFAULT FALSE
);

CREATE TABLE user_roles
(
    user_id             BIGINT                    NOT NULL,
    role_id             INT                       NOT NULL,

    PRIMARY KEY(user_id, role_id),

    FOREIGN KEY(user_id) REFERENCES users(id)     ON DELETE CASCADE,
    FOREIGN KEY(role_id) REFERENCES roles(id)
);

CREATE TABLE refresh_token
(
    id                 BIGSERIAL                   PRIMARY KEY,
    user_id            BIGINT,
    token              VARCHAR(255)                NOT NULL,
    expiry_date        TIMESTAMP                   NOT NULL          DEFAULT (now() AT TIME ZONE 'utc'),
    fingerprint        VARCHAR(255),

    FOREIGN KEY(user_id) REFERENCES users(id)
);

CREATE TABLE account_code
(
    id                 BIGSERIAL                   PRIMARY KEY,
    code               VARCHAR(255),
    two_factor_token   VARCHAR(255),
    create_at          TIMESTAMP                   NOT NULL          DEFAULT (now() AT TIME ZONE 'utc'),
    user_id            BIGINT,
    FOREIGN KEY(user_id) REFERENCES users(id)
);
