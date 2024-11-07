package tech.betterwith.tradingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CryptoPriceDTO {
    private String symbol;
    private BigDecimal askPrice;
    private BigDecimal bidPrice;
}
