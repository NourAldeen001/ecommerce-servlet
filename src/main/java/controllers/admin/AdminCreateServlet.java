package controllers.admin;

import dao.ConnectionFactory;
import dto.CreateAdminRequest;
import exceptions.BusinessException;
import exceptions.DataAccessException;
import exceptions.UsernameAlreadyExistsException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.AdminService;
import services.CustomerService;

import java.io.IOException;

@WebServlet(name="AdminAdminUpdate", urlPatterns = {"/admin/admins/create"})
public class AdminCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private AdminService adminService;


    @Override
    public void init() throws ServletException {
        ConnectionFactory connFactory = (ConnectionFactory) getServletContext().getAttribute("connectionFactory");
        this.adminService = new AdminService(connFactory);
    }
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/admin/AdminCreate.html")
                .forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            CreateAdminRequest createAdminRequest = new CreateAdminRequest(
                    request.getParameter("username"),
                    request.getParameter("password"),
                    request.getParameter("confirmPassword")
            );
            adminService.addAdmin(createAdminRequest);
            response.sendRedirect(request.getContextPath() + "/admin/admins");
        } catch (IllegalArgumentException | BusinessException ex) {
            response.sendRedirect(request.getContextPath() + "/admin/admins/create?error=" + java.net.URLEncoder.encode(ex.getMessage(), "UTF-8"));
        } catch (Exception ex) {
            ex.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/Error.html");
        }
    }
}
