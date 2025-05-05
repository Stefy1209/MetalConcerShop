package repository.user;

import repository.Repository;
import user.AbstractUser;

import java.util.UUID;

public interface UserRepository extends Repository<UUID, AbstractUser> {}