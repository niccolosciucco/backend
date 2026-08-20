CREATE TABLE teams
(
    id           UUID PRIMARY KEY,
    name         VARCHAR(255) NOT NULL UNIQUE,
    base         VARCHAR(255) NOT NULL,
    principal    VARCHAR(255) NOT NULL,
    founded_year INTEGER      NOT NULL,
    color_hex    VARCHAR(7)   NOT NULL
);

CREATE TABLE piloti
(
    id          UUID PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    team_id     UUID         NOT NULL REFERENCES teams (id),
    nationality VARCHAR(3)   NOT NULL,
    number      INTEGER      NOT NULL
);

CREATE TABLE circuiti
(
    id                UUID PRIMARY KEY,
    name              VARCHAR(255)     NOT NULL UNIQUE,
    location          VARCHAR(255)     NOT NULL,
    country           VARCHAR(255)     NOT NULL,
    length_km         DOUBLE PRECISION NOT NULL,
    laps              INTEGER          NOT NULL,
    turns             INTEGER          NOT NULL,
    drs_zones         INTEGER          NOT NULL,
    lap_record_time   VARCHAR(255),
    lap_record_driver VARCHAR(255),
    lap_record_year   INTEGER,
    description       TEXT
);

CREATE TABLE eventi
(
    id          UUID PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    circuito_id UUID         NOT NULL REFERENCES circuiti (id),
    event_date  DATE         NOT NULL,
    status      VARCHAR(20)  NOT NULL
);

CREATE TABLE utenti
(
    id            UUID PRIMARY KEY,
    email         VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role          VARCHAR(20)  NOT NULL
);