package WebProject.ReRover.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import WebProject.ReRover.model.enums.ItemStatus;

@Entity
@Table(name = "lost_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LostItem extends BaseEntity {

    @NotBlank
    @Size(max = 150)
    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Size(max = 100)
    private String location;

    @Column(name = "lost_date")
    private LocalDate lostDate;
    
    @Column(name = "image_url", length = 255)
    private String imageUrl;
    
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;
    
    @Enumerated(EnumType.STRING)
    private ItemStatus status = ItemStatus.LOST;

    @OneToOne(mappedBy = "lostItem", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private ItemMatch itemMatch;
    
    // Helper methods for bidirectional relationship
    public void setUser(User user) {
        this.user = user;
        if (user != null && !user.getLostItems().contains(this)) {
            user.getLostItems().add(this);
        }
    }
    
    public void setItemMatch(ItemMatch itemMatch) {
        if (itemMatch == null) {
            if (this.itemMatch != null) {
                this.itemMatch.setLostItem(null);
            }
        } else {
            itemMatch.setLostItem(this);
        }
        this.itemMatch = itemMatch;
    }
}
