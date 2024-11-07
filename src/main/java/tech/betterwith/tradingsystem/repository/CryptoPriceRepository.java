package tech.betterwith.tradingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.betterwith.tradingsystem.models.CryptoPrice;

import java.util.UUID;

@Repository
public interface CryptoPriceRepository extends JpaRepository<CryptoPrice, UUID> {
}
