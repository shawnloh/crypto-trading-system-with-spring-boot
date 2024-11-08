package tech.betterwith.tradingsystem.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import tech.betterwith.tradingsystem.constants.OrderType;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class OrderDto {
    @NotBlank
    private String symbol;

    @NotNull
    @DecimalMin("0.00000001")
    private BigDecimal quantity;

    @NotNull
    private OrderType orderType;

}
