package web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import web.dto.CreateUserDTO;
import web.dto.UpdateUserDTO;
import web.dto.UserResponseDTO;
import web.entity.Role;
import web.entity.User;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User createUserFromDto(CreateUserDTO dto);

    @Mapping(target = "password", ignore = true)
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
}
