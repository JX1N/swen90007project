package controller;

import domain.user.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import utils.AppSession;
import service.LoginService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet{
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UsernamePasswordToken passwordToken=new UsernamePasswordToken(username, password);
        passwordToken.setRememberMe(false);
        Subject subject = SecurityUtils.getSubject();

        try {
            subject.login(passwordToken);
            if(subject.hasRole("0")) {
                response.sendRedirect("admin?page=degree");
            } else {
                response.sendRedirect("student?page=enrollment");
            }
        } catch (AuthenticationException e) {
            e.printStackTrace();
            request.setAttribute("fail", true);
            request.getRequestDispatcher("/").forward(request, response);
        }
    }
}
