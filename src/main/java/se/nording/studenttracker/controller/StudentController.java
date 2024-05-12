package se.nording.studenttracker.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.nording.studenttracker.entity.Student;
import se.nording.studenttracker.exceptions.StudentNotFoundException;
import se.nording.studenttracker.service.StudentService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final Logger logger = org.slf4j.LoggerFactory.getLogger(StudentController.class);

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
        logger.info("Request to get student by ID: {}", id);
        Student student = studentService.findStudentById(id);
        return ResponseEntity.ok(student);
    }

    @GetMapping("/search/byEmail/{email}")
    public ResponseEntity<Student> searchByEmail(@PathVariable String email) {
        logger.info("Request to search by email: {}", email);
        Optional<Student> student = studentService.searchStudentByEmail(email);
        return student.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/search/byFirstName/{firstName}")
    public ResponseEntity<List<Student>> searchByFirstName(@PathVariable String firstName) {
        logger.info("Request to search by first name: {}", firstName);
        List<Student> students = studentService.searchStudentsByFirstName(firstName);
        return students.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(students);
    }

    @GetMapping("/search/byLastName/{lastName}")
    public ResponseEntity<List<Student>> searchByLastName(@PathVariable String lastName) {
        logger.info("Request to search by last name: {}", lastName);
        List<Student> students = studentService.searchStudentsByLastName(lastName);
        return students.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(students);
    }

    @GetMapping("/search/byFullName")
    public List<Student> searchByFullName(@RequestParam String firstName,
                                          @RequestParam String lastName) {
        logger.info("Request to search by full name: {} {}", firstName, lastName);
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
            throw new IllegalArgumentException("Mismatched ID in request path and body");
        }
        Student updatedStudent = studentService.updateStudent(id, student);
        return ResponseEntity.ok(updatedStudent);
    }

    @PatchMapping("/{id}") // Skönt med patch som uppdaterar enbart de fält som skickas in
    public ResponseEntity<Student> patchStudent(@PathVariable Long id,
                                                @RequestBody Map<String, Object> updates) {
        Student updatedStudent = studentService.patchStudent(id, updates);
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
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
//        }
//        return ResponseEntity.ok(students);
//    }

}
