package web.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import web.dto.admin.CreateUserDTO;
import web.dto.admin.UpdateUserDTO;
import web.dto.UserResponseDTO;
import web.dto.auth.RegisterUserDTO;
import web.entity.Role;
import web.entity.User;
import web.repository.RoleRepository;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "username", expression = "java(trimUsername(dto.getUsername()))")
    @Mapping(target = "roles", expression = "java(mapRoleIdsToRoles(dto.getRoleIds(), roleRepository))")
    User createUserFromDto(CreateUserDTO dto, @Context RoleRepository roleRepository);

    @Mapping(target = "username", expression = "java(trimUsername(dto.getUsername()))")
    User createUserFromDto(RegisterUserDTO dto);

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    void updateUserFromDto(UpdateUserDTO dto, @MappingTarget User user);

    @Mapping(target = "roleNames", source = "roles")
    @Mapping(target = "roleIds", source = "roles")
    UserResponseDTO toResponseDto(User user);

    List<UserResponseDTO> toResponseDto(List<User> users);

    default List<String> mapRolesToNames(Set<Role> roles) {
        if (roles == null) return List.of();
        return roles.stream()
                .map(role -> role.getName().replace("ROLE_", ""))
                .toList();
    }

    default List<Long> mapRolesToIds(Set<Role> roles) {
        if (roles == null) return List.of();
        return roles.stream().map(Role::getId).toList();
    }

    default Set<Role> mapRoleIdsToRoles(List<Long> roleIds, @Context RoleRepository roleRepository) {
        if (roleIds == null || roleIds.isEmpty()) return Set.of();
        return Set.copyOf(roleRepository.findAllById(roleIds));
    }


    default String trimUsername(String username) {
        return username == null ? null : username.trim();
    }
}
