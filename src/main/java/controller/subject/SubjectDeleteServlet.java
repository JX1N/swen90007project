package controller.subject;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import service.SubjectService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/subject/delete")
public class SubjectDeleteServlet extends HttpServlet{
    private SubjectService subjectService;
    private Gson gson;

    @Override
    public void init() {
        subjectService = SubjectService.getInstance();
        gson = new Gson();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int[] ids = gson.fromJson(req.getParameter("ids"), int[].class);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        JsonObject obj = new JsonObject();
        if (subjectService.deleteSubject(ids) != 0) {
            obj.addProperty("message", "success");
        } else {
            obj.addProperty("message", "failure");
        }
        resp.getWriter().write(obj.toString());
    }
}
