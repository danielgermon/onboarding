CREATE TABLE "order" (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    product String,
    timestamp TIMESTAMP
);

CREATE TABLE personalisation (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    personalisation_type INT8,
    order_id UUID REFERENCES "order"(id) ON DELETE CASCADE,
    personalisation_value String
);