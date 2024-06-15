package Test;

import com.mysql.cj.jdbc.Driver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnectionTest {
    private static final String DRIVER = "com.mysql.jdbc.Driver";

    private static final String URL = "jdbc:mysql://localhost:3306/crmdb?serverTimezone=GMT%2B8 " +
            "&useUnicode=true & characterEncoding=utf-8";
    private static final String USERNAME = "root"; //置换成自己数据库的用户名和密码
    private static final String PASSWORD="Mysql123456";

    private static Connection conn = null;

    @Test
    void test01Connection(){
        try{
            Driver driver = new Driver();

            Properties properties = new Properties();
            properties.setProperty("user",USERNAME);
            properties.setProperty("password",PASSWORD);

            conn = driver.connect(URL,properties);
            if(conn != null)
                System.out.println("连接成功");
            else
                System.out.println("连接失败");

        }catch( SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Test
    void test02Connection() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
         Class clazz = Class.forName(DRIVER);
         Driver driver = (Driver) clazz.newInstance();

         Properties properties = new Properties();
         properties.setProperty("user",USERNAME);
         properties.setProperty("password",PASSWORD);

         try{
             conn = driver.connect(URL,properties);
             if(conn != null)
                 System.out.println("连接成功");
             else
                 System.out.println("连接失败");
         }catch( SQLException e){
             throw new RuntimeException(e);
         }
    }

    @Test
    void test03Connection() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        Class clazz = Class.forName(DRIVER);
        Driver driver = (Driver) clazz.newInstance();
        DriverManager.registerDriver(driver);

        try{
            conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            if(conn != null)
                System.out.println("连接成功");
            else
                System.out.println("连接失败");
        }catch( SQLException e){
            throw new RuntimeException(e);
        }

    }

    @Test
    void test04Connection() throws ClassNotFoundException {

        Class.forName(DRIVER);
        try{
            conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            if(conn != null)
                System.out.println("连接成功");
            else
                System.out.println("连接失败");
        }catch( SQLException e){
            throw new RuntimeException(e);
        }
    }


    @Test
    void test05Connection() throws IOException, SQLException, ClassNotFoundException {

        //        InputStream input = Test.DBConnectionTest.class.getClassLoader().getResourceAsStream("db.properties");
        InputStream input= this.getClass().getResourceAsStream("/db.properties");
        Properties properties=new Properties();
        properties.load(input);
        String driver = properties.getProperty("jdbc.driver");
        String url = properties.getProperty("jdbc.url");
        String user = properties.getProperty("jdbc.username");
        String password = properties.getProperty("jdbc.password");
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);

            if(conn != null)
                System.out.println("连接成功");
            else
                System.out.println("连接失败");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @AfterEach
    void afterEach(){
        if(conn != null)
            try{
                conn.close();
            }catch(SQLException e){
                throw new RuntimeException(e);
            }
    }

}

