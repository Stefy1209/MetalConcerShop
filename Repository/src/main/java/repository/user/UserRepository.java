package repository.user;

import repository.Repository;
import user.User;

import java.util.UUID;

public interface UserRepository extends Repository<UUID, User> {}