package WebProject.ReRover.services.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import WebProject.ReRover.model.FoundItem;
import WebProject.ReRover.model.ItemMatch;
import WebProject.ReRover.model.LostItem;
import WebProject.ReRover.repository.ItemMatchRepository;
import WebProject.ReRover.services.ItemMatchService;
import WebProject.ReRover.services.LostItemService;
import WebProject.ReRover.services.FoundItemService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ItemMatchServiceImpl implements ItemMatchService {
    private static final Logger log = LoggerFactory.getLogger(ItemMatchServiceImpl.class);

    private ItemMatchRepository itemMatchRepository;
    private LostItemService lostItemService;
    private FoundItemService foundItemService;

    public List<LostItem> getLostItemsByUserId(int userId) {
        return itemMatchRepository.findLostItemsByUserId(userId);
    }

    public List<FoundItem> getFoundItemsByUserId(int userId) {
        return itemMatchRepository.findFoundItemsByUserId(userId);
    }

    public ItemMatchServiceImpl(ItemMatchRepository itemMatchRepository, LostItemService lostItemService, FoundItemService foundItemService) {
        this.itemMatchRepository = itemMatchRepository;
        this.lostItemService = lostItemService;
        this.foundItemService = foundItemService;
    }

    @Override
    public ItemMatch getItemMatchById(int id) {
        return itemMatchRepository.findById((Integer) id).orElse(null);
    }

    @Override
    public ItemMatch saveItemMatch(ItemMatch itemMatch) {
        // If the lostItem is not null and has an ID, fetch it from the database
        if (itemMatch.getLostItem() != null && itemMatch.getLostItem().getId() != null) {
            LostItem existingLostItem = lostItemService.getLostItemById(itemMatch.getLostItem().getId());
            itemMatch.setLostItem(existingLostItem);
        }
        
        // If the foundItem is not null and has an ID, fetch it from the database
        if (itemMatch.getFoundItem() != null && itemMatch.getFoundItem().getId() != null) {
            FoundItem existingFoundItem = foundItemService.getFoundItemById(itemMatch.getFoundItem().getId());
            itemMatch.setFoundItem(existingFoundItem);
        }
        
        return itemMatchRepository.save(itemMatch);
    }

    @Override
    public void deleteItemMatch(int id) {
        itemMatchRepository.deleteById((Integer) id);
    }

    @Override
    public List<ItemMatch> getMatchesForUser(int userId) {
        try {
            log.info("Finding matches for user ID: {}", userId);
            
            // Get all matches where user's lost items are involved
            List<ItemMatch> lostItemMatches = itemMatchRepository.findMatchesByLostItemsUserId(userId);
            
            // Get all matches where user's found items are involved
            List<ItemMatch> foundItemMatches = itemMatchRepository.findMatchesByFoundItemsUserId(userId);
            
            log.debug("Found {} lost item matches and {} found item matches for user ID: {}", 
                    lostItemMatches.size(), foundItemMatches.size(), userId);
            
            // Combine the results, avoiding duplicates
            List<ItemMatch> allMatches = new ArrayList<>();
            Set<Integer> seenMatchIds = new HashSet<>();
            
            // Add lost item matches
            for (ItemMatch match : lostItemMatches) {
                if (seenMatchIds.add(match.getId())) {
                    allMatches.add(match);
                }
            }
            
            // Add found item matches
            for (ItemMatch match : foundItemMatches) {
                if (seenMatchIds.add(match.getId())) {
                    allMatches.add(match);
                }
            }
            
            log.info("Total unique matches found for user ID {}: {}", userId, allMatches.size());
            
            return allMatches;
            
        } catch (Exception e) {
            log.error("Error in getMatchesForUser for user ID {}: {}", userId, e.getMessage(), e);
            throw new RuntimeException("Failed to fetch matches for user: " + e.getMessage(), e);
        }
    }
}
