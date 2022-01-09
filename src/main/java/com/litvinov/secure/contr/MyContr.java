package com.litvinov.secure.contr;

import com.litvinov.secure.model.Student;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/students")
public class MyContr {

    List<Student> studentList = List.of(new Student(1,"odin"), new Student(2, "dva"));

    @RequestMapping
    public List<Student> getStudentList() {
        return studentList;
    }

    @GetMapping(path = "{id}")
    public Student getStudent(@PathVariable("id") int id) {
        return studentList.stream().filter(s->s.getId() == id).findFirst().orElseThrow(() -> new IllegalArgumentException("no student"));
    }

    @GetMapping("/createStudent/{name}")
    public Integer addStudent(@PathVariable String student) {
        Student newStudent = new Student(studentList.size()+1, student);
        studentList.add(newStudent);
        return newStudent.getId();
    }
}
