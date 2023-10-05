package com.hogwarts.magicSchool.controller;

import com.hogwarts.magicSchool.model.Faculty;
import com.hogwarts.magicSchool.model.Student;
import com.hogwarts.magicSchool.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/")
    public Student createStudent(@RequestParam String name, @RequestParam int age) {
        return studentService.createStudent(name, age);
    }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    @PutMapping("/{id}")
    public void updateStudent(@PathVariable Long id, @RequestParam String name, @RequestParam int age) {
        studentService.updateStudent(id, name, age);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }

    @GetMapping("/filterByAge")
    public Collection<Student> filterStudentsByAge(@RequestParam int age) {
        return studentService.filterStudentsByAge(age);
    }

    @GetMapping("/students/all")
    public Collection<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/students")
    public Collection<Student> getStudentsByAgeRange(@RequestParam int min, @RequestParam int max) {
        return studentService.getStudentsByAgeRange(min, max);
    }
    @GetMapping("/{id}/faculty")
    public ResponseEntity<Faculty> getStudentFaculty(@PathVariable Long id) {
        Student student = studentService.getStudentById(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        Faculty faculty = student.getFaculty();
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

}
