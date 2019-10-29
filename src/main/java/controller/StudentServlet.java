package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Jiaxin Li
 * Date: 2018/9/28
 * Time: 16:57
 * Mail:star_1017@outlook.com
 */
@WebServlet("/student")
public class StudentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String page = (String) req.getParameter("page");
        req.getRequestDispatcher("/WEB-INF/jsp/student/" + page + ".jsp").forward(req, resp);
    }
}

