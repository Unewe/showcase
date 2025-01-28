CREATE SEQUENCE IF NOT EXISTS product_id_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE products
(
    id        BIGINT                                        NOT NULL,
    name      VARCHAR(200)                                  NOT NULL,
    weight    INTEGER                                       NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW()    NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_products PRIMARY KEY (id)
);
