package controllers.admin;

import dao.ConnectionFactory;
import dto.OrderResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.OrderService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet(name="AdminOrderCancel", urlPatterns = {"/admin/orders/cancel"})
public class AdminOrderCancelServlet extends HttpServlet{
    private OrderService orderService;

    @Override
    public void init() throws ServletException {
        ConnectionFactory connFactory = (ConnectionFactory) getServletContext().getAttribute("connectionFactory");
        this.orderService = new OrderService(connFactory);
    }
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            long id = Long.parseLong(request.getParameter("order-id"));
            OrderResponse order = orderService.findById(id);
            request.setAttribute("order", order);
            orderService.cancelOrder(id);
            response.sendRedirect(request.getContextPath() +"/admin/orders");
        } catch (RuntimeException ex) {
            response.sendRedirect(request.getContextPath() +"/admin/orders??error=" + java.net.URLEncoder.encode(ex.getMessage(), StandardCharsets.UTF_8));
        }
    }
}
