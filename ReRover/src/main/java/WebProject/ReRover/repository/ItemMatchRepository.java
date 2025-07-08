package WebProject.ReRover.repository;

import java.util.List;
import WebProject.ReRover.model.FoundItem;
import WebProject.ReRover.model.ItemMatch;
import WebProject.ReRover.model.LostItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemMatchRepository extends JpaRepository<ItemMatch, Integer> {
    
    @Query("SELECT li FROM LostItem li WHERE li.user.id = :userId")
    List<LostItem> findLostItemsByUserId(@Param("userId") int userId);
    
    @Query("SELECT fi FROM FoundItem fi WHERE fi.user.id = :userId")
    List<FoundItem> findFoundItemsByUserId(@Param("userId") int userId);
    
    @Query("SELECT im FROM ItemMatch im JOIN FETCH im.lostItem li WHERE li.id IN (SELECT l.id FROM LostItem l WHERE l.user.id = :userId)")
    List<ItemMatch> findMatchesByLostItemsUserId(@Param("userId") int userId);
    
    @Query("SELECT im FROM ItemMatch im JOIN FETCH im.foundItem fi WHERE fi.id IN (SELECT f.id FROM FoundItem f WHERE f.user.id = :userId)")
    List<ItemMatch> findMatchesByFoundItemsUserId(@Param("userId") int userId);
}
