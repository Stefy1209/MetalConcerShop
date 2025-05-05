package user;

import core.Entity;

import java.util.Objects;
import java.util.UUID;

public abstract class AbstractUser extends Entity<UUID> {
    private String username;
    private String password;

    public AbstractUser(UUID id, String username, String password) {
        super(id);
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

    public abstract UserRole getRole();

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AbstractUser that = (AbstractUser) o;
        return Objects.equals(username, that.username) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), username, password);
    }
}