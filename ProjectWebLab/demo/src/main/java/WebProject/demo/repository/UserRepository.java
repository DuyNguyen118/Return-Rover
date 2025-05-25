package WebProject.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import WebProject.demo.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
}
