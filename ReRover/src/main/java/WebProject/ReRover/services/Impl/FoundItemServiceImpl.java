package WebProject.ReRover.services.Impl;

import WebProject.ReRover.model.FoundItem;
import WebProject.ReRover.repository.FoundItemRepository;
import WebProject.ReRover.services.FoundItemService;

import org.springframework.stereotype.Service;

@Service
public class FoundItemServiceImpl implements FoundItemService {
    private FoundItemRepository foundItemRepository;

    public FoundItemServiceImpl(FoundItemRepository foundItemRepository) {
        this.foundItemRepository = foundItemRepository;
    }

    @Override
    public FoundItem getFoundItemById(int id) {
        return foundItemRepository.findById((long) id).orElse(null);
    }

    @Override
    public FoundItem saveFoundItem(FoundItem foundItem) {
        return foundItemRepository.save(foundItem);
    }

    @Override
    public void deleteFoundItem(int id) {
        foundItemRepository.deleteById((long) id);
    }
}
