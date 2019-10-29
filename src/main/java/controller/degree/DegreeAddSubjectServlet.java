package controller.degree;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import service.DegreeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/degree/addSubject")
public class DegreeAddSubjectServlet extends HttpServlet {
    private DegreeService degreeService;
    private Gson gson;

    @Override
    public void init() {
        degreeService = DegreeService.getInstance();
        gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int[] subjectIds = gson.fromJson(req.getParameter("subjectIds"), int[].class);
        int degreeId = Integer.valueOf(req.getParameter("degreeId"));

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        JsonObject obj = new JsonObject();
        if (degreeService.addSubjectToDegree(subjectIds, degreeId) != 0) {
            obj.addProperty("message", "success");
        } else {
            obj.addProperty("message", "failure");
        }

        resp.getWriter().write(obj.toString());
    }
}
