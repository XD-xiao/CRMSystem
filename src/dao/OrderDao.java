package dao;

import domain.Order;

import java.sql.SQLException;
import java.util.List;

public interface OrderDao {

    int addOrder(Order order);
    Order getOrderByid(Integer id);
    int updateOrder(Order order);
    int deleteOrder(Order order);
    int deleteOrderByStaffid(Integer staffid);
    int deleteOrderByUserid(Integer userid);
    int deleteOrderByProductid(Integer productid);
    List<Order> getOrders(Order order);

}
