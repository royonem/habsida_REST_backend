package web.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import web.dto.CreateUserDTO;
import web.dto.LoginRequestDTO;
import web.dto.LoginResponseDTO;
import web.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO request) {
        return authService.login(request);
    }

    @PostMapping("/register")
    public void register(@Valid @RequestBody CreateUserDTO dto) {
        authService.register(dto);
    }


}
