package tech.betterwith.tradingsystem.scheduler.aggregation.huobi;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tech.betterwith.tradingsystem.scheduler.aggregation.PriceFetcher;
import tech.betterwith.tradingsystem.scheduler.aggregation.PriceFetchedResult;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("huobiPriceFetcher")
public class HuobiPriceFetcher implements PriceFetcher {
    private static final Logger logger = LoggerFactory.getLogger(HuobiPriceFetcher.class);
    @Value("${app.crypto.price.sources.huobi}")
    private String url;
    @Value("${app.crypto.pairs}")
    private List<String> supportedPairs;
    
    private final RestTemplate restTemplate;
    public HuobiPriceFetcher(RestTemplate restTemplate) {
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
        ResponseEntity<HuobiCryptoPrice> response = restTemplate.getForEntity(url, HuobiCryptoPrice.class);
        if (response.getStatusCode().isError()) {
            logger.error("Failed to fetch prices from Binance. Status code: {}", response.getStatusCode());
            return null;
        }


        Map<String, PriceFetchedResult> prices = response.getBody().getData().stream()
                .filter(price -> supportedPairs.contains(price.getSymbol().toUpperCase()))
                .peek(price -> price.setSymbol(price.getSymbol().toUpperCase()))
                .collect(Collectors.toMap(HuobiCryptoPrice.CryptoPrice::getSymbol, price -> new PriceFetchedResult("Huobi", price.getSymbol(), price.getAsk(), price.getBid())));

        return prices;
    }
    
}
