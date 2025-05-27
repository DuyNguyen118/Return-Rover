package WebProject.ReRover.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import WebProject.ReRover.model.FoundItem;
import WebProject.ReRover.services.FoundItemService;

@RestController
@RequestMapping("/found-item")
public class FoundItemController {
    
    private FoundItemService foundItemService;

    public FoundItemController(FoundItemService foundItemService) {
        this.foundItemService = foundItemService;
    }

    @GetMapping("/{id}")
    public FoundItem getFoundItemById(@PathVariable int id) {
        return foundItemService.getFoundItemById(id);
    }

    @PostMapping
    public FoundItem saveFoundItem(@RequestBody FoundItem foundItem) {
        return foundItemService.saveFoundItem(foundItem);
    }

    @DeleteMapping("/{id}")
    public void deleteFoundItem(@PathVariable int id) {
        foundItemService.deleteFoundItem(id);
    }
}
