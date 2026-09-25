package controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import entities.Product;
import services.CategoryService;
import services.ProductService;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import dao.ConnectionFactory;
import dto.Category;


@WebServlet("/ProductDetails")
public class ProductDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private ConnectionFactory connection;
    private ProductService productService;
    private CategoryService categoryService;
    
    public ProductDetails() {
        super();
        productService=new ProductService();
        categoryService=new CategoryService();
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		connection=(ConnectionFactory) request.getServletContext().getAttribute("connectionFactory");
		String productName=request.getParameter("productName");
		int id = Integer.parseInt( request.getParameter("id") );
		Product product=productService.getProductDetails(id, connection);
		List<Category>categories=categoryService.getCategories(connection);
		request.setAttribute("product",product);
		request.setAttribute("categories", categories);
		RequestDispatcher dispatcher=request.getRequestDispatcher("/view/ProductDetails.jsp");
		dispatcher.forward(request, response);
	}
		
	

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}	

}
