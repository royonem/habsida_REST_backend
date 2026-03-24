package web.dto.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import web.enums.Country;
import web.enums.Gender;
import web.entity.Role;

import java.util.Set;

public class CreateUserDTO {
    private Long id;
    @NotBlank(message = "Username is required")
    private String username;
    @NotNull(message = "Age is required")
    private Long age;
    @NotNull(message = "Gender is required")
    private Gender gender;
    @NotNull(message = "Country is required")
    private Country country;
    @NotBlank(message = "Password is required")
    private String password;
    @NotBlank(message = "Please confirm your password")
    private String confirmPassword;
    private Set<Role> roles;

    // getters and setters
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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public @NotBlank(message = "Password is required") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "Password is required") String password) {
        this.password = password;
    }

    public @NotBlank(message = "Please confirm your password") String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(@NotBlank(message = "Please confirm your password") String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
