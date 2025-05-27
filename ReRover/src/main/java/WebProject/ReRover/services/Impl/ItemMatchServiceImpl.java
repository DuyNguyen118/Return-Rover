package WebProject.ReRover.services.Impl;

import WebProject.ReRover.model.ItemMatch;
import WebProject.ReRover.repository.ItemMatchRepository;
import WebProject.ReRover.services.ItemMatchService;

import org.springframework.stereotype.Service;

@Service
public class ItemMatchServiceImpl implements ItemMatchService {
    private ItemMatchRepository itemMatchRepository;

    public ItemMatchServiceImpl(ItemMatchRepository itemMatchRepository) {
        this.itemMatchRepository = itemMatchRepository;
    }

    @Override
    public ItemMatch getItemMatchById(int id) {
        return itemMatchRepository.findById((Integer) id).orElse(null);
    }

    @Override
    public ItemMatch saveItemMatch(ItemMatch itemMatch) {
        return itemMatchRepository.save(itemMatch);
    }

    @Override
    public void deleteItemMatch(int id) {
        itemMatchRepository.deleteById((Integer) id);
    }
}
