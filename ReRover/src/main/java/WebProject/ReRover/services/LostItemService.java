package WebProject.ReRover.services;

import WebProject.ReRover.model.LostItem;

public interface LostItemService {
    public LostItem getLostItemById(int id);
    public LostItem saveLostItem(LostItem lostItem);
    public void deleteLostItem(int id);
}
