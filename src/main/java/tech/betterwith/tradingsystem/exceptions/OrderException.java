package tech.betterwith.tradingsystem.exceptions;

import lombok.Getter;
import tech.betterwith.tradingsystem.dtos.OrderDto;

@Getter
public class OrderException extends RuntimeException {
    private String message;

    private OrderDto orderDto;

    public OrderException(String message, OrderDto orderDto) {
        super(String.format("Could not place an order: %s", message));
        this.message = message;
        this.orderDto = orderDto;
    }
}
