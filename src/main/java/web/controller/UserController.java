package web.controller;

import org.springframework.web.bind.annotation.*;
import web.dto.UserResponseDTO;
import web.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/view")
    public UserResponseDTO userView(@RequestParam Long userId) {
        return userService.getUserById(userId);
    }

}
