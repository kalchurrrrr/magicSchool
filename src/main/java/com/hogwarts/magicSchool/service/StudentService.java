package com.hogwarts.magicSchool.service;

import com.hogwarts.magicSchool.exceptions.CreateStudentException;
import com.hogwarts.magicSchool.model.Student;
import com.hogwarts.magicSchool.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Collection;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);


    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(String name, int age) {
        logger.info("Был вызван метод для создания студента");

        if (studentRepository.existsByNameAndAge(name, age)) {
            throw new CreateStudentException("Студент с таким именем и возрастом уже существует");
        }

        Student student = new Student();
        student.setName(name);
        student.setAge(age);

        Student createdStudent = studentRepository.save(student);
        logger.info("Новый студент создан: {}", createdStudent);

        return createdStudent;
    }

    public Student getStudentById(Long id) {
        logger.info("Был вызван метод для получения студента по идентификатору: {}", id);

        Optional<Student> studentOptional = studentRepository.findById(id);
        Student student = studentOptional.orElse(null);

        if (student == null) {
            logger.warn("Студент не найден по идентификатору: {}", id);
        } else {
            logger.info("Восстановленный студент по идентификатору: {}", id);
        }

        return student;
    }

    public void updateStudent(Long id, String name, int age) {
        logger.info("Был вызван метод для обновления студента. Идентификатор студента: {}", id);

        Student student = getStudentById(id);

        if (student != null) {
            logger.info("Обновлённый студент. Идентификатор студента: {}, Новое имя: {}, Новый возраст: {}", id, name, age);

            student.setName(name);
            student.setAge(age);
            studentRepository.save(student);

            logger.info("Студент успешно обновился. Идентификатор студента: {}", id);
        } else {
            logger.warn("Студент не найден по идентификатору: {}. Операция обновления пропущена.", id);
        }
    }

    public void deleteStudent(Long id) {
        logger.info("Был вызван метод для удаления студента. Идентификатор студента: {}", id);

        try {
            studentRepository.deleteById(id);
            logger.info("Студент успешно удален. Идентификатор студента: {}", id);
        } catch (Exception e) {
            logger.error("Произошла ошибка при удалении студента. Идентификатор студента: {}", id, e);
        }
    }

    public Collection<Student> filterStudentsByAge(int age) {
        logger.info("Был вызван метод для фильтрации студентов по возрасту. Возраст: {}", age);

        Collection<Student> filteredStudents = studentRepository.findByAge(age);

        logger.info("Найдено {} студентов по возрасту {}", filteredStudents.size(), age);

        return filteredStudents;
    }

    public Collection<Student> getAllStudents() {
        logger.info("Был вызван метод для получения всех студентов");

        Collection<Student> allStudents = studentRepository.findAll();

        logger.info("Получено {} студентов", allStudents.size());

        return allStudents;
    }
    public Collection<Student> getStudentsByAgeRange(int min, int max) {
        logger.info("Был вызван метод для получения студентов по диапазону возраста. Минимальный возраст: {}, Максимальный возраст: {}", min, max);

        Collection<Student> filteredStudents = studentRepository.findByAgeBetween(min, max);

        logger.info("Найдено {} студентов соответствующих диапазону возраста {}-{}", filteredStudents.size(), min, max);

        return filteredStudents;
    }
}