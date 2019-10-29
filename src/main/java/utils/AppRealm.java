package utils;

import domain.user.User;
import org.apache.shiro.realm.jdbc.JdbcRealm;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Jiaxin Li
 * Date: 2018/10/5
 * Time: 21:37
 * Mail:star_1017@outlook.com
 */
public class AppRealm extends JdbcRealm {
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // identify account to log to
        UsernamePasswordToken userPassToken = (UsernamePasswordToken) token;
        final String username = userPassToken.getUsername();

        final User user = User.getUser(username);
        if (user == null) {
            System.out.println("No account found for user with username " + username);
            return null;
        }

        return new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), getName());

    }

    @Override
    protected AuthorizationInfo getAuthorizationInfo(PrincipalCollection principals) {
        Set<String> roles = new HashSet<>();
        if (principals.isEmpty()) {
            System.out.println("Given principals to authorize are empty.");
            return null;
        }

        String username =principals.getPrimaryPrincipal().toString();
        final User user = User.getUser(username);
        AppSession.init(user);

        if (user == null) {
            System.out.println("No account found for user with username " + username);
            return null;
        }

        // add roles of the user according to its type
        if (user.getRole()==0) {
            roles.add(AppSession.ADMIN_ROLE);
        } else if (user.getRole()==1) {
            roles.add(AppSession.STUDENT_ROLE);
        }

        return new SimpleAuthorizationInfo(roles);
    }
}
