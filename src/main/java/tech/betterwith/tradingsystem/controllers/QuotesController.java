package tech.betterwith.tradingsystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.betterwith.tradingsystem.dto.CryptoPriceDTO;
import tech.betterwith.tradingsystem.services.CryptoPriceService;

import java.util.List;

@RestController
@RequestMapping("/api/quotes")
public class QuotesController {
    private final CryptoPriceService cryptoPriceService;

    @Autowired
    public QuotesController(CryptoPriceService cryptoPriceService) {
        this.cryptoPriceService = cryptoPriceService;
    }
    
    @GetMapping()
    public ResponseEntity<List<CryptoPriceDTO>> getQuotes() {
        return ResponseEntity.ok(cryptoPriceService.getLatestPrices());
    }
    
}
