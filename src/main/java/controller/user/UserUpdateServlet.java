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

@WebServlet("/user/update")
public class UserUpdateServlet extends HttpServlet {
    private UserService userService;
    private Gson gson;

    @Override
    public void init() {
        userService = UserService.getInstance();
        gson = new Gson();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = gson.fromJson(req.getParameter("user"), User.class);
        int degreeId = Integer.valueOf(req.getParameter("degreeId"));

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        JsonObject obj = new JsonObject();
        if (userService.updateUser(user, degreeId) == 1) {
            obj.addProperty("message", "success");
        } else {
            obj.addProperty("message", "failure");
        }
        resp.getWriter().write(obj.toString());
    }
}
