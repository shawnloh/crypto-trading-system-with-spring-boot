package tech.betterwith.tradingsystem.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import tech.betterwith.tradingsystem.constants.CryptoCurrency;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Entity
public class CryptoWallet {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Setter
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    @Setter
    private CryptoCurrency currency;

    @Setter
    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

    public CryptoWallet(BigDecimal balance, CryptoCurrency currency) {
        this.balance = balance;
        this.currency = currency;
    }

    public CryptoWallet() {

    }

}
