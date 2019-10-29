package controller.student;

import com.google.gson.Gson;
import domain.user.Student;
import service.StudentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Jiaxin Li
 * Date: 2018/9/8
 * Time: 0:24
 * Mail:star_1017@outlook.com
 */
@WebServlet("/student/detail")
public class StudentDetailServlet extends HttpServlet {
    private StudentService studentService;
    private Gson gson;

    @Override
    public void init() {
        studentService = StudentService.getInstance();
        gson = new Gson();
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        int id =  Integer.parseInt(request.getParameter("id"));
        Student student= studentService.getStudentById(id);
        String json = gson.toJson(student);
        response.getWriter().write(json);
    }
}