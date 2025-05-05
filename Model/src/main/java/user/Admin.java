package user;

import java.util.UUID;

public class Admin extends AbstractUser {
    public Admin(UUID id, String username, String password) {
        super(id, username, password);
    }

    @Override
    public UserRole getRole() {
        return UserRole.Admin;
    }
}
