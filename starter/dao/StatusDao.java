package com.dmdev.jdbc.starter.dao;

import com.dmdev.jdbc.starter.entity.Status;
import com.dmdev.jdbc.starter.exception.DaoException;
import com.dmdev.jdbc.starter.util.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class StatusDao implements Dao<Integer, Status> {

    private static final StatusDao INSTANCE = new StatusDao();

    private static final String FIND_BY_ID_SQL = """
            SELECT  id,
            status_name
            FROM status
            WHERE id = ?
            """;

    private StatusDao() {
    }

    public static StatusDao getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public Status save(Status entity) {
        return null;
    }

    @Override
    public void update(Status entity) {

    }

    @Override
    public Optional<Status> findById(Integer id) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setInt(1, id);

            var resultSet = preparedStatement.executeQuery();
            Status status = null;
            if (resultSet.next()) {
                status = new Status(
                        resultSet.getInt("id"),
                        resultSet.getString("status_name")
                );
            }
            return Optional.ofNullable(status);
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }

    }

    @Override
    public List<Status> findAll() {
        return List.of();
    }
}
