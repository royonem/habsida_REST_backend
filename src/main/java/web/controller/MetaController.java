package web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.enums.Country;
import web.enums.Gender;
import web.service.RoleService;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/meta")
public class MetaController {
    private final RoleService roleService;

    public MetaController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/genders")
    public List<String> getGenders() {
        return Arrays.stream(Gender.values())
                .map(Enum::name)
                .toList();
    }

    @GetMapping("/countries")
    public List<String> getCountries() {
        return Arrays.stream(Country.values())
                .map(Enum::name)
                .toList();
    }

    @GetMapping("/roles")
    public List<String> getRoleNames() {
        return roleService.getRoleNames();
    }
}
