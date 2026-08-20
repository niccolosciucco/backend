CREATE TABLE risultati_gara
(
    id          UUID PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    circuito_id UUID         NOT NULL REFERENCES circuiti (id),
    event_date  DATE         NOT NULL,
    laps        INTEGER      NOT NULL
);

CREATE TABLE pilota_risultati
(
    id                UUID PRIMARY KEY,
    risultato_gara_id UUID        NOT NULL REFERENCES risultati_gara (id) ON DELETE CASCADE,
    pilota_id         UUID        NOT NULL REFERENCES piloti (id),
    race_position     INTEGER,
    gap_seconds       DOUBLE PRECISION,
    status            VARCHAR(20) NOT NULL,
    fastest_lap       BOOLEAN     NOT NULL DEFAULT FALSE
);