package WebProject.ReRover.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import WebProject.ReRover.model.LostItem;

public interface LostItemRepository extends JpaRepository<LostItem, Long> {
    
}