package domain.user;

import domain.Subject;
import mapper.StudentMapper;

import java.util.ArrayList;

public class Student extends User{
    private static StudentMapper studentMapper;

    static {
        studentMapper = StudentMapper.getInstance();
    }

    public Student(int id, String username, String password, int role, String firstName, String lastName, int gender, String email, String phone, String address, String degreeName) {
        super(id, username, password, role, firstName, lastName, gender, email, phone, address);
        this.degreeName = degreeName;
    }

    public static Student getStudentById(int id) {
        return studentMapper.getStudentById(id);
    }

    public static ArrayList<Integer> getDegreesOfStudent(String username) {
        return studentMapper.getDegreesOfStudent(username);
    }

    public static ArrayList<Subject> getSubjectsOfStudent(String username) {
        return studentMapper.getSubjectsOfStudent(username);
    }

    public static int studentEnroll(String username, int subjectId) {
        return studentMapper.studentEnroll(username, subjectId);
    }

    public static int studentWithdraw(String username, int subjectId) {
        return studentMapper.studentWithdraw(username, subjectId);
    }

    public String getDegreeName() {
        return degreeName;
    }

    public void setDegreeName(String degreeName) {
        this.degreeName = degreeName;
    }

    //to implement Embedded value pattern;
    private String degreeName;
}
