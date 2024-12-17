CREATE TABLE authorities
(
    id      VARCHAR(255) NOT NULL,
    name    VARCHAR(255),
    user_id VARCHAR(255),
    CONSTRAINT pk_authorities PRIMARY KEY (id)
);

CREATE TABLE users
(
    id         VARCHAR(255) NOT NULL,
    username   VARCHAR(255),
    first_name VARCHAR(255),
    last_name  VARCHAR(255),
    password   VARCHAR(255),
    CONSTRAINT pk_users PRIMARY KEY (id)
);

CREATE TABLE users_authorities
(
    user_id        VARCHAR(255) NOT NULL,
    authorities_id VARCHAR(255) NOT NULL
);

ALTER TABLE users_authorities
    ADD CONSTRAINT uc_users_authorities_authorities UNIQUE (authorities_id);

ALTER TABLE authorities
    ADD CONSTRAINT FK_AUTHORITIES_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE users_authorities
    ADD CONSTRAINT fk_useaut_on_authority FOREIGN KEY (authorities_id) REFERENCES authorities (id);

ALTER TABLE users_authorities
    ADD CONSTRAINT fk_useaut_on_user FOREIGN KEY (user_id) REFERENCES users (id);