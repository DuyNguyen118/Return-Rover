package WebProject.ReRover.services.Impl;

import WebProject.ReRover.model.User;
import WebProject.ReRover.repository.UserRepository;
import WebProject.ReRover.services.UserService;

public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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
