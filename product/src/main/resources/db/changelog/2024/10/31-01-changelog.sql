-- liquibase formatted sql

-- changeset ClementOwireku-Bogya:1730396680889-1
CREATE SEQUENCE IF NOT EXISTS product_sequence START WITH 1 INCREMENT BY 1;

-- changeset ClementOwireku-Bogya:1730396680889-2
CREATE TABLE products
(
    id         INTEGER      NOT NULL,
    name       VARCHAR(255) NOT NULL,
    price      DECIMAL      NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    version    INTEGER,
    CONSTRAINT pk_products PRIMARY KEY (id)
);

