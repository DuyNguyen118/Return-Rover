package WebProject.ReRover.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import WebProject.ReRover.model.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {
    
}
