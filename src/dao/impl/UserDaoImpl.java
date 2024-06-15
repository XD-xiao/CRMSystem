package dao.impl;

import dao.UserDao;
import util.ToolUtil;
import domain.User;
import util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserDaoImpl implements UserDao {

    private JdbcUtil jdbcUtil = JdbcUtil.getJdbcUtil();
    private PreparedStatement pstmt = null;
    private Connection conn = null;

    ResultSet rs = null;

    @Override
    public int addUser(User user) {
        String sql = "insert into users(role,username,userpassword,age,sex,phone,address,creditrating) values(?,?,?,?,?,?,?,?)";
        Object[] params = new Object[]{
                user.getrole(),
                user.getUsername(),
                user.getUserpassword(),
                user.getAge(),
                user.getSex(),
                user.getPhone(),
                user.getAddress(),
                user.getCreditrating()
        };
        return jdbcUtil.executeUpdate(sql, params);
    }

    @Override
    public User getUserByName(String username) {                //只通过名字获取用户的所有信息，不安全
        String sql = "select * from users where username=?";
        Map<String, Object> map = jdbcUtil.executeQuerySingle(sql, new Object[]{username});
        User user = null;
        if (!map.isEmpty()) {
            user = new User();
            int id = (Integer) map.get("id");
            int role = (Integer) map.get("role");
            String userName = (String) map.get("username");
            String password = (String) map.get("userpassword");
            int age = (Integer) map.get("age");
            String sex = (String) map.get("sex");
            String phone = (String) map.get("phone");
            String address = (String) map.get("address");
            int creditrating = (Integer) map.get("creditrating");

            user.setId(id);
            user.setrole(role);
            user.setUsername(userName);
            user.setPhone(phone);
            user.setUserpassword(password);
            user.setSex(sex);
            user.setAddress(address);
            user.setAge(age);
            user.setCreditrating(creditrating);
            return user;
        }
        return null;
    }


    @Override
    public User getUserByNamePassword(String username, String password, int role) {
        String sql = "select * from users where username=? and userpassword=? and role=?";
        Map<String, Object> map = jdbcUtil.executeQuerySingle(sql, new Object[]{username, password, role});
        User user = null;
        if (!map.isEmpty()) {
            user = new User();
            int id = (Integer) map.get("id");
            int role1 = (Integer) map.get("role");
            String userName = (String) map.get("username");
            String passWord = (String) map.get("userpassword");
            int age = (Integer) map.get("age");
            String sex = (String) map.get("sex");
            String phone = (String) map.get("phone");
            String address = (String) map.get("address");
            int creditrating = (Integer) map.get("creditrating");

            user.setId(id);
            user.setrole(role1);
            user.setUsername(userName);
            user.setPhone(phone);
            user.setUserpassword(passWord);
            user.setSex(sex);
            user.setAddress(address);
            user.setAge(age);
            user.setCreditrating(creditrating);
            return user;
        }
        return null;
    }

    @Override
    public User getUserById(Integer id) {
        String sql = "select * from users where id=?";
        Map<String, Object> map = jdbcUtil.executeQuerySingle(sql, new Object[]{id});
        User user = null;
        if (!map.isEmpty()) {
            user = new User();
            int id1 = (Integer) map.get("id");
            int role1 = (Integer) map.get("role");
            String userName = (String) map.get("username");
            String passWord = (String) map.get("userpassword");
            int age = (Integer) map.get("age");
            String sex = (String) map.get("sex");
            String phone = (String) map.get("phone");
            String address = (String) map.get("address");
            int creditrating = (Integer) map.get("creditrating");

            user.setId(id1);
            user.setrole(role1);
            user.setUsername(userName);
            user.setPhone(phone);
            user.setUserpassword(passWord);
            user.setSex(sex);
            user.setAddress(address);
            user.setAge(age);
            user.setCreditrating(creditrating);
            return user;
        }
        return null;
    }

    @Override
    public int updateUser(User user) {
        String sql = "update users set role=?, username=?,userpassword=?,age=?,sex=?,phone=?,address=?,creditrating=? where id=?";

        Object[] params = new Object[]{
    //            user.getId(),                   //顺序
                user.getrole(),
                user.getUsername(),
                user.getUserpassword(),
                user.getAge(),
                user.getSex(),
                user.getPhone(),
                user.getAddress(),
                user.getCreditrating(),
                user.getId()
        };
        return jdbcUtil.executeUpdate(sql, params);
    }

    @Override
    public int deleteUser(User user){
        String sql = "delete from users where id=?";
        Object[] params = new Object[]{user.getId()};
        return jdbcUtil.executeUpdate(sql, params);
    }

    @Override
    public List<User> getUsers(User user){
        StringBuffer sb = new StringBuffer("select * from users where true");
        if (!ToolUtil.isEmpty(user.getUsername())) {
            sb.append(" and username like '%")
                    .append(user.getUsername())
                    .append("%'");
        }
        if (!ToolUtil.isEmpty(user.getPhone())) {
            sb.append(" and phone like '%")
                    .append(user.getPhone())
                    .append("%'");
        }
        if (!ToolUtil.isEmpty(user.getSex())) {
            sb.append(" and sex like '%")
                    .append(user.getSex())
                    .append("%'");
        }
        if (!ToolUtil.isEmpty(user.getAddress())) {
            sb.append(" and address like '%")
                    .append(user.getAddress())
                    .append("%'");
        }
        if (user.getrole() != 0) {
            sb.append(" and role = ")
                    .append(user.getrole());
        }
        List<Map<String, Object>> maps = jdbcUtil.executeQueryList(sb.toString(), null);
        //System.out.println(maps.size());
        List<User> users = new ArrayList<>();
        for (int i = 0; i < maps.size(); i++) {
            Map<String, Object> map = maps.get(i);
            user = new User();
            Integer id = (Integer) map.get("id");
            String userName = (String) map.get("username");
            String passWord = (String) map.get("userpassword");
            String sex = (String) map.get("sex");
            int role1 = (Integer) map.get("role");
            String phone = (String) map.get("phone");
            int age = (Integer) map.get("age");
            int creditrating = (Integer) map.get("creditrating");
            String address = (String) map.get("address");


            user.setId(id);
            user.setUsername(userName);
            user.setPhone(phone);
            user.setUserpassword(passWord);
            user.setSex(sex);
            user.setrole(role1);
            user.setAddress(address);
            user.setAge(age);
            user.setCreditrating(creditrating);


            users.add(user);
        }
        return users;
    }
}
