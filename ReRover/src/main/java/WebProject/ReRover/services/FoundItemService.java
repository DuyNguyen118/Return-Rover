package WebProject.ReRover.services;

import WebProject.ReRover.model.FoundItem;

import java.util.List;

public interface FoundItemService {
    List<FoundItem> getAllFoundItems();
    FoundItem getFoundItemById(int id);
    FoundItem saveFoundItem(FoundItem foundItem);
    void deleteFoundItem(int id);
}
