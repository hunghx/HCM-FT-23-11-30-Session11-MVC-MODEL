package ra.mvc_model.service;

import ra.mvc_model.model.Product;

import java.util.*;
import java.util.stream.Collectors;

public class ProductServiceImpl implements IProductService{
   private static final List<Product> products = new ArrayList<>();
   static {
       Product p1 = new Product(1,"áo dài ",99.99,"null","https://khoinguonsangtao.vn/wp-content/uploads/2022/09/hinh-anh-gai-xinh-mac-ao-dai.jpg",99);
       Product p2 = new Product(3,"Quần jean ",109.99,"null","https://afamilycdn.com/150157425591193600/2021/3/10/1530306901032039917589237871360618769515325n-16153480368581392256573.jpg",98);
       Product p3 = new Product(5,"áo sơ mi nam ",69.99,"null","https://4men.com.vn/images/thumbs/2017/08/nhung-mau-ao-so-mi-nam-caro-nam-dep-nhat-hien-nay-tai-4men-news-235.jpg",97);
        products.add(p1);
        products.add(p2);
        products.add(p3);
   }

    @Override
    public List<Product> searchByName(String keyword) {
        return products.stream()
                .filter(pro->pro.getName().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> findAll() {
        return products;
    }

    @Override
    public Product findById(Integer id) {
//        for (Product product : products) {
//            if (product.getId()==id){
//                return product;
//            }
//        }
//        return null;
        return products.stream()
                .filter(pro-> Objects.equals(pro.getId(), id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void save(Product product) {
           if(product.getId()==null){
               // thêm mới
               product.setId(getNewId()); // id tu tăng
               products.add(product);
           }else {
               products.set(products.indexOf(findById(product.getId())),product);
           }
    }

    @Override
    public void deleteById(Integer id) {
        products.remove(findById(id));
    }
    private Integer getNewId(){
//        Integer maxId=0;
//        for(Product p : products){
//            if(p.getId()>maxId){
//                maxId=p.getId();
//            }
//        }
//        return maxId+1;
        return products.stream().map(Product::getId).max(Integer::compareTo).orElse(0)+1;
    }
}
