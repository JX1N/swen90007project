package utils;
import domain.user.User;
import org.apache.shiro.SecurityUtils;

/**
 * Created by IntelliJ IDEA.
 * User: Jiaxin Li
 * Date: 2018/10/5
 * Time: 21:49
 * Mail:star_1017@outlook.com
 */
public class AppSession {

    public static final String USER_ATTRIBUTE_NAME = "user";
    public static final String ADMIN_ROLE = "0";
    public static final String STUDENT_ROLE = "1";

    public static boolean hasRole(String role) {
        return SecurityUtils.getSubject().hasRole(role);
    }

    public static boolean isAuthenticated() {
        return SecurityUtils.getSubject().isAuthenticated();
    }

    public static void init(User user) {
        SecurityUtils.getSubject().getSession().setAttribute(USER_ATTRIBUTE_NAME, user);
    }

    public static User getUser() {
        return (User) SecurityUtils.getSubject().getSession().getAttribute(USER_ATTRIBUTE_NAME);
    }

}
