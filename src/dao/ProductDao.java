package dao;

import domain.Product;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface ProductDao {
    int addProduct(Product product) ;
    //根据id查询
    Product getProductByid(Integer id);
    Product getProductByStaffidName(String name, Integer staffid);
    int updateProduct(Product product);
    int deleteProduct(Product product);
    int deleteProductByStaffid(Integer staffid);

    //遍历
    List<Product> getProducts(Product product);
}
