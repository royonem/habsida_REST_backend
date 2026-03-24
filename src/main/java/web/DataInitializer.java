package web;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import web.entity.Role;
import web.entity.User;
import web.repository.RoleRepository;
import web.repository.UserRepository;
import java.util.List;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer (RoleRepository roleRepository,
                       UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (roleRepository.count() == 0) {
            Role admin = new Role("ROLE_ADMIN");
            Role user = new Role("ROLE_USER");
            roleRepository.saveAll(List.of(admin, user));
        }

        if (userRepository.count() == 0) {
            User adminUser = new User("admin1", passwordEncoder.encode("1234"));
            Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                    .orElseThrow(() -> new RuntimeException("ROLE_ADMIN not found"));
            adminUser.setRoles(Set.of(adminRole));
            userRepository.save(adminUser);
        }
    }
}
