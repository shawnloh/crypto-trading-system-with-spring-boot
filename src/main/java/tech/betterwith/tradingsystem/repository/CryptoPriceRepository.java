package tech.betterwith.tradingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tech.betterwith.tradingsystem.models.CryptoPrice;

import java.util.List;
import java.util.UUID;

@Repository
public interface CryptoPriceRepository extends JpaRepository<CryptoPrice, UUID> {
    @Query(value = "SELECT cp FROM CryptoPrice cp WHERE cp.retrievedAt = (SELECT MAX(cp2.retrievedAt) FROM CryptoPrice cp2 WHERE cp2.symbol = cp.symbol)")
    List<CryptoPrice> findLatestResult();
}
