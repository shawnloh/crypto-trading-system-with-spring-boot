package tech.betterwith.tradingsystem.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import tech.betterwith.tradingsystem.constants.OrderType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String symbol;

    @Enumerated(EnumType.STRING)
    @Setter
    private OrderType orderType;
    
    @Setter
    private BigDecimal quantity;

    @Setter
    private BigDecimal price;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne
    @Setter
    @JoinColumn(name = "user_id")
    private AppUser user;

    public Order(String symbol, OrderType orderType, BigDecimal quantity, BigDecimal price) {
        this.symbol = symbol;
        this.orderType = orderType;
        this.quantity = quantity;
        this.price = price;
    }

    public Order() {

    }
}
