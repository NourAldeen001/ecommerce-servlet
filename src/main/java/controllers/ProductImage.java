package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import entities.Product;
import services.ProductService;

import java.io.IOException;
import java.sql.Connection;

import dao.ConnectionFactory;

/**
 * Servlet implementation class ProductImage
 */
@WebServlet("/ProductImage")
public class ProductImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private ProductService productService ;
	
    public ProductImage() {
        super();
        productService = new ProductService();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ConnectionFactory connection =
                (ConnectionFactory) request.getServletContext()
                        .getAttribute("connectionFactory");

        int id = Integer.parseInt(
                request.getParameter("id")
        );

        Product product =
                productService.findProductById(id, connection);

        if (product == null || product.getImage() == null) {

            response.sendError(
                    HttpServletResponse.SC_NOT_FOUND
            );

            return;
        }

        byte[] image = product.getImage();

        response.setContentType("image/jpeg");
        response.setContentLength(image.length);

        response.getOutputStream().write(image);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
