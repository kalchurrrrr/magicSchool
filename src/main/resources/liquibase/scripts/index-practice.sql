-- liquibase formatted sql
-- changeset yourname:1
-- Создание индекса для поиска по имени студента
CREATE INDEX idx_student_name ON Student (name);

-- changeset yourname:2
-- Создание индекса для поиска по названию и цвету факультета
CREATE INDEX idx_faculty_name_color ON Faculty (name, color);