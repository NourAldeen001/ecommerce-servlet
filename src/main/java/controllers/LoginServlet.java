package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import services.AuthService;
import java.io.IOException;
import dao.ConnectionFactory;
import dto.LoginRequest;
import entities.Role;
import entities.User;
import exceptions.DataAccessException;
import exceptions.AuthenticationException;

/**
 * Servlet implementation class LoginServlet
 */

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private AuthService authService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    
   	@Override
   	public void init() throws ServletException {
   		ConnectionFactory factory = (ConnectionFactory) getServletContext()
   				.getAttribute("connectionFactory");
   		authService = new AuthService(factory);
   	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Here In doGet");
		Cookie[] cookies = request.getCookies();
		String username = null;
		String pass = null;
		if(cookies != null) {
			for(Cookie c : cookies) {
				if(c.getName().equals("usernameToRemember")) {
					username = c.getValue();
				}
				if(c.getName().equals("passToRemember")) {
					pass = c.getValue();
				}
			}
			
			try {
				
				if(username != null && pass != null) {
					LoginRequest loginRequest = new LoginRequest(username, pass);
					User user = authService.login(loginRequest);
					HttpSession session = request.getSession();
					session.setAttribute("userUsernameLoggedIn", username);
					session.setAttribute("userRole", user.getRole().name());
					
					if(user.getRole() == Role.ADMIN) {
						response.sendRedirect(request.getContextPath() + "/admin/dashboard");
					}
					else {
						response.sendRedirect("/view/ShowProducts");
					}
					return;
				}
				else {
					response.sendRedirect("Login.html");
					return;
				}
				
			}
			catch (AuthenticationException | IllegalArgumentException ex) {
				response.sendRedirect("Login.html");
				return;
			}
			catch (DataAccessException ex) {
				ex.printStackTrace();
				response.sendRedirect("Error.html");
				return;
			}
			
		}
		else {
			response.sendRedirect("Login.html");
			return;
		}	
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Here In doPost");
		try {
			
			LoginRequest loginRequest = new LoginRequest(
					request.getParameter("username"),
					request.getParameter("password")
			);
			
			User user = authService.login(loginRequest);
			
			String rememberMe = request.getParameter("rememberMe");
			
			boolean isRememberMeChecked = "on".equals(rememberMe) || "true".equals(rememberMe);
			
			if(isRememberMeChecked) {
				Cookie usernameCookie = new Cookie("usernameToRemember", request.getParameter("username"));
				Cookie passCookie = new Cookie("passToRemember", request.getParameter("password"));
				
				usernameCookie.setMaxAge(60);
				passCookie.setMaxAge(60);
				
				response.addCookie(usernameCookie);
				response.addCookie(passCookie);
			}
			
			// Set User's Username who logged in in session
			HttpSession session = request.getSession();
			session.setAttribute("userUsernameLoggedIn", request.getParameter("username"));
			session.setAttribute("userRole", user.getRole().name());
			
			if(user.getRole() == Role.ADMIN) {
				response.sendRedirect(request.getContextPath() + "/admin/dashboard");
			}
			else {
				response.sendRedirect(request.getContextPath() + "/ShowProducts");
			}
			
		}
		catch (AuthenticationException | IllegalArgumentException ex) {
			response.sendRedirect("Login.html?error=" + java.net.URLEncoder.encode(ex.getMessage(), "UTF-8"));
		}
		catch (DataAccessException ex) {
			ex.printStackTrace();
			response.sendRedirect("Error.html");
		}
		
	}

}
