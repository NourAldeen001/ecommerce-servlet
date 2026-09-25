package controllers;

import dto.CheeckoutRequestDto;
import dto.OrderResponseDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import services.CartService;
import services.CartServiceImpl;
import services.CheckoutService;
import services.CheckoutServiceImpl;

import java.io.IOException;

public class CheckoutServlet extends HttpServlet {

    private final CheckoutService checkoutService = new CheckoutServiceImpl();
    private final CartService cartService = new CartServiceImpl();

    private Long getCustomerId(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("customerId") == null) {
            return null;
        }
        return (Long) session.getAttribute("customerId");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Long customerId = getCustomerId(req);
        if (customerId == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        try {
            var cartItems = cartService.getCart(customerId);
            if (cartItems.isEmpty()) {
                req.setAttribute("error", "السلة فارغة، أضف منتجات أولًا");
                req.getRequestDispatcher("/cart.jsp").forward(req, resp);
                return;
            }

            double total = cartItems.stream().mapToDouble(i -> i.getSubtotal()).sum();
            req.setAttribute("cartItems", cartItems);
            req.setAttribute("cartTotal", total);
            req.getRequestDispatcher("/checkout.jsp").forward(req, resp);

        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Long customerId = getCustomerId(req);
        if (customerId == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        String paymentMethod = req.getParameter("paymentMethod");

        CheeckoutRequestDto request = new CheeckoutRequestDto();
        request.setCustomerId(customerId);
        request.setPaymentMethod(paymentMethod);

        try {
        	OrderResponseDto order = checkoutService.checkout(request);
            req.setAttribute("order", order);
            req.getRequestDispatcher("/order-confirmation.jsp").forward(req, resp);

        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            doGet(req, resp);
        }
    }
}
