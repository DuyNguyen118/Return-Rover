package WebProject.ReRover;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.session.SessionAutoConfiguration;

@SpringBootApplication(exclude = {
    SessionAutoConfiguration.class
})
public class ReRoverApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReRoverApplication.class, args);
    }
}