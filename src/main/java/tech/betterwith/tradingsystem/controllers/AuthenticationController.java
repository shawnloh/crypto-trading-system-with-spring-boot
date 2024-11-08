package tech.betterwith.tradingsystem.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.betterwith.tradingsystem.dtos.LoginResponseDto;
import tech.betterwith.tradingsystem.dtos.LoginUserDto;
import tech.betterwith.tradingsystem.services.AuthenticationService;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginUserDto loginUserDto) {
        LoginResponseDto loginResponseDto = authenticationService.login(loginUserDto.getEmail(), loginUserDto.getPassword());
        return ResponseEntity.ok(loginResponseDto);
    }
}
