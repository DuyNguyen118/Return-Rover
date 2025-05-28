package WebProject.ReRover.services.Impl;

import WebProject.ReRover.model.User;
import WebProject.ReRover.repository.UserRepository;
import WebProject.ReRover.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserById(int id) {
        return userRepository.findById((Integer) id).orElse(null);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(int id) {
        userRepository.deleteById((Integer) id);
    }
}
