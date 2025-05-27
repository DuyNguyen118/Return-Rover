package WebProject.ReRover.services;

import WebProject.ReRover.model.ItemMatch;

public interface ItemMatchService {
    ItemMatch getItemMatchById(int id);
    ItemMatch saveItemMatch(ItemMatch itemMatch);
    void deleteItemMatch(int id);
}
