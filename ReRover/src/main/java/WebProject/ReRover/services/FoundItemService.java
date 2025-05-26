package WebProject.ReRover.services;

import WebProject.ReRover.model.FoundItem;

public interface FoundItemService {
    public FoundItem getFoundItemById(int id);
    public FoundItem saveFoundItem(FoundItem foundItem);
    public void deleteFoundItem(int id);
}
