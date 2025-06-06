package WebProject.ReRover.dto;

import WebProject.ReRover.model.FoundItem;
import WebProject.ReRover.model.ItemMatch;
import WebProject.ReRover.model.LostItem;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ItemMatchDTO {
    private Integer id;
    private LostItem lostItem;
    private FoundItem foundItem;
    private LocalDateTime matchedAt;
    private String status;
    private List<Object> adminActions; // Adjust the type based on what AdminAction contains

    public static ItemMatchDTO fromEntity(ItemMatch itemMatch) {
        ItemMatchDTO dto = new ItemMatchDTO();
        dto.setId(itemMatch.getId());
        dto.setLostItem(itemMatch.getLostItem());
        dto.setFoundItem(itemMatch.getFoundItem());
        dto.setMatchedAt(itemMatch.getMatchDate());
        dto.setStatus(itemMatch.getStatus());
        return dto;
    }
}