package ra.mvc_model.controller;

import ra.mvc_model.model.Product;
import ra.mvc_model.service.IProductService;
import ra.mvc_model.service.ProductServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ProductController", value = "/product")
public class ProductController extends HttpServlet {
    private static final IProductService productService = new ProductServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action != null) {
            switch (action) {
                case "LIST":
                    // hiển thị danh sách
                    List<Product> products = productService.findAll();
                    request.setAttribute("products", products);
                    request.getRequestDispatcher("/WEB-INF/views/list-product.jsp")
                            .forward(request, response);
                    break;
                case "DELETE":
                    // lấy id
                    Integer idDel = Integer.valueOf(request.getParameter("id"));
                    // tiến hành xóa
                    productService.deleteById(idDel);
                    // điều hướng về trang danh sách
                    response.sendRedirect("/product?action=LIST");
                    break;
                case "ADD":
                    // điêu hướng sang trang thêm mới
                    request.getRequestDispatcher("/WEB-INF/views/add-product.jsp").forward(request, response);
                    break;
                case "EDIT":
                    // lấy ra id
                    Integer idEdit = Integer.valueOf(request.getParameter("id"));
                    Product product = productService.findById(idEdit);
                    request.setAttribute("product", product);
                    request.getRequestDispatcher("/WEB-INF/views/edit-product.jsp").forward(request, response);
                    break;
                case "SEARCH":
                    String keyword = request.getParameter("keyword");
                    List<Product> productList = productService.searchByName(keyword);
                    request.setAttribute("products", productList);
                    request.setAttribute("keyword", keyword);
                    request.getRequestDispatcher("/WEB-INF/views/list-product.jsp").forward(request, response);
                    break;
            }
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // set utf-8
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action != null) {
            switch (action) {
                case "ADD":
                   Product product = getProdutFromRequest(request);
                    productService.save(product);
                    // điều hướng về trang danh sách
                    response.sendRedirect("/product?action=LIST");
                    break;
                case "UPDATE":
                    Integer idEdit = Integer.valueOf(request.getParameter("id"));
                    Product productEdit = getProdutFromRequest(request);
                    productEdit.setId(idEdit);
                    productService.save(productEdit);
                    // điều hướng về trang danh sách
                    response.sendRedirect("/product?action=LIST");
                    break;
            }
        }
    }
    private Product getProdutFromRequest(HttpServletRequest request) {
        String name = request.getParameter("name");
        Double price = Double.valueOf(request.getParameter("price"));
        Integer stock = Integer.valueOf(request.getParameter("stock"));
        String description = request.getParameter("description");
        String image = request.getParameter("image");
        return new Product(null, name, price, description, image, stock);
    }

}