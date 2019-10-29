package service;

import domain.user.User;
import mapper.DegreeMapper;
import mapper.UserMapper;

import java.util.List;

public class UserService {
    private static UserService instance;
    private UserMapper userMapper;
    private DegreeMapper degreeMapper;

    private UserService() {
        userMapper = UserMapper.getInstance();
        degreeMapper = DegreeMapper.getInstance();
    }

    public static UserService getInstance() {
        if (instance == null) {
            synchronized (SubjectService.class) {
                if (instance == null) {
                    instance = new UserService();
                }
            }
        }
        return instance;
    }


    public int addUser (User user, int degreeId) {
        if (degreeId == -1) {
            return user.addUser();
        } else {
            int addUser = user.addUser();
            User newUser = User.getUser(user.getUsername());
            int addDegree = newUser.setDegreeOfStudent(degreeId);
            return (addUser > 0 && addDegree > 0) ? 1 : 0;
        }
    }

    public List<User> getUserList() {
        return userMapper.getUserList();
    }

    public int deleteUsers(String[] usernames) {
        return User.deleteUsers(usernames);
    }

    public int updateUser(User user, int degreeId) {
        if (degreeId == -1) {
            return user.updateUser();
        }

        if (user.updateUser() > 0
                && user.setDegreeOfStudent(degreeId) > 0) {
            return 1;
        } else {
            return 0;
        }
    }

    public int resetPassword(User user) {
        return user.resetPassword();
    }

    public User getUser(String username) {
        return User.getUser(username);
    }
}
