package WebProject.ReRover.security;

import WebProject.ReRover.model.User;
import WebProject.ReRover.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Attempting to load user by username: {}", username);
        
        User user = userRepository.findByStudentId(username)
                .orElseThrow(() -> {
                    log.error("User not found with student ID: {}", username);
                    return new UsernameNotFoundException("User not found with student ID: " + username);
                });
        
        log.info("User found: {}", user.getStudentId());
        log.debug("User details - ID: {}, Email: {}", user.getId(), user.getEmail());

        return UserDetailsImpl.build(user);
    }
}
