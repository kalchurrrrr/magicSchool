package com.hogwarts.magicSchool.service;

import com.hogwarts.magicSchool.exceptions.CreateStudentException;
import com.hogwarts.magicSchool.model.Student;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class StudentService {
    private final Map<Long, Student> students;
    private Long counter;

    public StudentService() {
        this.students = new HashMap<>();
        this.counter = 1L;
    }

    public Student createStudent(String name, int age) {
        for (Student student : students.values()) {
            if (student.getName().equals(name) && student.getAge() == age) {
                throw new CreateStudentException("Студент с таким именем и возрастом уже существует");
            }
        }
        Student student = new Student(counter, name, age);
        students.put(counter, student);
        counter++;
        return student;
    }

    public Student getStudentById(Long id) {
        return students.get(id);
    }

    public void updateStudent(Long id, String name, int age) {
        if (students.containsKey(id)) {
            Student student = students.get(id);
            student.setName(name);
            student.setAge(age);
        }
    }

    public void deleteStudent(Long id) {
        students.remove(id);
    }
    public List<Student> filterStudentsByAge(int age) {
        List<Student> filteredStudents = new ArrayList<>();
        for (Student student : students.values()) {
            if (student.getAge() == age) {
                filteredStudents.add(student);
            }
        }
        return filteredStudents;
    }
    public Map<Long, Student> getAllStudents() {
        return students;
    }
}

