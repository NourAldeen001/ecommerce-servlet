package services;

import dao.*;
import entities.CartItem;
import entities.Product;
import dto.CartItemDto;

import java.util.ArrayList;
import java.util.List;

public class CartServiceImpl implements CartService {

    private final CartItemDao cartItemDAO = new CartitemDaoImpl();
    private final ProductDao productDAO = new ProductDaoImpl();

    @Override
    public void addToCart(long customerId, long productId, int quantity) throws Exception {
        Product product = productDAO.getById(productId);
        if (product == null) throw new Exception("المنتج غير موجود");

        CartItem existing = cartItemDAO.findByCustomerAndProduct(customerId, productId);
        int totalRequested = quantity + (existing != null ? existing.getQuantity() : 0);

        if (product.getStock() < totalRequested) {
            throw new Exception("الكمية المتاحة فقط " + product.getStock());
        }

//        cartItemDAO.add(new CartItem(customerId, productId, quantity));
        cartItemDAO.add(new CartItem(customerId,productId,quantity));
       
    }

    @Override
    public List<CartItemDto> getCart(long customerId) throws Exception {
        List<CartItem> items = cartItemDAO.findByCustomerId(customerId);
        List<CartItemDto> result = new ArrayList<>();

        for (CartItem item : items) {
            Product product = productDAO.getById(item.getProductId());
            if (product == null) continue; // ممكن المنتج اتحذف
            result.add(new CartItemDto(item.getId(), product.getId(), product.getName(),
                    product.getPrice(), item.getQuantity(), product.getStock()));
        }
        return result;
    }

    @Override
    public void updateQuantity(long cartItemId, int quantity) throws Exception {
        if (quantity <= 0) {
            cartItemDAO.delete(cartItemId);
        } else {
            cartItemDAO.updateQuantity(cartItemId, quantity);
        }
    }

    @Override
    public void removeItem(long cartItemId) throws Exception {
        cartItemDAO.delete(cartItemId);
    }
}
