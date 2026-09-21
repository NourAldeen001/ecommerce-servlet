package services;
import dto.CartItemDto;
import java.util.List;

public interface CartService {
	 void addToCart(long customerId, long productId, int quantity) throws Exception;
	    List<CartItemDto> getCart(long customerId) throws Exception;
	    void updateQuantity(long cartItemId, int quantity) throws Exception;
	    void removeItem(long cartItemId) throws Exception;

}
