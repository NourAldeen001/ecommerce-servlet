package dao;
import entities.Product;
public interface ProductDao {
Product getById(long id) throws Exception;
boolean decreaseStock(long productId, int quantity)throws Exception;
void increaseStock(long productId, int quantity) throws Exception;
}
