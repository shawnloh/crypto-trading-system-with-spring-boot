package tech.betterwith.tradingsystem.services;

import tech.betterwith.tradingsystem.dto.CryptoPriceDTO;

import java.util.List;

public interface CryptoPriceService {
     List<CryptoPriceDTO> getLatestPrices();
}
