package controllers.admin;

import dao.ConnectionFactory;
import entities.OrderStatus;
import entities.Role;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.CustomerService;
import services.OrderService;

import java.io.IOException;

@WebServlet(name="AdminCustomers", urlPatterns = {"/admin/customers"})
public class AdminCustomerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CustomerService customerService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminCustomerServlet() {
        super();
    }
    @Override
    public void init() throws ServletException {
        ConnectionFactory connFactory = (ConnectionFactory) getServletContext().getAttribute("connectionFactory");
        this.customerService = new CustomerService(connFactory);
    }


    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("customers", customerService.findAll());
        request.getRequestDispatcher("/admin/customers.jsp")
                .forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

}
