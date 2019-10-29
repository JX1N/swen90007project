package connection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class Util {
    public static int runUpdate(String sql, Connection conn) {
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            conn.commit();
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (Exception rollback) {
                System.out.println("Roll back failed.");
            }
        } finally {
            DBConnection.closeConnection(conn);
        }
        return 0;
    }
}
