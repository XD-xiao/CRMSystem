package dao.impl;

import dao.ProductDao;
import domain.Product;
import util.ToolUtil;
import util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDaoImpl  implements ProductDao {
    private JdbcUtil jdbcUtil = JdbcUtil.getJdbcUtil();
    private PreparedStatement pstmt = null;
    private Connection conn = null;

    ResultSet rs = null;

    @Override
    public int addProduct(Product product) {
        String sql = "insert into productlist(name,total,staffid,price,productiondate,type,text,imgurl) values(?,?,?,?,?,?,?,?)";
        Object[] params = new Object[]{
                product.getName(),
                product.getTotal(),
                product.getStaffid(),
                product.getPrice(),
                product.getProductiondata(),
                product.getType(),
                product.getText(),
                product.getImgurl()
        };
        return jdbcUtil.executeUpdate(sql, params);
    }

    @Override
    public Product getProductByid(Integer id) {
        String sql = "select * from productlist where id=?";
        Map<String, Object> map = jdbcUtil.executeQuerySingle(sql, new Object[]{id});
        Product product = null;
        if(!map.isEmpty()){
            product = new Product();
            product.setId((Integer) map.get("id"));
            product.setName((String) map.get("name"));
            product.setTotal((Integer) map.get("total"));
            product.setStaffid((Integer) map.get("staffid"));
            product.setPrice((Integer) map.get("price"));
            product.setProductiondata((String) map.get("productiondate"));
            product.setType((String) map.get("type"));
            product.setText((String) map.get("text"));
            product.setImgurl((String) map.get("imgurl"));
        }

        return product;
    }

    @Override
    public Product getProductByStaffidName(String name, Integer staffid) {
        String sql = "select * from productlist where staffid=? and name=?";
        Map<String, Object> map = jdbcUtil.executeQuerySingle(sql, new Object[]{staffid, name});
        Product product = null;
        if(!map.isEmpty()){
            product = new Product();
            product.setId((Integer) map.get("id"));
            product.setName((String) map.get("name"));
            product.setTotal((Integer) map.get("total"));
            product.setStaffid((Integer) map.get("staffid"));
            product.setPrice((Integer) map.get("price"));
            product.setProductiondata((String) map.get("productiondate"));
            product.setType((String) map.get("type"));
            product.setText((String) map.get("text"));
            product.setImgurl((String) map.get("imgurl"));
        }

        return product;
    }

    @Override
    public int updateProduct(Product product) {
        String sql = "update productlist set name=?, total=?,price=?,productiondate=?,type=?,text=?,imgurl=? where id=?";

        Object[] params = new Object[]{

                product.getName(),
                product.getTotal(),
                product.getPrice(),
                product.getProductiondata(),
                product.getType(),
                product.getText(),
                product.getImgurl(),
                product.getId()
        };
        return jdbcUtil.executeUpdate(sql, params);
    }

    @Override
    public int deleteProduct(Product product){
        String sql = "delete from productlist where id=?";
        Object[] params = new Object[]{product.getId()};
        return jdbcUtil.executeUpdate(sql, params);
    }

    @Override
    public int deleteProductByStaffid(Integer staffid) {
        String sql = "delete from productlist where staffid=?";
        Object[] params = new Object[]{staffid};
        return jdbcUtil.executeUpdate(sql, params);
    }

    @Override
    public List<Product> getProducts(Product product) {
        StringBuffer sb = new StringBuffer("select * from productlist where true");
        if (!ToolUtil.isEmpty(product.getName())) {
            sb.append(" and name like '%")
                    .append(product.getName())
                    .append("%'");
        }
        if (!ToolUtil.isEmpty(product.getType())) {
            sb.append(" and type like '%")
                    .append(product.getType())
                    .append("%'");
        }
        if (product.getStaffid() != 0) {
            sb.append(" and staffid =")
                    .append(product.getStaffid());
        }
        List<Map<String, Object>> maps = jdbcUtil.executeQueryList(sb.toString(), null);
        List<Product> products = new ArrayList<>();

        
        for (int i = 0; i < maps.size(); i++) {
            Map<String, Object> map = maps.get(i);
            product = new Product();
            product.setId((Integer) map.get("id"));
            product.setName((String) map.get("name"));
            product.setTotal((Integer) map.get("total"));
            product.setStaffid((Integer) map.get("staffid"));
            product.setPrice((Integer) map.get("price"));
            product.setProductiondata((String) map.get("productiondate"));
            product.setType((String) map.get("type"));
            product.setText((String) map.get("text"));
            product.setImgurl((String) map.get("imgurl"));

            products.add(product);
        }
        return products;
    }
}
