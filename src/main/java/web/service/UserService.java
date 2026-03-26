package web.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dto.admin.CreateUserDTO;
import web.dto.admin.UpdateUserDTO;
import web.dto.UserResponseDTO;
import web.entity.User;
import web.exception.DuplicateUsernameException;
import web.exception.UserNotFoundException;
import web.mapper.UserMapper;
import web.repository.RoleRepository;
import web.repository.UserRepository;
import java.util.List;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleService roleService;
    private final RoleRepository roleRepository;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository, UserMapper userMapper, RoleService roleService, RoleRepository roleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.roleService = roleService;
        this.roleRepository = roleRepository;
    }

    public void createUser(CreateUserDTO dto) {
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new DuplicateUsernameException("Username already taken");
        }
        User user = userMapper.createUserFromDto(dto, roleRepository);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        userRepository.save(user);
    }

    @Transactional
    public void editUser(UpdateUserDTO dto) {
        User user = userRepository.findById(dto.getId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        String newUsername = dto.getUsername();
        String oldUsername = user.getUsername();
        if (newUsername != null
                && !newUsername.equals(oldUsername)
                && userRepository.existsByUsername(newUsername)) {
            throw new DuplicateUsernameException("Username already taken");
        }
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
            user.getRoles().add(roleService.getRoleById(roleId));
        }
    }

    public List<UserResponseDTO> getAllUsers() {
        return userMapper.toResponseDto(
                userRepository.findAllByOrderByIdAsc()
        );
    }

    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return userMapper.toResponseDto(user);
    }

    public UserResponseDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return userMapper.toResponseDto(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}
