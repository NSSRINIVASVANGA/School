package com.example.school.controller;

import com.example.school.model.*;
import java.util.*;
import com.example.school.service.StudentH2Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {
    @Autowired
    public StudentH2Service studentSrevice;

    @GetMapping("/students")
    public ArrayList<Student> getStudents() {
        return studentSrevice.getStudents();
    }

    @PostMapping("/students")
    public Student addStudent(@RequestBody Student newStudent) {
        return studentSrevice.addStudent(newStudent);
    }

    @PostMapping("/students/bulk")
    public String addMultipleStudents(@RequestBody ArrayList<Student> studentsList) {
        return studentSrevice.addMultipleStudents(studentsList);
    }

    @GetMapping("/students/{studentId}")
    public Student getStudentById(@PathVariable("studentId") int studentId) {
        return studentSrevice.getStudentById(studentId);
    }

    @PutMapping("/students/{studentId}")
    public Student updateStudent(@PathVariable("studentId") int studentId, @RequestBody Student student) {
        return studentSrevice.updateStudent(studentId, student);
    }

    @DeleteMapping("/students/{studentId}")
    public void deleteStudent(@PathVariable("studentId") int studentId){
        studentSrevice.deleteStudent(studentId);
    }
}