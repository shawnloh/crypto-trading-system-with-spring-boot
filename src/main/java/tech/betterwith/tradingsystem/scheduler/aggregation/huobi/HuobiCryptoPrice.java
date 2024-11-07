package tech.betterwith.tradingsystem.scheduler.aggregation.huobi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class HuobiCryptoPrice {
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class CryptoPrice {
        private String symbol;
        private BigDecimal bid;
        private BigDecimal ask;
    }
    private List<HuobiCryptoPrice.CryptoPrice> data;
}
