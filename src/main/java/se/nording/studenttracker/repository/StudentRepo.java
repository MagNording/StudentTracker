package se.nording.studenttracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.nording.studenttracker.entity.Student;

public interface StudentRepo extends JpaRepository<Student, Long> {
}
