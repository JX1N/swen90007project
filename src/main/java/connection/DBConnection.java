package connection;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;

/**
 * Created by Jason Wu on 2018/8/19.
 */
public class DBConnection {
    private static DBConnection dataSource;
    private ComboPooledDataSource cpds;

    private static final String URL = "jdbc:mysql://45.62.101.44:3306/swen90007?useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String USER = "develop";
    private static final String PASSWORD = "swen90007";

    private DBConnection() {
        try {
            cpds = new ComboPooledDataSource();
            cpds.setDriverClass("com.mysql.jdbc.Driver");
            cpds.setJdbcUrl(URL);
            cpds.setUser(USER);
            cpds.setPassword(PASSWORD);

            cpds.setMinPoolSize(5);
            cpds.setAcquireIncrement(5);
            cpds.setMaxPoolSize(20);
            cpds.setMaxStatements(180);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("DB connection failed");
        }

    }

    public static DBConnection getInstance() {
        try {
            if (dataSource == null) {
                synchronized (DBConnection.class) {
                    if (dataSource == null) {
                        dataSource = new DBConnection();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("DB connection failed");
        }
        System.out.println(dataSource);
        return dataSource;
    }

    public Connection getConnection() {
        Connection conn = null;
        try {
            conn = this.cpds.getConnection();
            conn.setAutoCommit(false);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("DB connection failed");
        }
        System.out.println(conn);
        return conn;
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (Exception e) {
                System.out.println("Close connection failed");
                e.printStackTrace();
            }
        }
    }
}
