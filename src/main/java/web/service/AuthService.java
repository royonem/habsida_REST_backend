package web.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import web.dto.CreateUserDTO;
import web.dto.LoginRequestDTO;
import web.dto.LoginResponseDTO;
import web.exception.UserNotFoundException;
import web.security.JwtProvider;
import web.security.UserPrincipal;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtProvider jwtProvider;

    public AuthService(AuthenticationManager authenticationManager, UserService userService, JwtProvider jwtProvider) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    public LoginResponseDTO login(LoginRequestDTO request) {
        try {
            // built-in Spring Security Interface
            // receives the unauthenticated token and delegates to a provider chain like DaoAuthProvider
            // provider tries to validate credentials
            // if valid, returns new Authentication object with principal, authorities, etc.
            Authentication authentication = authenticationManager.authenticate(
                    // creates token with credentials but is unauthenticated
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
            return createLoginResponse(authentication);
        } catch (AuthenticationException e) {
            throw new UserNotFoundException("Invalid username or password");
        }
    }

    private LoginResponseDTO createLoginResponse(Authentication authentication) {
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        Set<String> roleNames = principal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
        String token = jwtProvider.generateToken(principal.getUsername(), roleNames);
        return new LoginResponseDTO(principal.getUsername(), token, roleNames);
    }

    public void register(CreateUserDTO dto) {
        userService.createUser(dto);
    }

}