package tech.betterwith.tradingsystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.betterwith.tradingsystem.dtos.OrderResponseDto;
import tech.betterwith.tradingsystem.dtos.PageResponseDto;
import tech.betterwith.tradingsystem.dtos.WalletResponseDto;
import tech.betterwith.tradingsystem.models.AppUser;
import tech.betterwith.tradingsystem.services.ProfileService;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {
    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/wallets")
    public ResponseEntity<PageResponseDto<WalletResponseDto>> getWallets(
            Authentication authentication,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(value = "pageNum", defaultValue = "0") int pageNum,
            @RequestParam(value = "sortBy", defaultValue = "currency") String sortBy,
            @RequestParam(value = "sortOrder", defaultValue = "desc") String sortOrder
    ) {
        AppUser user = (AppUser) authentication.getPrincipal();
        return ResponseEntity.ok(profileService.getWallets(user.getId(), pageSize, pageNum, sortBy, sortOrder));
    }

    @GetMapping("/trades")
    public ResponseEntity<PageResponseDto<OrderResponseDto>> getTrades(
            Authentication authentication,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(value = "pageNum", defaultValue = "0") int pageNum,
            @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy,
            @RequestParam(value = "sortOrder", defaultValue = "desc") String sortOrder
    ) {
        AppUser user = (AppUser) authentication.getPrincipal();
        return ResponseEntity.ok(profileService.getOrders(user.getId(), pageSize, pageNum, sortBy, sortOrder));
    }
}
