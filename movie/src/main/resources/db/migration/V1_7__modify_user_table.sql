ALTER TABLE user_detail ADD IF NOT EXISTS created_at date DEFAULT now();
ALTER TABLE user_detail ADD IF NOT EXISTS updated_at date DEFAULT now();
