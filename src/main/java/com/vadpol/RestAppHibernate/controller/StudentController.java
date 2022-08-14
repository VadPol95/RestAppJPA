package com.vadpol.RestAppHibernate.controller;

import com.vadpol.RestAppHibernate.entity.Student;
import com.vadpol.RestAppHibernate.repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class StudentController {
    StudentRepo studentRepo;

    @Autowired
    public StudentController(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }

    @GetMapping("/student")
    public ResponseEntity<List<Student>> findAll(@RequestParam(required = false) String name) {
        try {
            List<Student> studentList = new ArrayList<>();
            if (name == null)
                studentRepo.findAll().forEach(studentList::add);
            else
                studentRepo.findByName(name).forEach(studentList::add);
            if (studentList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(studentList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/student/{id}")
    public ResponseEntity<Student> findById(@PathVariable int id) {
        Optional<Student> student = studentRepo.findById(id);
        if (student.isPresent()) {
            return new ResponseEntity<>(studentRepo.getReferenceById(id), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PostMapping("/student")
    public ResponseEntity<Student> save(@RequestBody Student student) {
        try {
            return new ResponseEntity<>(studentRepo.save(student), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/student/{id}")
    public ResponseEntity<Student> update(@RequestBody Student student) {
        try {
            return new ResponseEntity<>(studentRepo.save(student), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/student/{id}")
    public ResponseEntity<Student> delete(@PathVariable int id) {
        try {
            Optional<Student> student = studentRepo.findById(id);
            if (student.isPresent()) {
                studentRepo.delete(studentRepo.getById(id));
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
