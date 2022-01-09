package com.litvinov.secure.contr;

import com.litvinov.secure.model.Student;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("manage/api/v1/students")
public class StudentManagmentController {

    List<Student> studentList = List.of(new Student(1,"odin"), new Student(2, "dva"));

    @GetMapping
    public List<Student> getStudentList() {
        return studentList;
    }

    @PostMapping
    public void registerNewStudent(@RequestBody Student student) {
        System.out.println("added " + student);
    }

    @DeleteMapping(path = "{id}")
    public void deleteStudent(@PathVariable("id") Integer id) {
        System.out.println("deleted " + id);
    }

    @PutMapping("{id}")
    public void updateStudent(@PathVariable("id") Integer studentId, @RequestBody Student student) {
        System.out.println("updated " + String.format("%s %s", studentId, student.getName()));
    }
}
