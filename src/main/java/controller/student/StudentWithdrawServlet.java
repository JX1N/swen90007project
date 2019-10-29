package controller.student;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import service.StudentService;
import utils.AppSession;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/student/withdraw")
public class StudentWithdrawServlet extends HttpServlet{
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
        String username = AppSession.getUser().getUsername();
        int subjectId = Integer.parseInt(request.getParameter("subjectId"));
        int result = studentService.studentWithdraw(username, subjectId);
        JsonObject obj = new JsonObject();
        if (result == 1) {
            obj.addProperty("message", "success");
        } else {
            obj.addProperty("message", "failure");
        }
        response.getWriter().write(obj.toString());
    }
}
