package tech.betterwith.tradingsystem.services;

import tech.betterwith.tradingsystem.dtos.OrderDto;
import tech.betterwith.tradingsystem.dtos.OrderTransactionDto;
import tech.betterwith.tradingsystem.models.AppUser;

public interface OrderService {
    OrderTransactionDto order(OrderDto orderDto, AppUser user);
}
