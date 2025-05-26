package WebProject.ReRover.services;

import WebProject.ReRover.model.User;

public interface UserService {
    public User getUserById(int id);
    public User saveUser(User user);
    public void deleteUser(int id);
}
