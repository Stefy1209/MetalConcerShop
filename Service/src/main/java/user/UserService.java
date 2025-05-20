package user;

import org.mindrot.jbcrypt.BCrypt;
import repository.user.UserRepository;

import java.util.Optional;
import java.util.UUID;

public class UserService implements UserServiceInterface {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean logIn(String username, String password, UserRole role) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.filter(value -> BCrypt.checkpw(password, value.getPassword()) && role.equals(value.getRole())).isPresent();
    }

    @Override
    public boolean signUp(String username, String password, UserRole role) {
        /*if(role.equals(UserRole.Admin)) {
            return false;
        }*/

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        User user = new User(UUID.randomUUID(), username, hashedPassword, role);
        Optional<User> result = userRepository.save(user);

        return result.isEmpty();
    }
}
