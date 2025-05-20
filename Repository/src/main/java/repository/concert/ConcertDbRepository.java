package repository.concert;

import concert.Concert;
import utils.JdbcUtils;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class ConcertDbRepository implements ConcertRepository {
    private final JdbcUtils jdbcUtils;

    public ConcertDbRepository(Properties properties) {
        jdbcUtils = new JdbcUtils(properties);
    }

    @Override
    public Optional<Concert> findOne(UUID uuid) {
        return Optional.empty();
    }

    @Override
    public Iterable<Concert> findAll() {
        Connection connection = jdbcUtils.getConnection();
        List<Concert> concerts = new ArrayList<>();

        try(PreparedStatement preparedStatement = connection.prepareStatement("select location, name, date, artist, id from concerts")) {
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String location = resultSet.getString("location");
                    String name = resultSet.getString("name");
                    LocalDateTime date = resultSet.getTimestamp("date").toLocalDateTime();
                    String artist = resultSet.getString("artist");
                    UUID id = UUID.fromString(resultSet.getString("id"));
                    concerts.add(new Concert(id, name, location, artist, date));
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getSQLState());
            System.err.println(e.getErrorCode());
            System.err.println(e.getMessage());
        }

        return concerts;
    }

    @Override
    public Optional<Concert> save(Concert entity) {
        Connection connection = jdbcUtils.getConnection();
        Optional<Concert> concert = Optional.of(entity);

        try(PreparedStatement preparedStatement = connection.prepareStatement("insert into concerts(location, name, date, artist, id) values (?, ?, ?, ?, ?)")) {
            preparedStatement.setString(1, entity.getLocation());
            preparedStatement.setString(2, entity.getName());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(entity.getDate()));
            preparedStatement.setString(4, entity.getArtist());
            preparedStatement.setString(5, entity.getId().toString());
            preparedStatement.executeUpdate();
            concert = Optional.empty();
        } catch (SQLException e) {
            System.err.println(e.getSQLState());
            System.err.println(e.getErrorCode());
            System.err.println(e.getMessage());
        }

        return concert;
    }

    @Override
    public Optional<Concert> delete(UUID uuid) {
        return Optional.empty();
    }

    @Override
    public Optional<Concert> update(Concert entity) {
        return Optional.empty();
    }
}
