package tech.betterwith.tradingsystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.betterwith.tradingsystem.models.CryptoWallet;

import java.util.UUID;

@Repository
public interface CryptoWalletRepository extends JpaRepository<CryptoWallet, UUID> {
    Page<CryptoWallet> findByUserId(UUID userId, Pageable pageable);
}
