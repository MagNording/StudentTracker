package se.nording.studenttracker.controller;

import jakarta.validation.Valid;
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
    public List<Student> searchByFullName(@RequestParam String firstName,
                                          @RequestParam String lastName) {
        return studentService.searchStudentsByFullName(firstName, lastName);
    }

    @PostMapping
    public ResponseEntity<Student> addStudent(@Valid @RequestBody Student student) {
        Student newStudent = studentService.addStudent(student);
        return ResponseEntity.ok(newStudent);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id,
                                                 @RequestBody Student student) {
        if (!id.equals(student.getId())) {
            return ResponseEntity.badRequest().build();
        }
        try {
            Student updatedStudent = studentService.updateStudent(id, student);
            return ResponseEntity.ok(updatedStudent);
        } catch (StudentNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        try {
            studentService.deleteStudent(id);
            return ResponseEntity.noContent().build();
        } catch (StudentNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }


//    @GetMapping("/search") // Alternativ sökmetod som tar flera parametrar
//    public ResponseEntity<List<Student>> searchStudents(@RequestParam(required = false) String email,
//                                                        @RequestParam(required = false) String firstName,
//                                                        @RequestParam(required = false) String lastName) {
//        List<Student> students;
//        if (firstName != null && lastName != null) {
//            students = studentService.searchStudentsByFullName(firstName, lastName);
//        } else if (firstName != null) {
//            students = studentService.searchStudentsByFirstName(firstName);
//        } else if (lastName != null) {
//            students = studentService.searchStudentsByLastName(lastName);
//        } else {
//            return ResponseEntity.badRequest().body(null); // eller returnera alla studenter eller tomt
//
//
//        }
//        return ResponseEntity.ok(students);
//    }

}
