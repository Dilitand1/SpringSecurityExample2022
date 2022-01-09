package com.litvinov.secure.contr;

import com.litvinov.secure.model.Student;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("manage/api/v1/students")
public class StudentManagmentController {

    List<Student> studentList = List.of(new Student(1,"odin"), new Student(2, "dva"));

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMINTRAIN')")
    public List<Student> getStudentList() {
        return studentList;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('student:write')")
    public void registerNewStudent(@RequestBody Student student) {
        System.out.println("added " + student);
    }

    @DeleteMapping(path = "{id}")
    @PreAuthorize("hasAuthority('student:write')")
    public void deleteStudent(@PathVariable("id") Integer id) {
        System.out.println("deleted " + id);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('student:write')")
    public void updateStudent(@PathVariable("id") Integer studentId, @RequestBody Student student) {
        System.out.println("updated " + String.format("%s %s", studentId, student.getName()));
    }
}
