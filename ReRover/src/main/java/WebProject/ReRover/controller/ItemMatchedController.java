package WebProject.ReRover.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import WebProject.ReRover.model.FoundItem;
import WebProject.ReRover.model.ItemMatch;
import WebProject.ReRover.model.LostItem;
import WebProject.ReRover.security.UserDetailsImpl;
import WebProject.ReRover.services.FoundItemService;
import WebProject.ReRover.services.ItemMatchService;
import WebProject.ReRover.services.LostItemService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import WebProject.ReRover.dto.ItemMatchDTO;

import org.springframework.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api")
public class ItemMatchedController {
    private static final Logger log = LoggerFactory.getLogger(ItemMatchedController.class);
    private ItemMatchService itemMatchService;
    private LostItemService lostItemService;
    private FoundItemService foundItemService;

    public ItemMatchedController(ItemMatchService itemMatchService, LostItemService lostItemService, FoundItemService foundItemService) {
        this.itemMatchService = itemMatchService;
        this.lostItemService = lostItemService;
        this.foundItemService = foundItemService;
    }

    @GetMapping("/users/{userId}/posts")
    public ResponseEntity<?> getUserPosts(
        @PathVariable int userId,
        @RequestParam String type) {
        
        if ("lost".equalsIgnoreCase(type)) {
            List<LostItem> lostItems = itemMatchService.getLostItemsByUserId(userId);
            return ResponseEntity.ok(lostItems);
        } else if ("found".equalsIgnoreCase(type)) {
            List<FoundItem> foundItems = itemMatchService.getFoundItemsByUserId(userId);
            return ResponseEntity.ok(foundItems);
        } else {
            return ResponseEntity.badRequest().body("Invalid type parameter. Use 'lost' or 'found'");
        }
    }

    @PostMapping("/item-matched")
    public ResponseEntity<?> saveItemMatch(@RequestBody ItemMatch itemMatch) {
        try {
            // Ensure the lostItem and foundItem are managed entities
            if (itemMatch.getLostItem() != null && itemMatch.getLostItem().getId() != null) {
                LostItem managedLostItem = lostItemService.getLostItemById(itemMatch.getLostItem().getId());
                if (managedLostItem == null) {
                    return ResponseEntity.badRequest().body("Lost item not found with id: " + itemMatch.getLostItem().getId());
                }
                itemMatch.setLostItem(managedLostItem);
            } else {
                return ResponseEntity.badRequest().body("Lost item ID is required");
            }
            
            if (itemMatch.getFoundItem() != null && itemMatch.getFoundItem().getId() != null) {
                FoundItem managedFoundItem = foundItemService.getFoundItemById(itemMatch.getFoundItem().getId());
                if (managedFoundItem == null) {
                    return ResponseEntity.badRequest().body("Found item not found with id: " + itemMatch.getFoundItem().getId());
                }
                itemMatch.setFoundItem(managedFoundItem);
            } else {
                return ResponseEntity.badRequest().body("Found item ID is required");
            }
            
            // Set the current timestamp
            itemMatch.setMatchDate(LocalDateTime.now());
            
            // Save the item match
            ItemMatch savedItemMatch = itemMatchService.saveItemMatch(itemMatch);
            return ResponseEntity.ok(savedItemMatch);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error saving item match: " + e.getMessage());
        }
    }

    @GetMapping("/matches")
    public ResponseEntity<?> getCurrentUserMatches(Authentication authentication) {
        try {
            if (authentication == null) {
                log.error("Authentication object is null");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: No authentication information available");
            }
        
            log.info("Authentication principal class: {}", authentication.getPrincipal().getClass().getName());
        
            Object principal = authentication.getPrincipal();
            if (principal == null) {
                log.error("Principal is null in authentication object");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: No principal found");
            }
        
            int userId;
            if (principal instanceof UserDetailsImpl) {
                userId = ((UserDetailsImpl) principal).getId();
            } else {
                log.error("Unexpected principal type: {}", principal.getClass().getName());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Authentication error: Unexpected user type");
            }
        
            log.info("Fetching matches for user ID: {}", userId);
            List<ItemMatch> matches = itemMatchService.getMatchesForUser(userId);
            log.info("Found {} matches for user ID: {}", matches.size(), userId);
        
            // Convert to DTOs
            List<ItemMatchDTO> matchDTOs = matches.stream()
                .map(ItemMatchDTO::fromEntity)
                .collect(Collectors.toList());
        
            return ResponseEntity.ok(matchDTOs);
        
        } catch (Exception e) {
            log.error("Error in getCurrentUserMatches: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error fetching matches: " + e.getMessage());
        }
    }

    @DeleteMapping("/item-matched/{id}")
    public void deleteItemMatch(@PathVariable int id) {
        itemMatchService.deleteItemMatch(id);
    }
}
