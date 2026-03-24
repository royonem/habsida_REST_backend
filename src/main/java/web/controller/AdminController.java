package web.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import web.dto.CreateUserDTO;
import web.dto.UpdateUserDTO;
import web.dto.UserResponseDTO;
import web.service.UserService;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<UserResponseDTO> userList() {
        return userService.getAllUsers();
    }

    @PostMapping("/users")
    public void createUser(@Valid @RequestBody CreateUserDTO dto) {
        userService.createUser(dto);
    }

    @GetMapping("/users/{id}")
    public UserResponseDTO getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PatchMapping("/users/{id}")
    public void updateUser(@Valid @PathVariable Long id, @RequestBody UpdateUserDTO dto) {
        dto.setId(id);
        userService.editUser(dto);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
