package service;

import domain.Subject;
import domain.user.Student;
import mapper.StudentMapper;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Jiaxin Li
 * Date: 2018/9/7
 * Time: 23:47
 * Mail:star_1017@outlook.com
 */
public class StudentService {
    private static StudentService instance;
    private StudentMapper studentMapper;

    private StudentService() {
        studentMapper = StudentMapper.getInstance();
    }

    public static StudentService getInstance() {
        if (instance == null) {
            synchronized (SubjectService.class) {
                if (instance == null) {
                    instance = new StudentService();
                }
            }
        }
        return instance;
    }


    public Student getStudentById(int id){

        return Student.getStudentById(id);

    }

    public ArrayList<Integer> getDegreeOfStudent(String username) {
        return Student.getDegreesOfStudent(username);
    }

    public ArrayList<Subject> getSubjectsOfStudent(String username) {
        return Student.getSubjectsOfStudent(username);
    }

    public int studentEnroll(String username, int subjectId) {
        return Student.studentEnroll(username, subjectId);
    }

    public int studentWithdraw(String username, int subjectId) {
        return Student.studentWithdraw(username, subjectId);
    }
}
