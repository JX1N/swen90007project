package controller.user;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import domain.user.User;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/add")
public class UserAddServlet extends HttpServlet {
    private UserService userService;
    private Gson gson;

    @Override
    public void init() {
        userService = UserService.getInstance();
        gson = new Gson();
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = gson.fromJson(request.getParameter("user"), User.class);
        int degreeId;
        degreeId = Integer.valueOf(request.getParameter("degreeId"));

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        JsonObject obj = new JsonObject();
        if (userService.addUser(user, degreeId) != 0) {
            obj.addProperty("message", "success");
        } else {
            obj.addProperty("message", "failure");
        }
        response.getWriter().write(obj.toString());
    }
}
