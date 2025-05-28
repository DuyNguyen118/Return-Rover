package WebProject.ReRover.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.userdetails.UserDetailsService;
import WebProject.ReRover.security.JwtTokenProvider;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final UserDetailsService userDetailsService;
    private final ObjectMapper objectMapper;

    public AuthController(AuthenticationManager authenticationManager, 
                         JwtTokenProvider tokenProvider,
                         UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.userDetailsService = userDetailsService;
        this.objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody String requestBody) {
        log.info("Raw request body: {}", requestBody);
        
        try {
            // Parse the request body manually with case-insensitive property matching
            JsonNode rootNode = objectMapper.readTree(requestBody);
            LoginRequest loginRequest = new LoginRequest();
            
            // Handle both student_id and studentId
            JsonNode studentIdNode = rootNode.findValue("student_id");
            if (studentIdNode == null) {
                studentIdNode = rootNode.findValue("studentId");
            }
            
            JsonNode passwordNode = rootNode.findValue("password");
            
            if (studentIdNode == null || passwordNode == null) {
                log.error("Invalid login request - missing studentId or password");
                return ResponseEntity.badRequest()
                    .body(Collections.singletonMap("error", "studentId and password are required"));
            }
            
            loginRequest.setStudentId(studentIdNode.asText());
            loginRequest.setPassword(passwordNode.asText());
            
            log.info("Parsed login request - studentId: {}", loginRequest.getStudentId());
            
            // Create authentication token
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginRequest.getStudentId(),
                loginRequest.getPassword()
            );
            
            log.debug("Attempting authentication...");
            
            // Authenticate the user
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            log.info("Authentication successful for user: {}", authentication.getName());

            // Set the authentication in the security context
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.debug("Security context updated with authentication");

            // Generate JWT token
            log.debug("Generating JWT token...");
            String jwt = tokenProvider.generateToken(authentication);
            log.debug("JWT token generated successfully");

            // Get user details
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            
            // Create and return response
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("token", jwt);
            response.put("studentId", userDetails.getUsername());
            response.put("authorities", userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));
                
            log.info("Login successful for user: {}", userDetails.getUsername());
            return ResponseEntity.ok(response);
            
        } catch (BadCredentialsException e) {
            log.error("Authentication failed: Invalid credentials");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Collections.singletonMap("error", "Invalid student ID or password"));
        } catch (Exception e) {
            log.error("Authentication error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Collections.singletonMap("error", "An error occurred during authentication"));
        }
    }
    
    // Login request DTO
    public static class LoginRequest {
        private String studentId;
        private String password;
        
        // Default constructor for JSON deserialization
        public LoginRequest() {}
        
        // Constructor for testing
        public LoginRequest(String studentId, String password) {
            this.studentId = studentId;
            this.password = password;
        }
        
        public String getStudentId() {
            return studentId;
        }
        
        public void setStudentId(String studentId) {
            this.studentId = studentId;
        }
        
        public String getPassword() {
            return password;
        }
        
        public void setPassword(String password) {
            this.password = password;
        }
    }
    
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            log.info("Logging out user: {}", auth.getName());
            new SecurityContextLogoutHandler().logout(request, response, auth);
            return ResponseEntity.ok().body(Collections.singletonMap("message", "Logout successful"));
        }
        return ResponseEntity.badRequest().body(Collections.singletonMap("error", "No user is currently logged in"));
    }

    @GetMapping("/user")
    public ResponseEntity<?> getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !auth.getPrincipal().equals("anonymousUser")) {
            log.debug("Fetching current user: {}", auth.getName());
            return ResponseEntity.ok().body(auth.getPrincipal());
        }
        log.warn("Unauthenticated access to /user endpoint");
        return ResponseEntity.status(401).body(Collections.singletonMap("error", "Not authenticated"));
    }
}
