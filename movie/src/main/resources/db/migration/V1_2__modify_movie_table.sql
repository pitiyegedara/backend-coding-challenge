ALTER TABLE movie DROP COLUMN IF EXISTS cast_list;
ALTER TABLE movie ADD IF NOT EXISTS movie_language varchar(255);;
ALTER TABLE movie ADD IF NOT EXISTS duration_in_minutes varchar(255);
ALTER TABLE movie ADD IF NOT EXISTS created_at date DEFAULT now();
ALTER TABLE movie ADD IF NOT EXISTS updated_at date DEFAULT now();
