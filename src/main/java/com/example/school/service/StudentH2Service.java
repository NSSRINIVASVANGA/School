package com.example.school.service;

import com.example.school.model.*;
import com.example.school.repository.*;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;

@Service
public class StudentH2Service implements StudentRepository {
    @Autowired
    private JdbcTemplate db;

    @Override
    public ArrayList<Student> getStudents() {
        List<Student> studentList = db.query("SELECT * FROM STUDENT", new StudentRowMapper());
        ArrayList<Student> students = new ArrayList<>(studentList);
        return students;
    }

    @Override
    public Student addStudent(Student newStudent) {
        db.update("INSERT INTO STUDENT(studentName,gender,standard) VALUES(?,?,?)", newStudent.getStudentName(),
                newStudent.getGender(), newStudent.getStandard());
        Student savedStudent = db.queryForObject(
                "SELECT * FROM STUDENT WHERE studentname LIKE ? AND gender LIKE ? AND standard = ?",
                new StudentRowMapper(), newStudent.getStudentName(), newStudent.getGender(), newStudent.getStandard());
        return savedStudent;
    }

    public String addMultipleStudents(ArrayList<Student> studentsList) {
        for (Student eachStudent : studentsList) {
            db.update("INSERT INTO STUDENT(studentName,gender,standard)VALUES(?,?,?)", eachStudent.getStudentName(),
                    eachStudent.getGender(), eachStudent.getStandard());
        }
        String responseMessage = String.format("Successfully added %d students", studentsList.size());
        return responseMessage;
    }

    @Override
    public Student getStudentById(int studentId) {
        try {
            Student student = db.queryForObject("SELECT * FROM STUDENT WHERE studentId = ?", new StudentRowMapper(),
                    studentId);
            return student;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Student updateStudent(int studentId, Student student) {
        if (student.getStudentName() != null) {
            db.update("UPDATE STUDENT SET studentName = ? WHERE studentId = ?", student.getStudentName(), studentId);
        }
        if (student.getGender() != null) {
            db.update("UPDATE STUDENT SET gender = ? WHERE studentId = ?", student.getGender(), studentId);
        }
        if (student.getStandard() > 0) {
            db.update("UPDATE STUDENT SET standard = ? WHERE studentId = ?", student.getStandard(), studentId);
        }
        return getStudentById(studentId);
    }

    @Override
    public void deleteStudent(int studentId){
        db.update("DELETE FROM STUDENT WHERE studentId = ?",studentId);
    }

}