package se.nording.studenttracker.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.nording.studenttracker.entity.Student;
import se.nording.studenttracker.exceptions.StudentNotFoundException;
import se.nording.studenttracker.repository.StudentRepo;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepo studentRepo;

    public StudentService(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }

    public Iterable<Student> findAllStudents() {
        return studentRepo.findAll();
    }

    public Student findStudentById(Long id) {
        return studentRepo.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(
                        "Student not found with ID: " + id));
    }

    public Optional<Student> searchStudentByEmail(String email) {
        return studentRepo.findByEmail(email);
    }

    public List<Student> searchStudentsByFirstName(String firstName) {
        return studentRepo.findByFirstName(firstName);
    }

    public List<Student> searchStudentsByLastName(String lastName) {
        return studentRepo.findByLastName(lastName);
    }

    public List<Student> searchStudentsByFullName(String firstName, String lastName) {
        return studentRepo.findByFirstNameAndLastName(firstName, lastName);
    }

    public Student addStudent(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Student must not be null");
        }
        return studentRepo.save(student);
    }

    @Transactional
    public Student updateStudent(Long id, Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Student must not be null");
        }
        Student existingStudent = findStudentById(id);  // Kastar en StudentNotFoundException om ej hittad
        existingStudent.setFirstName(student.getFirstName());
        existingStudent.setLastName(student.getLastName());
        existingStudent.setEmail(student.getEmail());
        return studentRepo.save(existingStudent);
    }

    @Transactional
    public void deleteStudent(Long id) {
        if (!studentRepo.existsById(id)) {
            throw new StudentNotFoundException("No student found with ID: " + id);
        }
        studentRepo.deleteById(id);
    }
}
