package web.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dto.CreateUserDTO;
import web.dto.UpdateUserDTO;
import web.dto.UserResponseDTO;
import web.entity.Role;
import web.entity.User;
import web.mapper.UserMapper;
import web.repository.RoleRepository;
import web.repository.UserRepository;
import java.util.List;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final RoleService roleService;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository, RoleRepository roleRepository, UserMapper userMapper, RoleService roleService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
        this.roleService = roleService;
    }

    public void createUser(CreateUserDTO dto) {
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new RuntimeException("Username already taken");
        }
        User user = userMapper.createUserFromDto(dto);

        Role defaultRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Default role not found"));
        if (user.getRoles().isEmpty()) {
            user.getRoles().add(defaultRole);
        }
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        userRepository.save(user);
    }

    @Transactional
    public void editUser(UpdateUserDTO dto) {
        User user = userRepository.findById(dto.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        userMapper.updateUserFromDto(dto, user);
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        List<Long> roleIds = dto.getRoleIds();
        if (roleIds != null) {
            updateRolesByIds(user, roleIds);
        }
    }

    public void updateRolesByIds(User user, List<Long> roleIds) {
        user.getRoles().clear();
        for (Long roleId : roleIds) {
            Role role = roleRepository.findById(roleId)
                    .orElseThrow(() -> new RuntimeException("Role not found"));
            user.getRoles().add(role);
        }
    }

    public List<UserResponseDTO> getAllUsers() {
        return userMapper.toResponseDto(
                userRepository.findAllByOrderByIdAsc()
        );
    }

    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toResponseDto(user);
    }

    public UserResponseDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toResponseDto(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}
