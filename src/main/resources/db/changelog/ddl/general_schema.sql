-- --------------------------------------------------------
--                     users
-- --------------------------------------------------------
CREATE TABLE users
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    firstname    VARCHAR(255)          NULL,
    lastname     VARCHAR(255)          NULL,
    email        VARCHAR(255)          NOT NULL,
    phone_number VARCHAR(255)          NULL,
    password     VARCHAR(255)          NULL,
    created_at   datetime              NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE users
    ADD CONSTRAINT uc_users_email UNIQUE (email);

-- --------------------------------------------------------
--                     stations
-- --------------------------------------------------------
CREATE TABLE stations
(
    id   BIGINT AUTO_INCREMENT NOT NULL,
    name VARCHAR(255)          NULL,
    CONSTRAINT pk_stations PRIMARY KEY (id)
);

-- --------------------------------------------------------
--                     images
-- --------------------------------------------------------
CREATE TABLE images
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    name       VARCHAR(255)          NULL,
    type       VARCHAR(255)          NULL,
    image      BLOB                  NOT NULL,
    station_id BIGINT                NULL,
    CONSTRAINT pk_images PRIMARY KEY (id)
);
ALTER TABLE images
    ADD CONSTRAINT FK_IMAGES_ON_STATION FOREIGN KEY (station_id) REFERENCES stations (id);

-- --------------------------------------------------------
--                     trains
-- --------------------------------------------------------
CREATE TABLE trains
(
    id     BIGINT AUTO_INCREMENT NOT NULL,
    number VARCHAR(255)          NULL,
    type   INT                   NULL,
    CONSTRAINT pk_trains PRIMARY KEY (id)
);

-- --------------------------------------------------------
--                     routes
-- --------------------------------------------------------
CREATE TABLE routes
(
    id                   BIGINT AUTO_INCREMENT NOT NULL,
    departure_time       datetime              NULL,
    arrival_time         datetime              NULL,
    price                DOUBLE                NULL,
    created_at           datetime              NULL,
    departure_station_id BIGINT                NOT NULL,
    arrival_station_id   BIGINT                NOT NULL,
    train_id             BIGINT                NOT NULL,
    CONSTRAINT pk_routes PRIMARY KEY (id)
);

ALTER TABLE routes
    ADD CONSTRAINT FK_ROUTES_ON_ARRIVAL_STATION FOREIGN KEY (arrival_station_id) REFERENCES stations (id);

ALTER TABLE routes
    ADD CONSTRAINT FK_ROUTES_ON_DEPARTURE_STATION FOREIGN KEY (departure_station_id) REFERENCES stations (id);

ALTER TABLE routes
    ADD CONSTRAINT FK_ROUTES_ON_TRAIN FOREIGN KEY (train_id) REFERENCES trains (id);

-- --------------------------------------------------------
--                     tickets
-- --------------------------------------------------------
CREATE TABLE tickets
(
    id       BIGINT AUTO_INCREMENT NOT NULL,
    user_id  BIGINT                NOT NULL,
    route_id BIGINT                NOT NULL,
    CONSTRAINT pk_tickets PRIMARY KEY (id)
);

ALTER TABLE tickets
    ADD CONSTRAINT FK_TICKETS_ON_ROUTE FOREIGN KEY (route_id) REFERENCES routes (id);

ALTER TABLE tickets
    ADD CONSTRAINT FK_TICKETS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);