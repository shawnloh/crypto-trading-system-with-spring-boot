package tech.betterwith.tradingsystem.scheduler.aggregation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceFetchedResult {
    private String source;
    private String symbol;
    private BigDecimal askPrice;
    private BigDecimal bidPrice;
}
