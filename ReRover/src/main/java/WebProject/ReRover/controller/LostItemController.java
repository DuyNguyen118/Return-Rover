package WebProject.ReRover.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import WebProject.ReRover.model.LostItem;
import WebProject.ReRover.services.LostItemService;
import WebProject.ReRover.services.UserService;
import WebProject.ReRover.model.User;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import WebProject.ReRover.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/lost-item")
public class LostItemController {
    
    private final LostItemService lostItemService;
    private final UserService userService;
    private final UserRepository userRepository;

    public LostItemController(LostItemService lostItemService, UserService userService, UserRepository userRepository) {
        this.lostItemService = lostItemService;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<LostItem> getAllLostItems() {
        return lostItemService.getAllLostItems();
    }

    @GetMapping("/{id}")
    public LostItem getLostItemById(@PathVariable int id) {
        return lostItemService.getLostItemById(id);
    }

    @PostMapping
    public ResponseEntity<?> saveLostItem(@RequestBody Map<String, Object> requestBody) {
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
            LostItem lostItem = new LostItem();
            lostItem.setTitle((String) requestBody.get("title"));
            lostItem.setDescription((String) requestBody.get("description"));
            lostItem.setLocation((String) requestBody.get("location"));
        
            String dateStr = (String) requestBody.get("lostDate");
            if (dateStr != null) {
                try {
                    lostItem.setLostDate(LocalDate.parse(dateStr.substring(0, 10)));
                } catch (Exception e) {
                    lostItem.setLostDate(LocalDate.now());
                }
            } else {
                lostItem.setLostDate(LocalDate.now());
            }
        
        lostItem.setImageUrl((String) requestBody.get("imageUrl"));
        lostItem.setUser(user);
        
        LostItem savedItem = lostItemService.saveLostItem(lostItem);
        
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(savedItem);
            
        } catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Failed to save lost item: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public void deleteLostItem(@PathVariable int id) {
        lostItemService.deleteLostItem(id);
    }
}
