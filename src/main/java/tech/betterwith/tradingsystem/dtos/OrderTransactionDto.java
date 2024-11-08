package tech.betterwith.tradingsystem.dtos;

public class OrderTransactionDto extends GenericAPIResponseDto {
    OrderDto orderDto;

    public OrderTransactionDto(String message, boolean status, OrderDto orderDto) {
        super(message, status);
        this.orderDto = orderDto;
    }
}
