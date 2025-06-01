package WebProject.ReRover.services;

import WebProject.ReRover.model.FoundItem;
import java.util.List;

public interface FoundItemService {
    public List<FoundItem> getAllFoundItems();
    public FoundItem getFoundItemById(int id);
    public FoundItem saveFoundItem(FoundItem foundItem);
    public void deleteFoundItem(int id);
}
