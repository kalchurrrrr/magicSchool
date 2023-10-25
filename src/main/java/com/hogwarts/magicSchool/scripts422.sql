-- Создание таблицы Person
CREATE TABLE Person (
  id SERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  age INT,
  has_license BOOLEAN
);

-- Создание таблицы Car
CREATE TABLE Car (
  id SERIAL PRIMARY KEY,
  brand VARCHAR(255),
  model VARCHAR(255),
  price DECIMAL(10,2)
);

-- Создание таблицы PersonCar (связь между Person и Car)
CREATE TABLE PersonCar (
  person_id INT,
  car_id INT,
  PRIMARY KEY (person_id, car_id),
  FOREIGN KEY (person_id) REFERENCES Person (id),
  FOREIGN KEY (car_id) REFERENCES Car (id)
);

-- JOIN-запрос для получения информации о студентах с названиями факультетов
SELECT Student.name, Student.age, Faculty.name AS faculty_name
FROM Student
JOIN Faculty ON Student.faculty_id = Faculty.id;

-- JOIN-запрос для получения информации только о студентах с аватарками
SELECT Student.name, Student.age
FROM Student
JOIN Avatar ON Student.avatar_id = Avatar.id;