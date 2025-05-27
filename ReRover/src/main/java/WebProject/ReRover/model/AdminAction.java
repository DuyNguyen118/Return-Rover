package WebProject.ReRover.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "admin_actions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminAction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "action_id")
    private Integer actionId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id", nullable = false)
    private Admin admin;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id", nullable = false)
    private ItemMatch match;
    
    @Column(name = "action_type", length = 50)
    private String actionType;
    
    @Column(columnDefinition = "TEXT")
    private String notes;
    
    @Column(name = "action_date")
    private LocalDateTime actionDate;
    
    // Helper methods for bidirectional relationships
    public void setAdmin(Admin admin) {
        this.admin = admin;
        if (admin != null && !admin.getAdminActions().contains(this)) {
            admin.getAdminActions().add(this);
        }
    }
    
    public void setMatch(ItemMatch match) {
        this.match = match;
        if (match != null && !match.getAdminActions().contains(this)) {
            match.getAdminActions().add(this);
        }
    }
}
