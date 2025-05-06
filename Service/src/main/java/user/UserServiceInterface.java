package user;

public interface UserServiceInterface {
    boolean logIn(String username, String password, UserRole role);
    boolean signUp(String username, String password, UserRole role);
}