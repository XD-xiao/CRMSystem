package dao.impl;

import dao.OrderDao;
import domain.Order;
import util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderDaoImpl implements OrderDao {
    private JdbcUtil jdbcUtil = JdbcUtil.getJdbcUtil();
    private PreparedStatement pstmt = null;
    private Connection conn = null;

    ResultSet rs = null;

    @Override
    public int addOrder(Order order){
        String sql = "insert into orderlist(userid,staffid,purchasedate,productid,buynumber,totalamount,status,productname) values(?,?,?,?,?,?,?,?)";
        Object[] params = new Object[]{
                order.getUserid(),
                order.getStaffid(),
                order.getPurchasedate(),
                order.getProductid(),
                order.getBuynumber(),
                order.getTotalamount(),
                order.getStatus(),
                order.getProductname()
        };
        return jdbcUtil.executeUpdate(sql, params);
    }

    @Override
    public Order getOrderByid(Integer id){
        String sql = "select * from orderlist where id=?";
        Map<String, Object> map = jdbcUtil.executeQuerySingle(sql, new Object[]{id});
        Order order = null;
        if(!map.isEmpty()){
            order = new Order();
            order.setId((Integer) map.get("id"));
            order.setUserid((Integer) map.get("userid"));
            order.setStaffid((Integer) map.get("staffid"));
            order.setPurchasedate((String) map.get("purchasedate"));
            order.setProductid((Integer) map.get("productid"));
            order.setBuynumber((Integer) map.get("buynumber"));
            order.setTotalamount((Integer) map.get("totalamount"));
            order.setStatus((Integer) map.get("status"));
            order.setProductname((String) map.get("productname"));

        }

        return order;
    }


    @Override
    public int updateOrder(Order order){
        String sql = "update orderlist set userid=?, staffid=?,purchasedate=?,productid=?,buynumber=?,totalamount=? ,status=?,productname=? where id=?";

        Object[] params = new Object[]{

                order.getUserid(),
                order.getStaffid(),
                order.getPurchasedate(),
                order.getProductid(),
                order.getBuynumber(),
                order.getTotalamount(),
                order.getStatus(),
                order.getProductname(),
                order.getId()
        };
        return jdbcUtil.executeUpdate(sql, params);
    }

    @Override
    public int deleteOrder(Order order){
        String sql = "delete from orderlist where id=?";
        Object[] params = new Object[]{order.getId()};
        return jdbcUtil.executeUpdate(sql, params);
    }
    @Override
    public int deleteOrderByStaffid(Integer staffid){
        return jdbcUtil.executeUpdate("delete from orderlist where staffid=?", new Object[]{staffid});
    }
    @Override
    public int deleteOrderByUserid(Integer userid){
        return jdbcUtil.executeUpdate("delete from orderlist where userid=?", new Object[]{userid});
    }
    @Override
    public int deleteOrderByProductid(Integer productid){
        return jdbcUtil.executeUpdate("delete from orderlist where productid=?", new Object[]{productid});
    }

    @Override
    public List<Order> getOrders(Order order){
        StringBuffer sb = new StringBuffer("select * from orderlist where true ");

        if (order.getUserid() != null) {
            sb.append("and userid like '%")
                    .append(order.getUserid())
                    .append("%'");
        }
        if (order.getStaffid() != null) {
            sb.append("and staffid like '%")
                    .append(order.getStaffid())
                    .append("%'");
        }
        if (order.getProductid() != null) {
            sb.append("and productid like '%")
                    .append(order.getProductid())
                    .append("%'");
        }
        if (order.getStatus() != null) {
            sb.append("and status like '%")
                    .append(order.getStatus())
                    .append("%'");
        }
        if (order.getProductname() != null) {
            sb.append("and productname like '%")
                    .append(order.getProductname())
                    .append("%'");
        }

        List<Map<String, Object>> maps = jdbcUtil.executeQueryList(sb.toString(), null);
        List<Order> orders = new ArrayList<>();

        for (int i = 0; i < maps.size(); i++) {
            Map<String, Object> map = maps.get(i);
            order = new Order();
            order.setId((Integer) map.get("id"));
            order.setUserid((Integer) map.get("userid"));
            order.setStaffid((Integer) map.get("staffid"));
            order.setPurchasedate((String) map.get("purchasedate"));
            order.setProductid((Integer) map.get("productid"));
            order.setBuynumber((Integer) map.get("buynumber"));
            order.setTotalamount((Integer) map.get("totalamount"));
            order.setStatus((Integer) map.get("status"));
            order.setProductname((String) map.get("productname"));

            orders.add(order);
        }
        return orders;
    }
}
