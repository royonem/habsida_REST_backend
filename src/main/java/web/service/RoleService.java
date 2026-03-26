package web.service;

import org.springframework.stereotype.Service;
import web.entity.Role;
import web.exception.RoleNotFoundException;
import web.repository.RoleRepository;

import java.util.List;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    public Role getRoleById(Long roleId) {
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new RoleNotFoundException("Role not found"));
    }

    public List<String> getRoleNames() {
        return roleRepository.findAll()
                .stream()
                .map(Role::getName)
                .toList();
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
