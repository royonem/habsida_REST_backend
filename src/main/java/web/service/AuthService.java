package web.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import web.dto.auth.LoginRequestDTO;
import web.dto.auth.LoginResponseDTO;
import web.dto.auth.RegisterUserDTO;
import web.entity.Role;
import web.entity.User;
import web.exception.DuplicateUsernameException;
import web.exception.RoleNotFoundException;
import web.exception.UserNotFoundException;
import web.mapper.UserMapper;
import web.repository.RoleRepository;
import web.repository.UserRepository;
import web.security.JwtProvider;
import web.security.UserPrincipal;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(AuthenticationManager authenticationManager, UserRepository userRepository, JwtProvider jwtProvider, UserMapper userMapper, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
        this.userMapper = userMapper;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
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

    public void register(RegisterUserDTO dto) {
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new DuplicateUsernameException("Username already taken");
        }
        User user = userMapper.createUserFromDto(dto);

        Role defaultRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RoleNotFoundException("Default role not found"));
        if (user.getRoles().isEmpty()) {
            user.getRoles().add(defaultRole);
        }
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        userRepository.save(user);
    }

}