package controllers.admin;

import dao.ConnectionFactory;
import dto.CreateAdminRequest;
import dto.DeleteAdminRequest;
import exceptions.AdminNotFoundException;
import exceptions.BusinessException;
import exceptions.CannotDeleteOwnAdminUserException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.AdminService;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name="AdminAdminsDelete", urlPatterns = {"/admin/admins/delete"})
public class AdminDeleteServlet extends HttpServlet {
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
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            DeleteAdminRequest deleteAdminRequest = new DeleteAdminRequest(
                    request.getParameter("toDelete"),
                    (String) request.getSession().getAttribute("userUsernameLoggedIn")
            );
            adminService.deleteAdmin(deleteAdminRequest);
            response.sendRedirect(request.getContextPath() + "/admin/admins");
        } catch (BusinessException | IllegalArgumentException ex) {
            PrintWriter pw = response.getWriter();
            pw.println("<h1><strong>Could not delete username: " +  request.getParameter("toDelete") + " due to error: </strong></h1>");
            pw.println(ex.getMessage());
            pw.println("<a href='" + request.getContextPath() + "/admin/admins" + "'> Return to admins</a>");
        } catch (Exception ex) {
            ex.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/Error.html");
        }
    }
}
