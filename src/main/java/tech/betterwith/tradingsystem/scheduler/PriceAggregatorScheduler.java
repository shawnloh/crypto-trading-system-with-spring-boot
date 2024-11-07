package tech.betterwith.tradingsystem.scheduler;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tech.betterwith.tradingsystem.models.CryptoPrice;
import tech.betterwith.tradingsystem.repository.CryptoPriceRepository;
import tech.betterwith.tradingsystem.scheduler.aggregation.PriceFetcher;
import tech.betterwith.tradingsystem.scheduler.aggregation.PriceFetchedResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PriceAggregatorScheduler {
    private static final Logger LOGGER = LoggerFactory.getLogger(PriceAggregatorScheduler.class);
    private final List<PriceFetcher> priceFetchers;
    private final CryptoPriceRepository cryptoPriceRepository;
    @Value("${app.crypto.pairs}")
    private List<String> supportedPairs;
    
    @Autowired
    public PriceAggregatorScheduler(List<PriceFetcher> priceFetchers, CryptoPriceRepository cryptoPriceRepository) {
        this.priceFetchers = priceFetchers;
        this.cryptoPriceRepository = cryptoPriceRepository;
    }
    @PostConstruct
    private void initialize() {
        supportedPairs = supportedPairs.stream()
                .map(String::toUpperCase)
                .toList();
    }
    
    @Scheduled(fixedRateString = "${app.aggregator.fixedRateInMs}")
    public void aggregatePrices() {
        LOGGER.info("Aggregating prices...");
        Map<String, PriceFetchedResult> bestAskPrices = new HashMap<>();
        Map<String, PriceFetchedResult> bestBidPrices = new HashMap<>();
        
        for (PriceFetcher priceFetcher : priceFetchers) {
            Map<String, PriceFetchedResult> prices = priceFetcher.fetchPrices();
            
            if (prices != null) {
                for (Map.Entry<String, PriceFetchedResult> entry : prices.entrySet()) {
                    String symbol = entry.getKey();
                    PriceFetchedResult price = entry.getValue();
                    
                    if (bestAskPrices.containsKey(symbol)) {
                        PriceFetchedResult bestPrice = bestAskPrices.get(symbol);
                        if (price.getAskPrice().compareTo(bestPrice.getAskPrice()) < 0) {
                            bestAskPrices.put(symbol, price);
                        }
                    } else {
                        bestAskPrices.put(symbol, price);
                    }
                    
                    if (bestBidPrices.containsKey(symbol)) {
                        PriceFetchedResult bestPrice = bestBidPrices.get(symbol);
                        if (price.getBidPrice().compareTo(bestPrice.getBidPrice()) > 0) {
                            bestBidPrices.put(symbol, price);
                        }
                    } else {
                        bestBidPrices.put(symbol, price);
                    }
                }
            }
        }
        List<CryptoPrice> cryptoPrices = new ArrayList<>(supportedPairs.size());
        for (String pairs : supportedPairs) {
            PriceFetchedResult bestAskPrice = bestAskPrices.get(pairs);
            PriceFetchedResult bestBidPrice = bestBidPrices.get(pairs);
            
            CryptoPrice cryptoPrice = new CryptoPrice();
            cryptoPrice.setSymbol(pairs);
            if (bestAskPrice != null) {
                cryptoPrice.setAskPrice(bestAskPrice.getAskPrice());
                cryptoPrice.setAskSource(bestAskPrice.getSource());
            }
            if (bestBidPrice != null) {
                cryptoPrice.setBidPrice(bestBidPrice.getBidPrice());
                cryptoPrice.setBidSource(bestBidPrice.getSource());
            }
            cryptoPrices.add(cryptoPrice);
        }
        cryptoPriceRepository.saveAll(cryptoPrices);
        
        LOGGER.info("Prices aggregated successfully");
        
        
    }
}
