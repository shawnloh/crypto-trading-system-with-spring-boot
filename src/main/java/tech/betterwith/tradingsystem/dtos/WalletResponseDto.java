package tech.betterwith.tradingsystem.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.betterwith.tradingsystem.constants.CryptoCurrency;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WalletResponseDto {
    private UUID id;
    private BigDecimal balance;
    private CryptoCurrency cryptoCurrency;
}
