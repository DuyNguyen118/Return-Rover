package WebProject.ReRover.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import WebProject.ReRover.model.LostItem;
import WebProject.ReRover.services.LostItemService;

import java.util.List;

@RestController
@RequestMapping("/api/lost-item")
public class LostItemController {
    
    private final LostItemService lostItemService;

    public LostItemController(LostItemService lostItemService) {
        this.lostItemService = lostItemService;
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
    public LostItem saveLostItem(@RequestBody LostItem lostItem) {
        return lostItemService.saveLostItem(lostItem);
    }

    @DeleteMapping("/{id}")
    public void deleteLostItem(@PathVariable int id) {
        lostItemService.deleteLostItem(id);
    }
}
