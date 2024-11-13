CREATE TABLE IF NOT EXISTS orders
(
    id            BIGSERIAL PRIMARY KEY       NOT NULL,
    product_name  VARCHAR(255)                NOT NULL,
    product_id    INTEGER                     NOT NULL,
    product_price INTEGER                     NOT NULL,
    quantity      INTEGER                     NOT NULL,
    order_status  VARCHAR(225)                NOT NULL,
    created_at    TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP WITHOUT TIME ZONE          DEFAULT CURRENT_TIMESTAMP,
    version       INTEGER                     NOT NULL
);

