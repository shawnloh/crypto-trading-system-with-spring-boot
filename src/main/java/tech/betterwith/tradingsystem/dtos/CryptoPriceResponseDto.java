package tech.betterwith.tradingsystem.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CryptoPriceResponseDto {
    private String symbol;
    private BigDecimal askPrice;
    private BigDecimal bidPrice;
}
