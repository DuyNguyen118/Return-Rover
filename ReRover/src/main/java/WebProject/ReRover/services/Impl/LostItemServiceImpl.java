package WebProject.ReRover.services.Impl;

import WebProject.ReRover.model.LostItem;
import WebProject.ReRover.repository.LostItemRepository;
import WebProject.ReRover.services.LostItemService;

import org.springframework.stereotype.Service;

@Service
public class LostItemServiceImpl implements LostItemService {
    private LostItemRepository lostItemRepository;

    public LostItemServiceImpl(LostItemRepository lostItemRepository) {
        this.lostItemRepository = lostItemRepository;
    }

    @Override
    public LostItem getLostItemById(int id) {
        return lostItemRepository.findById((long) id).orElse(null);
    }

    @Override
    public LostItem saveLostItem(LostItem lostItem) {
        return lostItemRepository.save(lostItem);
    }

    @Override
    public void deleteLostItem(int id) {
        lostItemRepository.deleteById((long) id);
    }
}
