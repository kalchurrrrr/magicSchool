package com.hogwarts.magicSchool.repository;

import com.hogwarts.magicSchool.model.Faculty;
import com.hogwarts.magicSchool.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student, Long> {
    boolean existsByNameAndAge(String name, int age);

    Collection<Student> findByAge(int age);
    Collection<Student> findByAgeBetween(int min, int max);
    Collection<Student> findByFaculty(Faculty faculty);
}
