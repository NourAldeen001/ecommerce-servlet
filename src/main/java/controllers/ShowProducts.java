package controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import dao.ConnectionFactory;
import dto.Category;
import entities.Product;
import services.CategoryService;
import services.ProductService;


@WebServlet("/ShowProducts")
public class ShowProducts extends HttpServlet {
	private static final long serialVersionUID = 1L;
       private ConnectionFactory connection;
       private ProductService productService;
       private CategoryService categoryService;

    public ShowProducts() {
        super();
        productService=new ProductService();
        categoryService=new CategoryService();
        
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		connection=(ConnectionFactory)request.getServletContext().getAttribute("connectionFactory");
		List<Product> products=productService.findAllProducts(connection);
		List<Category> categories=categoryService.getCategories(connection);
		request.setAttribute("products", products);
		request.setAttribute("categories", categories);
		System.out.println("products : "+products);
		System.out.println("categories : "+categories);
		RequestDispatcher dispatcher=request.getRequestDispatcher("/view/ShowProducts.jsp");
		dispatcher.forward(request, response);
		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id=Integer.parseInt(request.getParameter("categoryId"));
		List<Product> products=productService.findProductsByCategory(id, connection);
		List<Category> categories=categoryService.getCategories(connection);
		request.setAttribute("products", products);
		request.setAttribute("categories", categories);
		RequestDispatcher dispatcher=request.getRequestDispatcher("/view/ShowProducts.jsp");
		dispatcher.forward(request, response);
		
	}

}
