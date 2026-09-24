package controllers.admin;

import dao.ConnectionFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.AdminService;
import services.CustomerService;

import java.io.IOException;

@WebServlet(name="AdminAdmins", urlPatterns = {"/admin/admins"})
public class AdminAdminsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private AdminService adminService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    @Override
    public void init() throws ServletException {
        ConnectionFactory connFactory = (ConnectionFactory) getServletContext().getAttribute("connectionFactory");
        this.adminService = new AdminService(connFactory);
    }


    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("admins", adminService.getAll());
        request.getRequestDispatcher("/admin/admins.jsp")
                .forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

}
