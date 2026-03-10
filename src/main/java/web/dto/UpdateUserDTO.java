package web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import web.enums.Country;
import web.enums.Gender;
import web.entity.Role;

import java.util.List;
import java.util.Set;

public class UpdateUserDTO {
    private Long id;
    @NotBlank(message = "Username is required")
    private String username;
    @NotNull(message = "Age is required")
    private Long age;
    @NotNull(message = "Gender is required")
    private Gender gender;
    @NotNull(message = "Country is required")
    private Country country;
    private String password;
    private String confirmPassword;
    private Set<Role> roles;
    @NotEmpty(message = "User must have at least one role")
    private List<Long> roleIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "Username is required") String getUsername() {
        return username;
    }

    public void setUsername(@NotBlank(message = "Username is required") String username) {
        this.username = username;
    }

    public @NotNull(message = "Age is required") Long getAge() {
        return age;
    }

    public void setAge(@NotNull(message = "Age is required") Long age) {
        this.age = age;
    }

    public @NotNull(message = "Gender is required") Gender getGender() {
        return gender;
    }

    public void setGender(@NotNull(message = "Gender is required") Gender gender) {
        this.gender = gender;
    }

    public @NotNull(message = "Country is required") Country getCountry() {
        return country;
    }

    public void setCountry(@NotNull(message = "Country is required") Country country) {
        this.country = country;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword (String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword (String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public List<Long> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Long> roleIds) {
        this.roleIds = roleIds;
    }
}
