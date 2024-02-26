package com.example.school.repository;

import com.example.school.model.*;

import java.util.*;

public interface StudentRepository {
    ArrayList<Student> getStudents();

    Student addStudent(Student newStudent);

    Student getStudentById(int studentId);

    Student updateStudent(int studentId, Student student);

    void deleteStudent(int studentId);
}