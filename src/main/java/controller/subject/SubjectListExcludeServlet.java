package controller.subject;

import com.google.gson.Gson;
import service.DegreeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/subject/list/exclude")
public class SubjectListExcludeServlet extends HttpServlet {
    DegreeService degreeService;
    @Override
    public void init() {
        degreeService = DegreeService.getInstance();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Used to get all subjects in a degree
        String id = request.getParameter("id");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (id != null) {
            String json = new Gson().toJson(degreeService.getSubjectListExclude(id));
            response.getWriter().write(json);
        }
    }


    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
