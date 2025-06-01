CREATE TABLE IF NOT EXISTS user_detail (
    id UUID NOT NULL,
    user_name VARCHAR(30) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL,
    address VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);



