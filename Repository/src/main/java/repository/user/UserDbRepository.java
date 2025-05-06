package repository.user;

import user.User;
import user.UserRole;
import utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class UserDbRepository implements UserRepository {
    private final JdbcUtils jdbcUtils;

    public UserDbRepository(Properties properties) {
        jdbcUtils = new JdbcUtils(properties);
    }

    @Override
    public Optional<User> findOne(UUID uuid) {
        Connection connection = jdbcUtils.getConnection();
        Optional<User> user = Optional.empty();

        try(PreparedStatement preparedStatement = connection.prepareStatement("select username, password, role from users where id = ?")) {
            preparedStatement.setString(1, uuid.toString());
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    UserRole role = UserRole.fromCode(resultSet.getInt("role"));
                    user = Optional.of(new User(uuid, username, password, role));
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getSQLState());
            System.err.println(e.getErrorCode());
            System.err.println(e.getMessage());
        }

        return user;
    }

    @Override
    public Iterable<User> findAll() {
        Connection connection = jdbcUtils.getConnection();
        List<User> users = new ArrayList<>();

        try(PreparedStatement preparedStatement = connection.prepareStatement("select id, username, password, role from users")) {
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    UUID uuid = UUID.fromString(resultSet.getString("id"));
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    UserRole role = UserRole.fromCode(resultSet.getInt("role"));
                    users.add(new User(uuid, username, password, role));
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getSQLState());
            System.err.println(e.getErrorCode());
            System.err.println(e.getMessage());
        }

        return users;
    }

    @Override
    public Optional<User> save(User entity) {
        Connection connection = jdbcUtils.getConnection();
        Optional<User> user = Optional.of(entity);

        try(PreparedStatement preparedStatement = connection.prepareStatement("insert into users(id, username, password, role) values (?, ?, ?, ?)")) {
            preparedStatement.setString(1,entity.getId().toString());
            preparedStatement.setString(2,entity.getUsername());
            preparedStatement.setString(3,entity.getPassword());
            preparedStatement.setInt(4,entity.getRole().getCode());
            preparedStatement.executeUpdate();
            user = Optional.empty();

        } catch (SQLException e) {
            System.err.println(e.getSQLState());
            System.err.println(e.getErrorCode());
            System.err.println(e.getMessage());
        }

        return user;
    }

    @Override
    public Optional<User> delete(UUID uuid) {
        Optional<User> user = findOne(uuid);

        if(user.isPresent()) {
            Connection connection = jdbcUtils.getConnection();

            try(PreparedStatement preparedStatement = connection.prepareStatement("delete from users where id = ?")) {
                preparedStatement.setString(1, uuid.toString());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                System.err.println(e.getSQLState());
                System.err.println(e.getErrorCode());
                System.err.println(e.getMessage());
            }
        }

        return user;
    }

    @Override
    public Optional<User> update(User entity) {
        Connection connection = jdbcUtils.getConnection();
        Optional<User> user = Optional.of(entity);

        try(PreparedStatement preparedStatement = connection.prepareStatement("update users set username = ?, password = ?, role = ? where id = ?")) {
            preparedStatement.setString(1,entity.getUsername());
            preparedStatement.setString(2,entity.getPassword());
            preparedStatement.setInt(3,entity.getRole().getCode());
            preparedStatement.setString(4,entity.getId().toString());
            preparedStatement.executeUpdate();
            user = Optional.empty();
        } catch (SQLException e) {
            System.err.println(e.getSQLState());
            System.err.println(e.getErrorCode());
            System.err.println(e.getMessage());
        }

        return user;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        Connection connection = jdbcUtils.getConnection();
        Optional<User> user = Optional.empty();

        try(PreparedStatement preparedStatement = connection.prepareStatement("select id, password, role from users where username = ?")) {
            preparedStatement.setString(1,username);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    UUID uuid = UUID.fromString(resultSet.getString("id"));
                    String password = resultSet.getString("password");
                    UserRole role = UserRole.fromCode(resultSet.getInt("role"));
                    user = Optional.of(new User(uuid, username, password, role));
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getSQLState());
            System.err.println(e.getErrorCode());
            System.err.println(e.getMessage());
        }

        return user;
    }
}
