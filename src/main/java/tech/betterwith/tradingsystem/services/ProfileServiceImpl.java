package tech.betterwith.tradingsystem.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import tech.betterwith.tradingsystem.dtos.OrderResponseDto;
import tech.betterwith.tradingsystem.dtos.PageResponseDto;
import tech.betterwith.tradingsystem.dtos.WalletResponseDto;
import tech.betterwith.tradingsystem.models.CryptoWallet;
import tech.betterwith.tradingsystem.models.Order;
import tech.betterwith.tradingsystem.repository.CryptoWalletRepository;
import tech.betterwith.tradingsystem.repository.OrderRepository;

import java.util.List;
import java.util.UUID;

@Service
public class ProfileServiceImpl implements ProfileService {
    private final CryptoWalletRepository cryptoWalletRepository;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ProfileServiceImpl(CryptoWalletRepository cryptoWalletRepository, OrderRepository orderRepository, ModelMapper modelMapper) {
        this.cryptoWalletRepository = cryptoWalletRepository;
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PageResponseDto<WalletResponseDto> getWallets(UUID userId, int pageSize, int pageNumber, String sort, String sortOrder) {
        Sort sortBy = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sort).ascending() : Sort.by(sort).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortBy);
        Page<CryptoWallet> walletsPage = cryptoWalletRepository.findByUserId(userId, pageable);
        List<WalletResponseDto> walletDtos = walletsPage.getContent().stream().map(wallet -> modelMapper.map(wallet, WalletResponseDto.class)).toList();
        return new PageResponseDto<>(walletDtos, walletsPage.getSize(), walletsPage.getNumber(), walletsPage.getTotalPages(), walletsPage.getTotalElements(), walletsPage.isLast());
    }

    @Override
    public PageResponseDto<OrderResponseDto> getOrders(UUID userId, int pageSize, int pageNumber, String sort, String sortOrder) {
        Sort sortBy = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sort).ascending() : Sort.by(sort).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortBy);
        Page<Order> ordersPage = orderRepository.findByUserId(userId, pageable);
        List<OrderResponseDto> orderDtos = ordersPage.getContent().stream().map(order -> modelMapper.map(order, OrderResponseDto.class)).toList();
        return new PageResponseDto<>(orderDtos, ordersPage.getSize(), ordersPage.getNumber(), ordersPage.getTotalPages(), ordersPage.getTotalElements(), ordersPage.isLast());
    }


}
