package tech.betterwith.tradingsystem.services;

import tech.betterwith.tradingsystem.dtos.CryptoPriceResponseDto;

import java.util.List;

public interface CryptoPriceService {
    List<CryptoPriceResponseDto> getLatestPrices();
}
