package WebProject.ReRover.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import WebProject.ReRover.model.LostItems;

public interface LostItemRepository extends JpaRepository<LostItems, Long> {
    
}
