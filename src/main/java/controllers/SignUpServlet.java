package controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.AuthService;
import java.io.IOException;
import java.io.PrintWriter;
import dao.ConnectionFactory;
import dto.SignUpRequest;
import exceptions.DataAccessException;
import exceptions.DuplicateKeyException;
import exceptions.EmailAlreadyExistsException;
import exceptions.UsernameAlreadyExistsException;
import services.CustomerService;

/**
 * Servlet implementation class SignUpServlet
 */
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
       
    private CustomerService customerService;
    
    public SignUpServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	@Override
	public void init() throws ServletException {
		ConnectionFactory factory = (ConnectionFactory) getServletContext()
				.getAttribute("connectionFactory");
		customerService = new CustomerService(factory);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		PrintWriter out = response.getWriter();
		out.print("Sign Up Page");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
		try {
			
			SignUpRequest signUpRequest = new SignUpRequest(
					request.getParameter("email"),
					request.getParameter("username"),
					request.getParameter("name"),
					request.getParameter("phone"),
					request.getParameter("address"),
					request.getParameter("password"),
					request.getParameter("confirmPassword")
			);
			
			customerService.signUp(signUpRequest);
			
			RequestDispatcher requestDispatch = request.getRequestDispatcher("Login.html");
			requestDispatch.forward(request, response);
		}
		catch (IllegalArgumentException ex) {
			response.sendRedirect("SignUp.html?error=" + java.net.URLEncoder.encode(ex.getMessage(), "UTF-8"));
		}
		catch (DataAccessException ex) {
			ex.printStackTrace();
			response.sendRedirect("Error.html");
		}
	}

}
