package tech.betterwith.tradingsystem.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.betterwith.tradingsystem.dtos.OrderDto;
import tech.betterwith.tradingsystem.dtos.OrderTransactionDto;
import tech.betterwith.tradingsystem.models.AppUser;
import tech.betterwith.tradingsystem.services.OrderService;

@RestController
@RequestMapping("/api/trade")
public class TradeController {
    private final OrderService orderService;

    public TradeController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderTransactionDto> order(@Valid @RequestBody OrderDto orderDto, Authentication authentication) {
        AppUser user = (AppUser) authentication.getPrincipal();
        return ResponseEntity.ok(orderService.order(orderDto, user));
    }
    

}
