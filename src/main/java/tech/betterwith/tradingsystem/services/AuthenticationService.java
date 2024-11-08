package tech.betterwith.tradingsystem.services;

import tech.betterwith.tradingsystem.dtos.LoginResponseDto;

public interface AuthenticationService {
    LoginResponseDto login(String username, String password);
}
