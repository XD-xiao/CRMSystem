package Test;

import dao.UserDao;
import dao.impl.UserDaoImpl;
import domain.User;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

public class UserDaoImplTest {

    UserDao userDao = new UserDaoImpl();
    @Test
    public void addtest()
    {
        UserDao userDao = new UserDaoImpl();
        User user = new User();
        user.setUsername("FFF");
        user.setUserpassword("123456");
        user.setrole(2);
        user.setSex("男");
        user.setPhone("123456789");
        user.setAddress("上海");
        user.setAge(30);
        user.setCreditrating(0);

        if(userDao.addUser(user)==1){
            System.out.println("添加成功");
        }
    }

    @Test
    public void getUserByName() throws SQLException {
      User user = userDao.getUserByName("Joker");
      System.out.println(user);

    }

    @Test
    public void getUserByNamePassword() throws SQLException {
        User user = userDao.getUserByNamePassword("Joker","123456",1);
        System.out.println(user);
    }

    @Test
    void getUsers() throws SQLException {
        User user = new User();
        user.setUsername("客户11");
        List<User> users = userDao.getUsers(user);
        for (User user1 : users) {
            System.out.println(user1);
        }
    }

    @Test
    void updateUser()  {
        User user = new User();
        user.setId(4);
        user.setUsername("员工");
        user.setUserpassword("123456");
        user.setrole(2);
        user.setSex("男");
        user.setPhone("123456789");
        user.setAddress("江苏南京");
        user.setAge(20);
        user.setCreditrating(10);
        if(userDao.updateUser(user)==1){
            System.out.println("修改成功");
        }
    }
    @Test
    void deleteUser() throws SQLException {
        User user = new User();
        user.setId(8);
        if(userDao.deleteUser(user)==1){
            System.out.println("删除成功");
        }
    }
}
