package controller.subject;

import com.google.gson.Gson;
import domain.Subject;
import service.DegreeService;
import service.SubjectService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/subject/list")
public class SubjectListServlet extends HttpServlet {
    SubjectService subjectService;
    DegreeService degreeService;
    @Override
    public void init() {
        subjectService = SubjectService.getInstance();
        degreeService = DegreeService.getInstance();
    }
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Used to get all subjects in a degree
        String id = request.getParameter("id");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (id == null) {
            String json = new Gson().toJson(subjectService.getAllSubjects());
            response.getWriter().write(json);
        } else {
            List<Subject> subjects = degreeService.getSubjectListInDegree(id);
            String json = new Gson().toJson(degreeService.getSubjectListInDegree(id));
            response.getWriter().write(json);
        }
    }


    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
