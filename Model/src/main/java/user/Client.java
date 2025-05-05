package user;

import java.util.UUID;

public class Client extends AbstractUser {
    public Client(UUID id, String username, String password) {
        super(id, username, password);
    }

    @Override
    public UserRole getRole() {
        return UserRole.Client;
    }
}
