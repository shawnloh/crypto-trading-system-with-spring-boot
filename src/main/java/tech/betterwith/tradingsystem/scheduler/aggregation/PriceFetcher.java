package tech.betterwith.tradingsystem.scheduler.aggregation;

import java.util.Map;

public interface PriceFetcher {
     Map<String, PriceFetchedResult> fetchPrices();
}
