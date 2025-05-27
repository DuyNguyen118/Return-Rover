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
@Table(name = "found_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoundItem extends BaseEntity {

    @NotBlank
    @Size(max = 150)
    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Size(max = 100)
    private String location;

    @Column(name = "found_date")
    private LocalDate foundDate;
    
    @Column(name = "image_url", length = 255)
    private String imageUrl;
    
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @Enumerated(EnumType.STRING)
    private ItemStatus status = ItemStatus.FOUND;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @OneToOne(mappedBy = "foundItem", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private ItemMatch itemMatch;
    
    // Helper methods for bidirectional relationship
    public void setUser(User user) {
        this.user = user;
        if (user != null && !user.getFoundItems().contains(this)) {
            user.getFoundItems().add(this);
        }
    }
    
    public void setItemMatch(ItemMatch itemMatch) {
        if (itemMatch == null) {
            if (this.itemMatch != null) {
                this.itemMatch.setFoundItem(null);
            }
        } else {
            itemMatch.setFoundItem(this);
        }
        this.itemMatch = itemMatch;
    }
}
