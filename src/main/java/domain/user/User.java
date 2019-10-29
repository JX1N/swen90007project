package domain.user;

import mapper.StudentMapper;
import mapper.UnitOfWork;
import mapper.UserMapper;

import java.util.ArrayList;
import java.util.List;

public class User {
    private int id;
    private String username;
    private String password;
    private int role;
    private String firstName;
    private String lastName;
    private int gender;
    private String email;
    private String phone;
    private String address;
    private ArrayList<Integer> subjects;
    private ArrayList<Integer> degrees;
    private static UserMapper userMapper;
    private static StudentMapper studentMapper;

    static {
        userMapper = UserMapper.getInstance();
        studentMapper = StudentMapper.getInstance();
    }

    public User(int id, String username, String password, int role, String firstName, String lastName,
                int gender, String email, String phone, String address) {

        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public User(int id, String username, int role, String firstName, String lastName,
                int gender, String email, String phone, String address) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public int addUser() {
        return userMapper.addUser(this);
    }

    public static User getUser(String username) {
        return userMapper.getUser(username);
    }

    public int setDegreeOfStudent(int degreeId) {
        return studentMapper.setDegreeOfStudent(this, degreeId);
    }

    public static List<User> getUserList() {
        return userMapper.getUserList();
    }

    public static int deleteUsers(String[] username) {
        return userMapper.deleteUsers(username);
    }

    public int updateUser() {
        UnitOfWork.newCurrent();
        UnitOfWork.getCurrent().registerDirty(this);
        return UnitOfWork.getCurrent().commit();
    }

    public int resetPassword() {
        return userMapper.resetPassword(this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ArrayList<Integer> getSubjects() {
        return subjects;
    }

    public void setSubjects(ArrayList<Integer> subjects) {
        this.subjects = subjects;
    }

    public ArrayList<Integer> getDegrees() {
        return degrees;
    }

    public void setDegrees(ArrayList<Integer> degrees) {
        this.degrees = degrees;
    }
}
