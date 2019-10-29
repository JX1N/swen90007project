package service;

import mapper.UserMapper;
import domain.user.User;

public class LoginService {
    private static LoginService instance;
    private UserMapper userMapper;

    private LoginService() {
        userMapper = UserMapper.getInstance();
    }

    public static LoginService getInstance() {
        if (instance == null) {
            synchronized (LoginService.class) {
                if (instance == null) {
                    instance = new LoginService();
                }
            }
        }
        return instance;
    }
    public User login(String username, String password) {
        User user = userMapper.getUser(username);
        if (user == null) {
            return null;
        }
        if (user.getPassword().equals(password)) {
            return user;
        } else {
            return null;
        }
    }
}
