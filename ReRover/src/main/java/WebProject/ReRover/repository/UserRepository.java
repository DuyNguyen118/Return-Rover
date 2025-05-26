package WebProject.ReRover.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import WebProject.ReRover.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
}