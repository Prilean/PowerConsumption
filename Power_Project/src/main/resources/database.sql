CREATE TABLE users (
    user_id BIGSERIAL PRIMARY KEY, 
    username VARCHAR(255) NOT NULL,            
    password VARCHAR(255) NOT NULL,         
    role VARCHAR(255) NOT NULL,                
    enabled BOOLEAN NOT NULL                   
);

CREATE TABLE "type" (
    type_id BIGSERIAL PRIMARY KEY,
    "name" VARCHAR(255) NOT NULL
);

CREATE TABLE powerconsumption (
    id BIGSERIAL PRIMARY KEY,
    "date" VARCHAR(255),
    hour VARCHAR(255),
    consumption BIGINT,
    "type" BIGINT,
    user_id BIGINT,
    FOREIGN KEY ("type") REFERENCES "type"(type_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE appliance (
    id BIGSERIAL PRIMARY KEY,
    "type" BIGINT,
    active BOOLEAN,
    user_id BIGINT,
    FOREIGN KEY ("type") REFERENCES "type"(type_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

