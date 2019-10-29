package controller.degree;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import domain.Degree;
import service.DegreeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/degree/add")
public class DegreeAddServlet extends HttpServlet {
    private DegreeService degreeService;
    private Gson gson;

    @Override
    public void init() {
        degreeService = DegreeService.getInstance();
        gson = new Gson();
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Degree degree = gson.fromJson(request.getParameter("degree"), Degree.class);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        JsonObject obj = new JsonObject();
        if (degreeService.addDegree(degree) != 0) {
            obj.addProperty("message", "success");
        } else {
            obj.addProperty("message", "failure");
        }
        response.getWriter().write(obj.toString());
    }
}
