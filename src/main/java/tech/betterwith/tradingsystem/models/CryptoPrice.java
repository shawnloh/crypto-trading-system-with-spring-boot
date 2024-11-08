package tech.betterwith.tradingsystem.models;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
public class CryptoPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String symbol;
    private BigDecimal askPrice;
    private String askSource;
    private BigDecimal bidPrice;
    private String bidSource;

    @OneToMany(mappedBy = "cryptoPrice")
    private Set<Order> orders;

    @CreationTimestamp
    private LocalDateTime retrievedAt;

    public CryptoPrice(String symbol, BigDecimal askPrice, String askSource, BigDecimal bidPrice, String bidSource) {
        this.symbol = symbol;
        this.askPrice = askPrice;
        this.askSource = askSource;
        this.bidPrice = bidPrice;
        this.bidSource = bidSource;
    }

    public CryptoPrice() {

    }

}
