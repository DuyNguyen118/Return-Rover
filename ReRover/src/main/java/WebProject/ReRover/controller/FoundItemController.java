package WebProject.ReRover.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import WebProject.ReRover.model.FoundItem;
import WebProject.ReRover.model.User;
import WebProject.ReRover.repository.UserRepository;
import WebProject.ReRover.services.FoundItemService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/found-item")
public class FoundItemController {
    
    private final FoundItemService foundItemService;
    private final UserRepository userRepository;

    public FoundItemController(FoundItemService foundItemService, UserRepository userRepository) {
        this.foundItemService = foundItemService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<FoundItem> getAllFoundItems() {
        return foundItemService.getAllFoundItems();
    }

    @GetMapping("/{id}")
    public FoundItem getFoundItemById(@PathVariable int id) {
        return foundItemService.getFoundItemById(id);
    }

    @PostMapping
    public ResponseEntity<?> saveFoundItem(@RequestBody Map<String, Object> requestBody) {
        try {
            // Get userId from the request body
            Integer userId = (Integer) requestBody.get("userId");
            if (userId == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "User ID is required"));
            }

            // Find the user by ID
            User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

            // Rest of the code remains the same
            FoundItem foundItem = new FoundItem();
            foundItem.setTitle((String) requestBody.get("title"));
            foundItem.setDescription((String) requestBody.get("description"));
            foundItem.setLocation((String) requestBody.get("location"));
        
            String dateStr = (String) requestBody.get("foundDate");
            if (dateStr != null) {
                try {
                    foundItem.setFoundDate(LocalDate.parse(dateStr.substring(0, 10)));
                } catch (Exception e) {
                    foundItem.setFoundDate(LocalDate.now());
                }
            } else {
                foundItem.setFoundDate(LocalDate.now());
            }
        
        foundItem.setImageUrl((String) requestBody.get("imageUrl"));
        foundItem.setUser(user);
        
        FoundItem savedItem = foundItemService.saveFoundItem(foundItem);
        
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(savedItem);
            
        } catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Failed to save found item: " + e.getMessage()));
        }
    }
    @DeleteMapping("/{id}")
    public void deleteFoundItem(@PathVariable int id) {
        foundItemService.deleteFoundItem(id);
    }
}
