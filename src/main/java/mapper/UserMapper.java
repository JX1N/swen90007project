package mapper;

import connection.DBConnection;
import connection.Util;
import domain.user.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Database operations regarding users
 */
public class UserMapper {
    private static UserMapper userMapper;
    private DegreeMapper degreeMapper;
    private SubjectMapper subjectMapper;

    /**
     * Private constructor to avoid instantiating an object
     */
    private UserMapper() {
        degreeMapper = DegreeMapper.getInstance();
        subjectMapper = SubjectMapper.getInstance();
    }

    /**
     * Singleton method to get the mapper instance
     *
     * @return a UserMapper instance
     */
    public static UserMapper getInstance() {
        if (userMapper == null) {
            synchronized (UserMapper.class) {
                if (userMapper == null) {
                    userMapper = new UserMapper();
                }
            }
        }
        return userMapper;
    }

    /**
     * Get a user using the username
     * @param username the username of the user
     * @return a user object
     */
    public User getUser(String username) {
        User cachedUser = Registry.loadUser(username);
        if (cachedUser != null) {
            return cachedUser;
        }
        Connection conn = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM user WHERE username = ?";
        User user = null;

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();
            conn.commit();

            while (rs.next()) {
                int id = rs.getInt("id");
                String password = rs.getString("password");
                int role = rs.getInt("role");
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                int gender = rs.getInt("gender");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String address = rs.getString("address");

                user = new User(id, username, password, role, firstname, lastname, gender, email, phone, address);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            try {
                conn.rollback();
            } catch (Exception rollback) {
                System.out.println("Roll back failed.");
            }
        } finally {
            DBConnection.closeConnection(conn);
        }

        return user;
    }

    /**
     * Add a user into the database
     * @param user the user to be added
     * @return number of rows affected (1 for success, 0 for failure)
     */
    public int addUser(User user) {
        Connection conn = DBConnection.getInstance().getConnection();
        String sql = "INSERT INTO user " +
                "(username, password, role, firstname, lastname, gender, email, phone, address) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setInt(3, user.getRole());
            ps.setString(4, user.getFirstName());
            ps.setString(5, user.getLastName());
            ps.setInt(6, user.getGender());
            ps.setString(7, user.getEmail());
            ps.setString(8, user.getPhone());
            ps.setString(9, user.getAddress());

            int result =  ps.executeUpdate();
            conn.commit();
            if (result != 0) {
                ResultSet key = ps.getGeneratedKeys();
                key.next();
                user.setId(key.getInt(1));
                Registry.saveUser(user);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (Exception rollback) {
                System.out.println("Roll back failed.");
            }
        } finally {
            DBConnection.closeConnection(conn);
        }
        return 0;
    }

    /**
     * Get all users from the database
     * @return a list of user objects
     */
    public List<User> getUserList() {
        if (!Registry.userEmpty()) {
            return Registry.loadAllUsers();
        }

        Connection conn = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM user";
        ArrayList<User> users = new ArrayList<>();

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            conn.commit();

            while (rs.next()) {
                User user;
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                int role = rs.getInt("role");
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                int gender = rs.getInt("gender");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String address = rs.getString("phone");
                user = new User(id, username, password, role, firstname, lastname, gender, email, phone, address);
                users.add(user);
                Registry.saveUser(user);
            }
            return users;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (Exception rollback) {
                System.out.println("Roll back failed.");
            }
        } finally {
            DBConnection.closeConnection(conn);
        }

        return null;
    }

    /**
     * Delete users from the database
     * @param usernames an array of usernames which are the users to be deleted
     * @return number of rows affected
     */
    public int deleteUsers(String[] usernames) {
        Connection conn = DBConnection.getInstance().getConnection();
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM user WHERE username IN (\"");
        for (String str : usernames) {
            sb.append(str + "\",\"");
        }
        sb.setLength(sb.length() - 3);
        sb.append("\")");
        int result =  Util.runUpdate(sb.toString(), conn);
        if (result != 0) {
            for (String str : usernames) {
                Registry.deleteUser(str);
            }
        }

        return result;
    }

    /**
     * Update the information of a user
     * @param user the user object with updated information
     * @return number of rows affected (1 for success, 0 for failure)
     */
    public int updateUser(User user) {
        Connection conn = DBConnection.getInstance().getConnection();
        String sql = "UPDATE user SET username = ?, role = ?, firstname = ?," +
                "lastname = ?, gender = ?, email = ?, phone = ?, address = ? WHERE id = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setInt(2, user.getRole());
            ps.setString(3, user.getFirstName());
            ps.setString(4, user.getLastName());
            ps.setInt(5, user.getGender());
            ps.setString(6, user.getEmail());
            ps.setString(7, user.getPhone());
            ps.setString(8, user.getAddress());
            ps.setInt(9, user.getId());
            System.out.println(ps);
            int result = ps.executeUpdate();
            conn.commit();
            if (result != 0) {
                User cached = Registry.loadUser(user.getUsername());
                if (cached != null) {
                    cached.setRole(user.getRole());
                    cached.setFirstName(user.getFirstName());
                    cached.setLastName(user.getLastName());
                    cached.setGender(user.getGender());
                    cached.setEmail(user.getEmail());
                    cached.setPhone(user.getPhone());
                    cached.setAddress(user.getAddress());
                }
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (Exception rollback) {
                System.out.println("Roll back failed.");
            }
        } finally {
            DBConnection.closeConnection(conn);
        }
        return 0;
    }

    /**
     * Reset the password for a user
     * @param user the user object with new password
     * @return number of rows affected (1 for success, 0 for failure)
     */
    public int resetPassword(User user) {
        Connection conn = DBConnection.getInstance().getConnection();
        String sql = "UPDATE user SET password = ? WHERE username = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user.getPassword());
            ps.setString(2, user.getUsername());

            int result = ps.executeUpdate();
            conn.commit();
            if (result != 0) {
                User cached = Registry.loadUser(user.getUsername());
                cached.setPassword(user.getPassword());
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (Exception rollback) {
                System.out.println("Roll back failed.");
            }
        } finally {
            DBConnection.closeConnection(conn);
        }
        return 0;
    }
}
