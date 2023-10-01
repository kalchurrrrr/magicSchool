package com.hogwarts.magicSchool.repository;

import com.hogwarts.magicSchool.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Map;

public interface StudentRepository extends JpaRepository<Student, Long> {
    boolean existsByNameAndAge(String name, int age);

    List<Student> findByAge(int age);

    Map<Long, Student> findAllStudents();
}
