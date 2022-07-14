CREATE TABLE roles
(
    id   BIGINT AUTO_INCREMENT NOT NULL,
    name VARCHAR(255)          NOT NULL,
    CONSTRAINT pk_roles PRIMARY KEY (id)
);

INSERT INTO roles (name)
VALUES ('ROLE_ADMIN'),
       ('ROLE_USER');

CREATE TABLE users_roles
(
    role_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT pk_users_roles PRIMARY KEY (role_id, user_id)
);

ALTER TABLE users_roles
    ADD CONSTRAINT fk_userol_on_role_entity FOREIGN KEY (role_id) REFERENCES roles (id);

ALTER TABLE users_roles
    ADD CONSTRAINT fk_userol_on_user_entity FOREIGN KEY (user_id) REFERENCES users (id);

CREATE TABLE access_tokens
(
    id                      BIGINT AUTO_INCREMENT NOT NULL,
    access_token            TEXT                  NOT NULL,
    refresh_token           TEXT                  NOT NULL,
    user_email              VARCHAR(255)          NOT NULL,
    is_active               BIT(1)                NOT NULL,
    refresh_expiration_date date                  NOT NULL,
    access_expiration_date  date                  NOT NULL,
    user_id                 BIGINT                NOT NULL,
    CONSTRAINT pk_access_tokens PRIMARY KEY (id)
);
