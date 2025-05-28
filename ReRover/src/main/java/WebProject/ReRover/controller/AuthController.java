package WebProject.ReRover.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @PostMapping("/login")
    public String login() {
        return "Login";
    }
}
