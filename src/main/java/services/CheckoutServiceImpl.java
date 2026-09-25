package services;

import dao.*;
import util.DbConnection;
import dto.CheeckoutRequestDto;
import dto.OrderResponseDto;
import entities.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class CheckoutServiceImpl implements CheckoutService {

    private final CartItemDao cartItemDAO = new CartitemDaoImpl();
    private final ProductDao productDAO = new ProductDaoImpl();
    private final OrderDao orderDAO = new OrderDaoImpl();

    @Override
    public OrderResponseDto checkout(CheeckoutRequestDto request) throws Exception {
        long customerId = request.getCustomerId();

        List<CartItem> cartItems = cartItemDAO.findByCustomerId(customerId);
        if (cartItems.isEmpty()) {
            throw new Exception("السلة فارغة");
        }

        Connection con = null;
        try {
            con = DbConnection.getConnection();
            con.setAutoCommit(false); 

            double total = 0;
            List<OrderItem> orderItems = new ArrayList<>();

            for (CartItem item : cartItems) {
                Product product = productDAO.getById(item.getProductId());
                if (product == null) {
                    throw new Exception("منتج غير موجود: " + item.getProductId());
                }

                boolean stockOk = productDAO.decreaseStock(item.getProductId(), item.getQuantity());
                if (!stockOk) {
                    throw new Exception("الكمية غير متوفرة لمنتج: " + product.getName());
                }

                double price = product.getPrice();
                total += price * item.getQuantity();
                orderItems.add(new OrderItem(0, item.getProductId(), item.getQuantity(), price));
            }

            Order order = new Order(customerId, Order.Status.PENDING);
            long orderId = orderDAO.createOrder(con, order);

            for (OrderItem oi : orderItems) {
                oi.setOrderId(orderId);
            }
            orderDAO.addOrderItems(con, orderItems);

            boolean paymentSuccess = processPayment(request.getPaymentMethod(), total);

            if (paymentSuccess) {
                orderDAO.updateStatus(con, orderId, Order.Status.CONFIRMED);
                cartItemDAO.clearByCustomerId(customerId);
                con.commit();
                return new OrderResponseDto(orderId, total, "CONFIRMED", "تم تأكيد الطلب بنجاح");
            } else {
                con.rollback(); 
                return new OrderResponseDto(orderId, total, "FAILED", "فشلت عملية الدفع");
            }

        } catch (Exception e) {
            if (con != null) con.rollback();
            throw e;
        } finally {
            if (con != null) {
                con.setAutoCommit(true);
                con.close();
            }
        }
    }

    private boolean processPayment(String method, double amount) {
        return true;
    }
}
