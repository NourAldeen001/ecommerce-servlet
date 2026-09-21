package controllers;

import dto.CartItemDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import services.CartService;
import services.CartServiceImpl;

import java.io.IOException;
import java.util.List;

public class CartServlet extends HttpServlet {

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
            List<CartItemDto> cartItems = cartService.getCart(customerId);

            double total = cartItems.stream()
                    .mapToDouble(CartItemDto::getSubtotal)
                    .sum();

            req.setAttribute("cartItems", cartItems);
            req.setAttribute("cartTotal", total);
            req.getRequestDispatcher("/cart.jsp").forward(req, resp);

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

        String action = req.getParameter("action");

        try {
            switch (action) {
                case "add": {
                    long productId = Long.parseLong(req.getParameter("productId"));
                    int quantity = Integer.parseInt(req.getParameter("quantity"));
                    cartService.addToCart(customerId, productId, quantity);
                    break;
                }
                case "update": {
                    long cartItemId = Long.parseLong(req.getParameter("cartItemId"));
                    int quantity = Integer.parseInt(req.getParameter("quantity"));
                    cartService.updateQuantity(cartItemId, quantity);
                    break;
                }
                case "remove": {
                    long cartItemId = Long.parseLong(req.getParameter("cartItemId"));
                    cartService.removeItem(cartItemId);
                    break;
                }
                default:
                    throw new Exception("عملية غير معروفة");
            }
            resp.sendRedirect(req.getContextPath() + "/cart");

        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            doGet(req, resp);
        }
    }
}
