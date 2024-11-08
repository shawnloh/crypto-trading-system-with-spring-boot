package tech.betterwith.tradingsystem.services;

import tech.betterwith.tradingsystem.dtos.CryptoPriceDTO;

import java.util.List;

public interface CryptoPriceService {
     List<CryptoPriceDTO> getLatestPrices();
}
