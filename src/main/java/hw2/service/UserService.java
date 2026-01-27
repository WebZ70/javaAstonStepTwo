package hw2.service;


import hw2.dao.UserDAO;
import hw2.models.User;

import java.util.List;

public class UserService {
    private final UserDAO userDAO = new UserDAO();

    public User findUser(int id) {
        return userDAO.findById(id);
    }

    public void createUser(User user) {
        userDAO.create(user);
    }

    public void updateUser(User user) {
        userDAO.update(user);
    }

    public void deleteUser(User user) {
        userDAO.delete(user);
    }

    public List<User> findAllUsers() {
        return userDAO.findAll();
    }
}
