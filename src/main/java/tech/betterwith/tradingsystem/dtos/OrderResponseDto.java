package tech.betterwith.tradingsystem.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.betterwith.tradingsystem.constants.OrderType;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderResponseDto {

    private String symbol;
    private BigDecimal quantity;
    private OrderType orderType;
    private BigDecimal price;

}
