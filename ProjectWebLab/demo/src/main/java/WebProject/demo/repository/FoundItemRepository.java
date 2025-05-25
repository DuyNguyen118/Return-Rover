package WebProject.ReRover.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import WebProject.ReRover.model.FoundItems;

public interface FoundItemRepository extends JpaRepository<FoundItems, Long> {
    
}
