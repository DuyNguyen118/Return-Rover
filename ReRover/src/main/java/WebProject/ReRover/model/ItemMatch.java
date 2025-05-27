package WebProject.ReRover.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "item_matches")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemMatch {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "match_id")
    private Integer matchId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lost_item_id", nullable = false)
    private LostItem lostItem;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "found_item_id", nullable = false)
    private FoundItem foundItem;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matched_by_user")
    private User matchedByUser;
    
    @Column(name = "lost_item_user_confirmed", nullable = false)
    private boolean lostItemUserConfirmed = false;
    
    @Column(name = "found_item_user_confirmed", nullable = false)
    private boolean foundItemUserConfirmed = false;
    
    @Column(name = "admin_approved", nullable = false)
    private boolean adminApproved = false;
    
    @Column(length = 50, nullable = false)
    private String status = "pending";
    
    @Column(name = "match_date", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime matchDate = LocalDateTime.now();
    
    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<AdminAction> adminActions = new ArrayList<>();
    
    // Helper methods for bidirectional relationships
    public void setLostItem(LostItem lostItem) {
        this.lostItem = lostItem;
        if (lostItem != null && lostItem.getItemMatch() != this) {
            lostItem.setItemMatch(this);
        }
    }
    
    public void setFoundItem(FoundItem foundItem) {
        this.foundItem = foundItem;
        if (foundItem != null && foundItem.getItemMatch() != this) {
            foundItem.setItemMatch(this);
        }
    }
    
    public void addAdminAction(AdminAction action) {
        adminActions.add(action);
        action.setMatch(this);
    }
    
    public void removeAdminAction(AdminAction action) {
        adminActions.remove(action);
        action.setMatch(null);
    }
}
