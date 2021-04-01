CREATE TABLE "order" (
    id UUID PRIMARY KEY DEFAULT random_uuid(),
    product String,
    timestamp TIMESTAMP DEFAULT current_timestamp()
);

CREATE TABLE personalisation (
    id UUID PRIMARY KEY DEFAULT random_uuid(),
    personalisation_type INT8,
    product_id UUID REFERENCES "order"(id) ON DELETE CASCADE,
    personalisation_value String
);

INSERT INTO "order" (id, product) VALUES ('59d9ed36-9166-11eb-b41e-acde48001122', "test1");
INSERT INTO personalisation (personalisation_type, product_id, personalisation_value) VALUES (0, '59d9ed36-9166-11eb-b41e-acde48001122', "test value 1");
INSERT INTO "order" (id, product) VALUES ('6f0d87e4-9166-11eb-b41e-acde48001122', "test2");
INSERT INTO personalisation (personalisation_type, product_id, personalisation_value) VALUES (1, '6f0d87e4-9166-11eb-b41e-acde48001122', "test value 2");
