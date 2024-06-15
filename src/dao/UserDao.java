package dao;

import domain.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    //注册使用
    int addUser(User user);
    //登录使用
    User getUserByName(String username) ;
    User getUserByNamePassword(String username,String password,int role);
    User getUserById(Integer id);


    //修改使用
    int updateUser(User user);
    //删除使用
    int deleteUser(User user);
    //查询用户
    List<User> getUsers(User user);

}
