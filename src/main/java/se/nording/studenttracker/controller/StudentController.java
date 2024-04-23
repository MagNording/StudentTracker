package se.nording.studenttracker.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.nording.studenttracker.entity.Student;
import se.nording.studenttracker.exceptions.StudentNotFoundException;
import se.nording.studenttracker.service.StudentService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public Iterable<Student> getAllStudents() {
        return studentService.findAllStudents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        try {
            Student student = studentService.findStudentById(id);
            return ResponseEntity.ok(student);
        } catch (StudentNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search/byEmail")
    public ResponseEntity<Student> searchByEmail(@RequestParam String email) {
        Optional<Student> student = studentService.searchStudentByEmail(email);
        return student.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/search/byFirstName")
    public List<Student> searchByFirstName(@RequestParam String firstName) {
        return studentService.searchStudentsByFirstName(firstName);
    }

    @GetMapping("/search/byLastName")
    public List<Student> searchByLastName(@RequestParam String lastName) {
        return studentService.searchStudentsByLastName(lastName);
    }

    @GetMapping("/search/byFullName")
    public List<Student> searchByFullName(@RequestParam String firstName, @RequestParam String lastName) {
        return studentService.searchStudentsByFullName(firstName, lastName);
    }
}
