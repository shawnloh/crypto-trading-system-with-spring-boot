package tech.betterwith.tradingsystem;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import tech.betterwith.tradingsystem.constants.AppRole;
import tech.betterwith.tradingsystem.constants.CryptoCurrency;
import tech.betterwith.tradingsystem.models.AppUser;
import tech.betterwith.tradingsystem.models.CryptoWallet;
import tech.betterwith.tradingsystem.models.Role;
import tech.betterwith.tradingsystem.repository.RoleRepository;
import tech.betterwith.tradingsystem.repository.UserRepository;

import java.math.BigDecimal;

@SpringBootApplication
public class TradingSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(TradingSystemApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            Role role = new Role(AppRole.ROLE_USER);
            AppUser appUser = new AppUser();
            appUser.setUsername("user");
            appUser.setPassword(passwordEncoder.encode("password"));
            appUser.setFullName("User");
            appUser.addRole(role);
            CryptoWallet usdtCryptoWallet = new CryptoWallet(new BigDecimal("50000.0"), CryptoCurrency.USDT);
            appUser.addWallet(usdtCryptoWallet);
            userRepository.save(appUser);
        };
    }
}
