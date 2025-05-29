package WebProject.ReRover.controller;

import WebProject.ReRover.model.User;
import WebProject.ReRover.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login() {
        // This endpoint is handled by Spring Security's formLogin
        return ResponseEntity.ok(Collections.singletonMap("success", true));
    }

    @GetMapping("/user")
    public ResponseEntity<?> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
            String username = authentication.getName();
            User user = userRepository.findByStudentId(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(401).body(Collections.singletonMap("error", "Not authenticated"));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        // This endpoint is handled by Spring Security's logout
        return ResponseEntity.ok(Collections.singletonMap("success", true));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> userData) {
        try {
            // Validate required fields
            if (userData.get("student_id") == null || userData.get("student_id").trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Student ID is required"));
            }
            if (userData.get("fullname") == null || userData.get("fullname").trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Full name is required"));
            }
            if (userData.get("email") == null || userData.get("email").trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Email is required"));
            }
            if (userData.get("password") == null || userData.get("password").trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Password is required"));
            }

            // Check if username or email already exists
            if (userRepository.existsByStudentId(userData.get("student_id"))) {
                return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Student ID already exists"));
            }
            if (userRepository.existsByEmail(userData.get("email"))) {
                return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Email already in use"));
            }

            // Create new user
            User newUser = new User();
            newUser.setStudentId(userData.get("student_id"));
            newUser.setFullname(userData.get("fullname"));
            newUser.setEmail(userData.get("email"));
            newUser.setPassword(passwordEncoder.encode(userData.get("password")));
            newUser.setCreatedAt(LocalDateTime.now());
            
            // Set optional fields
            if (userData.containsKey("phoneNumber")) {
                newUser.setPhoneNumber(userData.get("phoneNumber"));
            }
            newUser.setMeritPoint(0);
            
            // Save user
            User savedUser = userRepository.save(newUser);
            
            // Clear password before returning
            savedUser.setPassword(null);
            return ResponseEntity.ok(Collections.singletonMap("success", savedUser));
            
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Collections.singletonMap("error", "Registration failed: " + e.getMessage()));
        }
    }
}
