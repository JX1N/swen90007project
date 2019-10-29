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

@WebServlet(urlPatterns = "/degree/delete")
public class DegreeDeleteServlet extends HttpServlet {
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
        int[] ids = gson.fromJson(request.getParameter("ids"), int[].class);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        JsonObject obj = new JsonObject();
        if (degreeService.deleteDegrees(ids) != 0) {
            obj.addProperty("message", "success");
        } else {
            obj.addProperty("message", "failure");
        }
        response.getWriter().write(obj.toString());
    }
}
