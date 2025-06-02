package user;

import core.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Table;

import java.util.Objects;
import java.util.UUID;

@jakarta.persistence.Entity
@Table(name = "users")
public class User extends Entity<UUID> {

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Convert(converter = UserRoleConverter.class)
    @Column(name = "role")
    private UserRole role;

    public User() {
        super();
    }

    public User(UUID id, String username, String password, UserRole role) {
        super(id);
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {}

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) && Objects.equals(password, user.password) && role == user.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), username, password, role);
    }
}