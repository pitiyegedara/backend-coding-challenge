ALTER TABLE rating ADD IF NOT EXISTS created_at date DEFAULT now();
ALTER TABLE rating ADD IF NOT EXISTS updated_at date DEFAULT now();
