package util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBConnect {
    private static final String driver;
    private static final String url;
    private static final String username;
    private static final String password;
    private Connection conn = null;



    private static JDBConnect jdbcUtil = null;//单例
    private JDBConnect() {
    }

    static {
        InputStream input = JDBConnect.class.getClassLoader().getResourceAsStream("db.properties");
        Properties properties = new Properties();
        try {
            properties.load(input);
            driver = properties.getProperty("jdbc.driver");
            url = properties.getProperty("jdbc.url");
            username = properties.getProperty("jdbc.username");
            password = properties.getProperty("jdbc.password");
            Class.forName(driver);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static JDBConnect getJdbcUtil() {
        if (jdbcUtil == null) {
            synchronized (JDBConnect.class){
                if (jdbcUtil == null)
                    jdbcUtil = new JDBConnect();
            }
        }
        return jdbcUtil;
    }

    public Connection getConnection() {
        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }

    public void closeAll() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

//    public static void main(String[] args) {
//        JDBConnect jdbcUtil = JDBConnect.getJdbcUtil();
//        Connection conn = jdbcUtil.getConnection();
//        System.out.println(conn);
//        jdbcUtil.closeAll();
//    }


}
