package WebProject.ReRover.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "admins")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Admin {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Long id;
    
    @NotBlank
    @Size(max = 100)
    @Column(nullable = false)
    private String name;
    
    @NotBlank
    @Size(max = 100)
    @Email
    @Column(unique = true, nullable = false)
    private String email;
    
    @NotBlank
    @Size(max = 255)
    @Column(nullable = false)
    private String password;
    
    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<AdminAction> adminActions = new ArrayList<>();
    
    // Helper methods for bidirectional relationship
    public void addAdminAction(AdminAction action) {
        adminActions.add(action);
        action.setAdmin(this);
    }
    
    public void removeAdminAction(AdminAction action) {
        adminActions.remove(action);
        action.setAdmin(null);
    }
}