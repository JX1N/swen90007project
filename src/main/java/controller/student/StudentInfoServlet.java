package controller.student;

import com.google.gson.Gson;
import domain.user.Student;
import domain.user.User;
import service.StudentService;
import service.UserService;
import utils.AppSession;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Jiaxin Li
 * Date: 2018/9/27
 * Time: 17:38
 * Mail:star_1017@outlook.com
 */

@WebServlet("/student/info")
public class StudentInfoServlet extends HttpServlet {
    private StudentService studentService;
    private UserService userService;
    private Gson gson;

    @Override
    public void init() {
        studentService=StudentService.getInstance();
        userService=UserService.getInstance();
        gson = new Gson();
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = AppSession.getUser();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Student student=studentService.getStudentById(user.getId());
        String json = gson.toJson(student);
        response.getWriter().write(json);
    }
}
