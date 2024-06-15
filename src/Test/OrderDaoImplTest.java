package Test;

import dao.impl.OrderDaoImpl;
import org.junit.jupiter.api.Test;
import domain.Order;

import java.sql.SQLException;


public class OrderDaoImplTest {
    OrderDaoImpl orderDao = new OrderDaoImpl();
    @Test
    public void addtest() throws SQLException {
        Order order = new Order();
        order.setUserid(1);
        order.setStaffid(3);
        order.setProductid(4);
        order.setBuynumber(20);
        order.setTotalamount(150);
        order.setPurchasedate("2020-02-02");
        order.setStatus(1);

        if(orderDao.addOrder(order) == 1)
            System.out.println("添加成功");
        else
            System.out.println("添加失败");
    }

    @Test
    public void updatetest() throws SQLException {
        Order order = new Order();
        order.setId(1);
        order.setUserid(1);
        order.setStaffid(2);
        order.setProductid(3);
        order.setBuynumber(10);
        order.setTotalamount(100);
        order.setPurchasedate("2020-01-02");
        order.setStatus(1);
        if(orderDao.updateOrder(order) == 1)
            System.out.println("修改成功");
        else
            System.out.println("修改失败");
    }

    @Test
    public void deletetest() throws SQLException {
        Order order = new Order();
        order.setId(1);
        if(orderDao.deleteOrder(order) == 1)
            System.out.println("删除成功");
        else
            System.out.println("删除失败");
    }

    @Test
    public void getlisttest() throws SQLException {
        Order order = new Order();
        System.out.println(orderDao.getOrders(order));
    }
    @Test
    public void gettest() throws SQLException {
        Order order = new Order();

        System.out.println(orderDao.getOrderByid(2));
    }
}
