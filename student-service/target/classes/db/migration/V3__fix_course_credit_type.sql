ALTER TABLE courses
ALTER COLUMN credit TYPE INTEGER
  USING credit::integer;
