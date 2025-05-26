package WebProject.ReRover.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import WebProject.ReRover.model.FoundItem;

public interface FoundItemRepository extends JpaRepository<FoundItem, Long> {
    
}