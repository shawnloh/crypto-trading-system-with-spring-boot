package tech.betterwith.tradingsystem.services;

import tech.betterwith.tradingsystem.dtos.OrderResponseDto;
import tech.betterwith.tradingsystem.dtos.PageResponseDto;
import tech.betterwith.tradingsystem.dtos.WalletResponseDto;

import java.util.UUID;

public interface ProfileService {
    PageResponseDto<WalletResponseDto> getWallets(UUID userId, int pageSize, int pageNumber, String sort, String sortOrder);

    PageResponseDto<OrderResponseDto> getOrders(UUID userId, int pageSize, int pageNumber, String sort, String sortOrder);
}
