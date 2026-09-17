package controllers.admin;

import entities.OrderStatus;
import jakarta.servlet.annotation.WebServlet;
import dao.ConnectionFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.OrderService;
import java.io.IOException;

@WebServlet(name="AdminOrders", urlPatterns = {"/admin/orders"})
public class AdminOrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private OrderService orderService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminOrderServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    @Override
    public void init() throws ServletException {
        ConnectionFactory connFactory = (ConnectionFactory) getServletContext().getAttribute("connectionFactory");
        this.orderService = new OrderService(connFactory);
    }


    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("pending", orderService.findOrdersByStatus(OrderStatus.PENDING));
        request.setAttribute("confirmed", orderService.findOrdersByStatus(OrderStatus.CONFIRMED));
        request.setAttribute("shipped", orderService.findOrdersByStatus(OrderStatus.SHIPPED));
        request.setAttribute("delivered", orderService.findOrdersByStatus(OrderStatus.DELIVERED));
        request.setAttribute("cancelled", orderService.findOrdersByStatus(OrderStatus.CANCELLED));

        request.getRequestDispatcher("/admin/orders.jsp")
                .forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

}
