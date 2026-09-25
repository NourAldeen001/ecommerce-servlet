package controllers.admin;

import dao.ConnectionFactory;
import dto.OrderResponse;
import dto.OrderUpdateStatusRequest;
import exceptions.BusinessException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.OrderService;

import java.io.IOException;

@WebServlet(name="AdminOrderUpdate", urlPatterns = {"/admin/orders/update"})
public class AdminOrderUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

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
            request.getRequestDispatcher("/admin/order_update.jsp")
                    .forward(request, response);
        } catch (RuntimeException e) {
            response.sendRedirect(request.getContextPath() +"/admin/orders");
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        long orderId = Long.parseLong(request.getParameter("order-id"));
        String status = request.getParameter("status");

        OrderUpdateStatusRequest orderUpdateDto = new OrderUpdateStatusRequest(orderId, status);

        try {
            orderService.updateStatus(orderUpdateDto);
            response.sendRedirect(request.getContextPath() +"/admin/orders");
        } catch (BusinessException | IllegalArgumentException ex){
            response.sendRedirect(request.getContextPath() + "/admin/orders/update?error=" + java.net.URLEncoder.encode(ex.getMessage(), "UTF-8"));
        } catch (Exception ex) {
            ex.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/Error.html");
        }
    }
}
