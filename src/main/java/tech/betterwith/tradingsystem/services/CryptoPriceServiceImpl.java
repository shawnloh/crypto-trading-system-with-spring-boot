package tech.betterwith.tradingsystem.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.betterwith.tradingsystem.dtos.CryptoPriceResponseDto;
import tech.betterwith.tradingsystem.repository.CryptoPriceRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CryptoPriceServiceImpl implements CryptoPriceService {
    private final CryptoPriceRepository cryptoPriceRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CryptoPriceServiceImpl(CryptoPriceRepository cryptoPriceRepository, ModelMapper modelMapper) {
        this.cryptoPriceRepository = cryptoPriceRepository;
        this.modelMapper = modelMapper;
    }

    public List<CryptoPriceResponseDto> getLatestPrices() {
        return cryptoPriceRepository.findLatestResult().stream()
                .map(cryptoPrice -> modelMapper.map(cryptoPrice, CryptoPriceResponseDto.class))
                .collect(Collectors.toList());
    }
}
