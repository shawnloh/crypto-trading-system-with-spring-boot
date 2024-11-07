package tech.betterwith.tradingsystem.scheduler.aggregation.binance;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tech.betterwith.tradingsystem.scheduler.aggregation.PriceFetcher;
import tech.betterwith.tradingsystem.scheduler.aggregation.PriceFetchedResult;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("binancePriceFetcher")
public class BinancePriceFetcher implements PriceFetcher {
    private static final Logger logger = LoggerFactory.getLogger(BinancePriceFetcher.class);
    @Value("${app.crypto.price.sources.binance}")
    private String url;
    @Value("${app.crypto.pairs}")
    private List<String> supportedPairs;

    private final RestTemplate restTemplate;

    public BinancePriceFetcher(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    @PostConstruct
    private void initialize() {
        supportedPairs = supportedPairs.stream()
                .map(String::toUpperCase)
                .toList();
    }

    @Override
    public Map<String, PriceFetchedResult> fetchPrices() {
        ResponseEntity<BinanceCryptoPrice[]> response = restTemplate.getForEntity(url, BinanceCryptoPrice[].class);
        if (response.getStatusCode().isError()) {
            logger.error("Failed to fetch prices from Binance. Status code: {}", response.getStatusCode());
            return null;
        }
        
        Map<String, PriceFetchedResult> prices = Arrays.stream(response.getBody())
                .filter(price -> supportedPairs.contains(price.getSymbol()))
                .peek(price -> price.setSymbol(price.getSymbol().toUpperCase()))
                .collect(Collectors.toMap(BinanceCryptoPrice::getSymbol, price -> new PriceFetchedResult("Binance", price.getSymbol(), price.getAskPrice(), price.getBidPrice())));
        
        return prices;
        
    }
}