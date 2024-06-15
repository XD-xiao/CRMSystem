package Test;

import dao.ProductDao;
import dao.impl.ProductDaoImpl;
import domain.Product;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

public class ProductDaoImplTest {
    ProductDao productDao = new ProductDaoImpl();
    @Test
    public void addtest()
    {
        Product product = new Product();
        product.setName("鼠标");
        product.setTotal(100);
        product.setStaffid(3);
        product.setPrice(50);
        product.setProductiondata("2020-01-01");
        product.setType("数码");
        product.setText("鼠标鼠标鼠标");
        product.setImgurl("");

        if(productDao.addProduct(product)==1){
            System.out.println("添加成功");
        }


    }
    @Test
    public void getproductBystaffidtest() throws SQLException {
        Product product = productDao.getProductByid(1);
        System.out.println(product);
    }


    @Test
    public void updatetest() throws SQLException {
        Product product = new Product();
        product.setId(1);
        product.setName("苹果");
        product.setTotal(100);
        product.setStaffid(1);
        product.setPrice(100);
        product.setProductiondata("2020-01-01");
        product.setType("水果");
        product.setText("大苹果啊大苹果");
        if(productDao.updateProduct(product)==1){
            System.out.println("修改成功");
        }
    }
    @Test
    public void deletetest() throws SQLException {
        Product product = new Product();
        product.setId(8);
        if(productDao.deleteProduct(product)==1){
            System.out.println("删除成功");
        }
    }

    @Test
    public void getproducttest() throws SQLException {
        Product product = new Product();

        List<Product> products = productDao.getProducts(product);
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            System.out.println(p.getId());
        }
    }
}
