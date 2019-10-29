package controller.user;

import com.google.gson.Gson;
import service.StudentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/degree")
public class UserDegreeServlet extends HttpServlet {
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
        Object[] degreeIds = studentService.getDegreeOfStudent(username).toArray();
        String ids = gson.toJson(degreeIds, int[].class);

        response.getWriter().write(ids);
    }
}
