CREATE TABLE IF NOT EXISTS movie (
    id UUID NOT NULL,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(500) NOT NULL,
    producer VARCHAR(255) NOT NULL,
    cover_image VARCHAR(255),
    cast_list VARCHAR(1000),
    PRIMARY KEY (id)
    );



