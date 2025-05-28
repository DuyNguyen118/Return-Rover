package WebProject.ReRover.repository;

import WebProject.ReRover.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByStudentId(String studentId);
}