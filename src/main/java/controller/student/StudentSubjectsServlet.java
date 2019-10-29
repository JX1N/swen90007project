package controller.student;

import com.google.gson.Gson;
import domain.Subject;
import domain.user.Student;
import domain.user.User;
import dto.StudentSubjectDTO;
import dto.SubjectDTO;
import service.StudentService;
import service.UserService;
import utils.AppSession;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/student/subjects")
public class StudentSubjectsServlet extends HttpServlet{
    private StudentService studentService;
    private UserService userService;
    private Gson gson;

    @Override
    public void init() {
        studentService = StudentService.getInstance();
        userService = userService.getInstance();
        gson = new Gson();
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = AppSession.getUser().getUsername();
        User user = userService.getUser(username);
        ArrayList<Subject> subjects = Student.getSubjectsOfStudent(username);
        ArrayList<SubjectDTO> subjectDTOs = new ArrayList<>();
        for (Subject subject : subjects) {
            subjectDTOs.add(subject.transformToDTO());
        }

        StudentSubjectDTO studentSubjectDTO = new StudentSubjectDTO(user.getId(), username,
                subjectDTOs, Student.getDegreesOfStudent(username));
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String json = gson.toJson(studentSubjectDTO);
        response.getWriter().write(json);
    }
}
