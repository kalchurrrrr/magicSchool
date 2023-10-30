-- Ограничение на возраст студента
ALTER TABLE Student
ADD CONSTRAINT age_check CHECK (age >= 16);

-- Ограничение на уникальность имен студентов
ALTER TABLE Student
ADD CONSTRAINT name_unique UNIQUE (name);

-- Ограничение на уникальную пару "название факультета" - "цвет факультета"
ALTER TABLE Faculty
ADD CONSTRAINT name_color_unique UNIQUE (name, color);

-- По умолчанию присваиваем возраст 20 годам, если он не указан при создании студента
ALTER TABLE Student
ALTER COLUMN age SET DEFAULT 20;