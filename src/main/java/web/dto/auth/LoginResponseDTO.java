package web.dto.auth;

import java.util.Set;

public class LoginResponseDTO {
    private String username;
    private Set<String> roleNames;
    private String token;

    public LoginResponseDTO(String username, String token, Set<String> roleNames) {
        this.username = username;
        this.token = token;
        this.roleNames = roleNames;
    }

    // getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<String> getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(Set<String> roleNames) {
        this.roleNames = roleNames;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
