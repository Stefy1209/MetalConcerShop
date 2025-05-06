package repository.user;

import repository.Repository;
import user.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends Repository<UUID, User> {
    /**
     *
     * @param username -the username of the entity to be returned
     *           id must not be null
     * @return an {@code Optional} encapsulating the entity with the given username
     * @throws IllegalArgumentException
     *                  if username is null.
     */
    Optional<User> findByUsername(String username);
}