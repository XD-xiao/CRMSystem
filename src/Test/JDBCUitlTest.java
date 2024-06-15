package Test;

import org.junit.jupiter.api.Test;
import util.JDBConnect;

import java.sql.Connection;

public class JDBCUitlTest {

        @Test
        void getConnection(){
                JDBConnect jdbcUtil=JDBConnect.getJdbcUtil();
                Connection conn=jdbcUtil.getConnection();
                System.out.println(conn);

        }
}
