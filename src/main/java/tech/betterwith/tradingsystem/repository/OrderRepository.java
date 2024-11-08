package tech.betterwith.tradingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.betterwith.tradingsystem.models.Order;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    
}
