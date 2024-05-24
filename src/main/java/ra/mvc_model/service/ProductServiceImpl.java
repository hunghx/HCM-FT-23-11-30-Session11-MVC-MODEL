package ra.mvc_model.service;

import ra.mvc_model.dao.IProductDao;
import ra.mvc_model.dao.ProductDaoImpl;
import ra.mvc_model.model.Product;

import java.util.*;
import java.util.stream.Collectors;

public class ProductServiceImpl implements IProductService{
    private static  final IProductDao productDao = new ProductDaoImpl();

    @Override
    public List<Product> searchByName(String keyword) {
        return productDao.searchByName(keyword);
    }

    @Override
    public List<Product> findAll() {
        return productDao.findAll();
    }

    @Override
    public Product findById(Integer id) {
        return productDao.findById(id);
    }

    @Override
    public void save(Product product) {
        productDao.save(product);
    }

    @Override
    public void deleteById(Integer id) {
        productDao.deleteById(id);
    }
}
