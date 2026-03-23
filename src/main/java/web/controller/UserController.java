package web.controller;

import org.springframework.security.core.Authentication;
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
    public UserResponseDTO userView(Authentication authentication) {
        return userService.getUserByUsername(authentication.getName());
    }

}
