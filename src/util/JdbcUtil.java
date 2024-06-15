package util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;

public class JdbcUtil {
    private static final String driver;
    private static final String url;
    private static final String username;
    private static final String password;
    private Connection conn = null;

    private PreparedStatement pstmt = null;
    private ResultSet rs = null;

    //返回查询到的结果记录集
    public ResultSet executeQuery(String sql, Object[] params) {
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            if(params != null){
                for(int i = 0; i < params.length; i++)
                    pstmt.setObject(i + 1, params[i]);
            }
            rs = pstmt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }

    //查询得到多个数据
    public List<Map<String,Object>> executeQueryList(String sql,Object[] params){
        List<Map<String, Object>> list = new ArrayList<>();
        rs=executeQuery(sql,params);
        try {
            ResultSetMetaData rsmd=rs.getMetaData();
            int columnCount=rsmd.getColumnCount();
            while(rs.next()){
                Map<String, Object> map = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    map.put(rsmd.getColumnLabel(i),rs.getObject(i));
                }
                list.add(map);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            jdbcUtil.closeAll();
        }
        return list;
    }

    public Map<String,Object> executeQuerySingle(String sql,Object[] params){
        rs=executeQuery(sql,params);
        Map<String, Object> map = new HashMap<>();
        try {
            ResultSetMetaData rsmd=rs.getMetaData();
            int columnCount=rsmd.getColumnCount();
            if(rs.next()){
                for (int i = 1; i <= columnCount; i++) {
                    map.put(rsmd.getColumnLabel(i),rs.getObject(i));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            jdbcUtil.closeAll();
        }
        return map;
    }


    // 封装增删改
    public int executeUpdate(String sql, Object[] params)  {
        int effectLine = 0;
        try{
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            if(params != null){
                for(int i = 0; i < params.length; i++)
                    pstmt.setObject(i + 1, params[i]);
                effectLine = pstmt.executeUpdate();
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        } finally {
            jdbcUtil.closeAll();
        }

        return effectLine;
    }



    private static JdbcUtil jdbcUtil = null;//单例
    private JdbcUtil() {
    }

    static {
        InputStream input = JdbcUtil.class.getClassLoader().getResourceAsStream("db.properties");
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

    public static JdbcUtil getJdbcUtil() {
        if (jdbcUtil == null) {
            synchronized (JDBConnect.class){
                if (jdbcUtil == null)
                    jdbcUtil = new JdbcUtil();
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

    public void closeAll()  {
        if (conn != null) {
            if(rs!=null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if(pstmt!=null){
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


}
