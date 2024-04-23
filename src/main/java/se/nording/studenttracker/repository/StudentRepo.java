package se.nording.studenttracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.nording.studenttracker.entity.Student;

import java.util.List;
import java.util.Optional;

public interface StudentRepo extends JpaRepository<Student, Long> {
    Optional<Student> findByEmail(String email);
    List<Student> findByFirstName(String firstName);
    List<Student> findByLastName(String lastName);
    List<Student> findByFirstNameAndLastName(String firstName, String lastName);
}
