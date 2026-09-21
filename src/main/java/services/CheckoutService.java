package services;
import dto.CheeckoutRequestDto;
import dto.OrderResponseDto;

public interface CheckoutService {
    OrderResponseDto checkout(CheeckoutRequestDto request) throws Exception;

}
