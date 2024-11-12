CREATE SEQUENCE IF NOT EXISTS order_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE orders
(
    id            INTEGER                     NOT NULL,
    product_name  VARCHAR(255)                NOT NULL,
    product_price DECIMAL                     NOT NULL,
    quantity      INTEGER                     NOT NULL,
    order_status  SMALLINT                    NOT NULL,
    created_at    TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at    TIMESTAMP WITHOUT TIME ZONE,
    version       INTEGER,
    CONSTRAINT pk_orders PRIMARY KEY (id)
);