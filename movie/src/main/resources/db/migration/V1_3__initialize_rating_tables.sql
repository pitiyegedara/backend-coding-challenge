CREATE TABLE IF NOT EXISTS rating (
    id UUID NOT NULL,
    movie_id UUID NOT NULL,
    user_id UUID NOT NULL,
    rate INT NOT NULL,
    details VARCHAR(500),
    PRIMARY KEY (id),
    FOREIGN KEY (movie_id) REFERENCES movie(id)
);



