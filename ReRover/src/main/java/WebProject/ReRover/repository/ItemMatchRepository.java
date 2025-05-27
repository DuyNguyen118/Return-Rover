package WebProject.ReRover.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import WebProject.ReRover.model.ItemMatch;

public interface ItemMatchRepository extends JpaRepository<ItemMatch, Integer> {
    
}
