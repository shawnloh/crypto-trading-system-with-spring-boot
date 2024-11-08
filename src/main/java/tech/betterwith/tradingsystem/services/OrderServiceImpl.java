package tech.betterwith.tradingsystem.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.betterwith.tradingsystem.constants.CryptoCurrency;
import tech.betterwith.tradingsystem.constants.OrderType;
import tech.betterwith.tradingsystem.dtos.OrderDto;
import tech.betterwith.tradingsystem.dtos.OrderTransactionDto;
import tech.betterwith.tradingsystem.exceptions.OrderException;
import tech.betterwith.tradingsystem.exceptions.ResourceNotFoundException;
import tech.betterwith.tradingsystem.models.AppUser;
import tech.betterwith.tradingsystem.models.CryptoPrice;
import tech.betterwith.tradingsystem.models.CryptoWallet;
import tech.betterwith.tradingsystem.models.Order;
import tech.betterwith.tradingsystem.repository.CryptoPriceRepository;
import tech.betterwith.tradingsystem.repository.CryptoWalletRepository;
import tech.betterwith.tradingsystem.repository.OrderRepository;
import tech.betterwith.tradingsystem.repository.UserRepository;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    private final CryptoPriceRepository cryptoPriceRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CryptoWalletRepository cryptoWalletRepository;

    @Autowired
    public OrderServiceImpl(CryptoPriceRepository cryptoPriceRepository, OrderRepository orderRepository,
                            UserRepository userRepository, CryptoWalletRepository cryptoWalletRepository) {
        this.cryptoPriceRepository = cryptoPriceRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.cryptoWalletRepository = cryptoWalletRepository;
    }


    public OrderTransactionDto order(OrderDto orderDto, AppUser user) {
        CryptoPrice cryptoPrice = cryptoPriceRepository.findTopBySymbolOrderByRetrievedAtDesc(orderDto.getSymbol()).orElseThrow(() -> new ResourceNotFoundException("Crypto Price", orderDto.getSymbol(), "symbol"));
        CryptoCurrency buyOrSellCurrency;
        if (orderDto.getSymbol().equals("BTCUSDT")) {
            buyOrSellCurrency = CryptoCurrency.BTC;
        } else if (orderDto.getSymbol().equals("ETHUSDT")) {
            buyOrSellCurrency = CryptoCurrency.ETH;
        } else {
            return new OrderTransactionDto("Order type not supported", false, orderDto);
        }

        if (orderDto.getOrderType() == OrderType.BUY) {
            return buy(orderDto, user, cryptoPrice, buyOrSellCurrency);
        } else if (orderDto.getOrderType() == OrderType.SELL) {
            return sell(orderDto, user, cryptoPrice, buyOrSellCurrency);
        }

        return new OrderTransactionDto("Order type not supported", false, orderDto);
    }

    private OrderTransactionDto buy(OrderDto orderDto, AppUser user, CryptoPrice cryptoPrice, CryptoCurrency buyOrSellCurrency) {
        // check if user has enough USDT
        BigDecimal balance = user.getWallets().stream().filter(wallet -> wallet.getCurrency() == CryptoCurrency.USDT).findFirst().orElseThrow(() -> new ResourceNotFoundException("Wallet", "USDT", "currency")).getBalance();
        if (balance.compareTo(orderDto.getQuantity().multiply(cryptoPrice.getAskPrice())) < 0) {
            throw new OrderException("Insufficient balance", orderDto);
        }

        // deduct USDT from user
        Order order = new Order(cryptoPrice.getSymbol(), orderDto.getOrderType(), orderDto.getQuantity(), cryptoPrice.getAskPrice().multiply(orderDto.getQuantity()), cryptoPrice, user);
        orderRepository.save(order);
        CryptoWallet usdtWallet = user.getWallets().stream().filter(wallet -> wallet.getCurrency() == CryptoCurrency.USDT).findFirst().orElseThrow(() -> new ResourceNotFoundException("Wallet", "USDT", "currency"));
        usdtWallet.setBalance(balance.subtract(orderDto.getQuantity().multiply(cryptoPrice.getAskPrice())));
        // add BTC/ETH to user
        Optional<CryptoWallet> existingWallet = user.getWallets().stream().filter(w -> w.getCurrency() == buyOrSellCurrency).findFirst();
        if (existingWallet.isPresent()) {
            existingWallet.get().setBalance(existingWallet.get().getBalance().add(orderDto.getQuantity()));
        } else {
            CryptoWallet wallet = new CryptoWallet(orderDto.getQuantity(), buyOrSellCurrency, user);
            cryptoWalletRepository.save(wallet);
        }
        userRepository.save(user);
        return new OrderTransactionDto("Order placed successfully", true, orderDto);
    }

    private OrderTransactionDto sell(OrderDto orderDto, AppUser user, CryptoPrice cryptoPrice, CryptoCurrency buyOrSellCurrency) {
        // check if user has enough BTC/ETH
        Optional<CryptoWallet> existingWallet = user.getWallets().stream().filter(w -> w.getCurrency() == buyOrSellCurrency).findFirst();
        if (existingWallet.isEmpty() || existingWallet.get().getBalance().compareTo(orderDto.getQuantity()) < 0) {
            throw new OrderException("Insufficient balance", orderDto);
        }

        // deduct BTC/ETH from user
        Order order = new Order(cryptoPrice.getSymbol(), orderDto.getOrderType(), orderDto.getQuantity(), cryptoPrice.getAskPrice().multiply(orderDto.getQuantity()), cryptoPrice, user);
        orderRepository.save(order);
        existingWallet.get().setBalance(existingWallet.get().getBalance().subtract(orderDto.getQuantity()));
        cryptoWalletRepository.save(existingWallet.get());

        // add USDT to user
        Optional<CryptoWallet> usdtWallet = user.getWallets().stream().filter(wallet -> wallet.getCurrency() == CryptoCurrency.USDT).findFirst();
        if (usdtWallet.isEmpty()) {
            CryptoWallet wallet = new CryptoWallet(orderDto.getQuantity().multiply(cryptoPrice.getBidPrice()), CryptoCurrency.USDT, user);
            cryptoWalletRepository.save(wallet);
        } else {
            usdtWallet.get().setBalance(usdtWallet.get().getBalance().add(orderDto.getQuantity().multiply(cryptoPrice.getBidPrice())));
            cryptoWalletRepository.save(usdtWallet.get());
        }
        userRepository.save(user);

        return new OrderTransactionDto("Order placed successfully", true, orderDto);
    }
}
