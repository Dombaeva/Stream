package com.dmdev.jdbc.starter.dao;

import com.dmdev.jdbc.starter.dto.RoleFilter;
import com.dmdev.jdbc.starter.entity.Role;
import com.dmdev.jdbc.starter.entity.User;
import com.dmdev.jdbc.starter.exception.DaoException;
import com.dmdev.jdbc.starter.util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class RoleDao implements Dao<Integer, Role> {
    private static final RoleDao INSTANCE = new RoleDao();

    private static final String DELETE_SQL = """
            DELETE FROM role
            WHERE id = ?
            """;

    private static final String SAVE_SQL = """
            INSERT INTO role(id,name) 
            VALUES (?,?) 
            """;

    private static final String UPDATE_SQl = """
            UPDATE role
            SET id=?,
            name=?
            """;
    private static final String FIND_ALL_SQL = """
            SELECT id, 
            name
            FROM role
            """;
    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id=?
            """;


    private RoleDao() {
    }

    public List<Role> findAll(RoleFilter filter) {
        List<Object> parameters = new ArrayList<>();
        parameters.add(filter.limit());
        parameters.add(filter.offset());
        var sql = FIND_ALL_SQL + """
                LIMIT ?
                OFFSET ? 
                """;
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(sql)) {
            for (int i = 0; i < parameters.size(); i++) {
                preparedStatement.setObject(i + 1, parameters.get(i));
            }
            System.out.println(preparedStatement);
            var resultSet = preparedStatement.executeQuery();
            List<Role> roles = new ArrayList<>();
            while (resultSet.next()) {
                roles.add(buildRole(resultSet));
            }
            return roles;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public List<Role> findAll() {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            var resultSet = preparedStatement.executeQuery();
            List<Role> roles = new ArrayList<>();
            while (resultSet.next()) {
                roles.add(buildRole(resultSet));
            }
            return roles;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public Optional<Role> findById(Integer id) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setInt(1, id);
            var resultSet = preparedStatement.executeQuery();
            Role role = null;
            if (resultSet.next()) {
                role = buildRole(resultSet);
            }
            return Optional.ofNullable(role);
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    private Role buildRole(ResultSet resultSet) throws SQLException {
        return new Role(
                resultSet.getInt("id"),
                resultSet.getString("name")
        );
    }

    public void update(Role role) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(UPDATE_SQl)) {
            preparedStatement.setInt(1, role.getId());
            preparedStatement.setString(2, role.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public Role save(Role role) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, role.getId());
            preparedStatement.setString(2, role.getName());
            preparedStatement.executeUpdate();
            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                role.setId(generatedKeys.getInt("id"));
            }
            return role;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public boolean delete(Integer id) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public static RoleDao getInstance() {
        return INSTANCE;
    }
}
