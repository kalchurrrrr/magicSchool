package com.hogwarts.magicSchool.service;

import com.hogwarts.magicSchool.exceptions.CreateStudentException;
import com.hogwarts.magicSchool.model.Student;
import com.hogwarts.magicSchool.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(String name, int age) {
        if (studentRepository.existsByNameAndAge(name, age)) {
            throw new CreateStudentException("Студент с таким именем и возрастом уже существует");
        }
        Student student = new Student();
        student.setName(name);
        student.setAge(age);
        return studentRepository.save(student);
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public void updateStudent(Long id, String name, int age) {
        Student student = getStudentById(id);
        if (student != null) {
            student.setName(name);
            student.setAge(age);
            studentRepository.save(student);
        }
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public List<Student> filterStudentsByAge(int age) {
        return studentRepository.findByAge(age);
    }

    public Map<Long, Student> getAllStudents() {
        return studentRepository.findAllStudents();
    }
}