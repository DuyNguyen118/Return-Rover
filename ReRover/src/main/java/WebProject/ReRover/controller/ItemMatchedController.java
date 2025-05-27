package WebProject.ReRover.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import WebProject.ReRover.model.ItemMatch;
import WebProject.ReRover.services.ItemMatchService;

@RestController
@RequestMapping("/item-matched")
public class ItemMatchedController {
    private ItemMatchService itemMatchService;

    public ItemMatchedController(ItemMatchService itemMatchService) {
        this.itemMatchService = itemMatchService;
    }

    @GetMapping("/{id}")
    public ItemMatch getItemMatchById(@PathVariable int id) {
        return itemMatchService.getItemMatchById(id);
    }

    @PostMapping
    public ItemMatch saveItemMatch(@RequestBody ItemMatch itemMatch) {
        return itemMatchService.saveItemMatch(itemMatch);
    }

    @DeleteMapping("/{id}")
    public void deleteItemMatch(@PathVariable int id) {
        itemMatchService.deleteItemMatch(id);
    }
}
