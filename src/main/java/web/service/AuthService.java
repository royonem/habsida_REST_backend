package web.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import web.dto.CreateUserDTO;
import web.dto.LoginRequestDTO;
import web.dto.LoginResponseDTO;
import web.security.UserPrincipal;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public AuthService(AuthenticationManager authenticationManager, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    public LoginResponseDTO login(LoginRequestDTO request) {
        Authentication authentication =
                authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        UserPrincipal principal =
                (UserPrincipal) authentication.getPrincipal();

        String token = "temp-token";
        return new LoginResponseDTO(principal.getUsername(), token);
    }

    public void register(CreateUserDTO dto) {
        userService.createUser(dto);
    }

}