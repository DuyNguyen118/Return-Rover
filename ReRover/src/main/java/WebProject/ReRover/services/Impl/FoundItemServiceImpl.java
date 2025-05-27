// package WebProject.ReRover.services.Impl;

// import WebProject.ReRover.model.FoundItem;
// import WebProject.ReRover.repository.FoundItemRepository;
// import WebProject.ReRover.services.FoundItemService;

// public class FoundItemServiceImpl implements FoundItemService {
//     private FoundItemRepository foundItemRepository;

//     public FoundItemServiceImpl(FoundItemRepository foundItemRepository) {
//         this.foundItemRepository = foundItemRepository;
//     }

//     @Override
//     public FoundItem getFoundItemById(Integer id) {
//         return foundItemRepository.findById((Integer) id).orElse(null);
//     }

//     @Override
//     public FoundItem saveFoundItem(FoundItem foundItem) {
//         return foundItemRepository.save(foundItem);
//     }

//     @Override
//     public void deleteFoundItem(Integer id) {
//         foundItemRepository.deleteById((Integer) id);
//     }
// }
