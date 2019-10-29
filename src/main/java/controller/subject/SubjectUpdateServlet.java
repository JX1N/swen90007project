package controller.subject;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import domain.Subject;
import service.SubjectService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/subject/update")
public class SubjectUpdateServlet extends HttpServlet{
    private SubjectService subjectService;
    private Gson gson;

    @Override
    public void init() {
        subjectService = SubjectService.getInstance();
        gson = new Gson();
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Subject subject = gson.fromJson(req.getParameter("subject"), Subject.class);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        JsonObject obj = new JsonObject();
        if (subjectService.updateSubject(subject) != 0) {
            obj.addProperty("message", "success");
        } else {
            obj.addProperty("message", "failure");
        }
        resp.getWriter().write(obj.toString());
    }
}
