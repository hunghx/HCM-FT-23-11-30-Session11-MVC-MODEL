package ra.mvc_model.dao;

import ra.mvc_model.model.Product;
import ra.mvc_model.util.ConnectDB;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl implements IProductDao{
    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        // mở 1 kết nối
        Connection conn = ConnectDB.getConnection();
        try {
            CallableStatement callSt = conn.prepareCall("{call getAllProduct()}");
            // thực thi sql
            ResultSet rs = callSt.executeQuery(); // thực trhi câu lệnh select
            while (rs.next()){
               Product product = new Product(
                       rs.getInt("id"),
                       rs.getString("name"),
                       rs.getDouble("price"),
                       rs.getString("description"),
                       rs.getString("image"),
                       rs.getInt("stock")
               ) ;
               products.add(product);
            }
            return products;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectDB.closeConnection(conn);
        }

    }

    @Override
    public List<Product> searchByName(String keyword) {
        List<Product> products = new ArrayList<>();
        // mở 1 kết nối
        Connection conn = ConnectDB.getConnection();
        try {
            CallableStatement callSt = conn.prepareCall("select * from product like ?");
            // truyền tham số
            callSt.setString(1,"%"+keyword+"%");
            // thực thi sql
            ResultSet rs = callSt.executeQuery(); // thực trhi câu lệnh select
            while (rs.next()){
                Product product = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getString("description"),
                        rs.getString("image"),
                        rs.getInt("stock")
                ) ;
                products.add(product);
            }
            return products;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectDB.closeConnection(conn);
        }
    }

    @Override
    public Product findById(Integer id) {
        Product product = null;
        // mở 1 kết nối
        Connection conn = ConnectDB.getConnection();
        try {
            CallableStatement callSt = conn.prepareCall("select * from product where id =?");
            callSt.setInt(1, id);
            // thực thi sql
            ResultSet rs = callSt.executeQuery(); // thực trhi câu lệnh select
            if (rs.next()){
                 product = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getString("description"),
                        rs.getString("image"),
                        rs.getInt("stock")
                ) ;
            }
            return product;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectDB.closeConnection(conn);
        }
    }

    @Override
    public void save(Product product) {
        // mở 1 kết nối
        Connection conn = ConnectDB.getConnection();
        CallableStatement callSt = null;
        try {
            if (product.getId()==null){
                callSt = conn.prepareCall("insert into product(name, price, description, image, stock) value (?,?,?,?,?)");
                callSt.setString(1,product.getName());
                callSt.setDouble(2,product.getPrice());
                callSt.setString(3,product.getDescription());
                callSt.setString(4,product.getImage());
                callSt.setInt(5,product.getStock());
            }else {
                callSt = conn.prepareCall("update  product set name =?, price=?, description=?, image=?, stock=? where id = ?");
                callSt.setString(1,product.getName());
                callSt.setDouble(2,product.getPrice());
                callSt.setString(3,product.getDescription());
                callSt.setString(4,product.getImage());
                callSt.setInt(5,product.getStock());
                callSt.setInt(6,product.getId());
            }
            callSt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectDB.closeConnection(conn);
        }
    }

    @Override
    public void deleteById(Integer id) {
        Connection conn = ConnectDB.getConnection();

        try {
            CallableStatement callSt = conn.prepareCall("delete from product where id = ?");
            callSt.setInt(1,id);
            callSt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectDB.closeConnection(conn);
        }
    }
}
