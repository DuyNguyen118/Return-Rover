package WebProject.ReRover.services;

import WebProject.ReRover.model.LostItem;
import java.util.List;

public interface LostItemService {
    public List<LostItem> getAllLostItems();
    public LostItem getLostItemById(int id);
    public LostItem saveLostItem(LostItem lostItem);
    public void deleteLostItem(int id);
}
