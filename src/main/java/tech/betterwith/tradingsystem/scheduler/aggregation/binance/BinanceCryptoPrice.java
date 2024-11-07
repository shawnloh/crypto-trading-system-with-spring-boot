package tech.betterwith.tradingsystem.scheduler.aggregation.binance;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class BinanceCryptoPrice {
    private String symbol;
    private BigDecimal bidPrice;
    private BigDecimal askPrice;
}
