package com.example1ipc2.app.persistence;

import com.example1ipc2.app.aplication.DBConnection;
import com.example1ipc2.app.model.UserModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author elvis
 */
public class UserDAO extends CrudDAO<UserModel> {

    @Override
    public UserModel insert(UserModel entity) throws SQLException {
        String sql = "INSERT INTO user (name, email, address, dpi, password, role_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, entity.getName());
            stmt.setString(2, entity.getEmail());
            stmt.setString(3, entity.getAddress());
            stmt.setString(4, entity.getDpi());
            stmt.setString(5, entity.getPassword());
            stmt.setInt(6, entity.getRoleId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        entity.setId(generatedKeys.getInt(1));
                    }
                }
            }
        }
        return entity;
    }

    @Override
    public UserModel findById(Long id) throws SQLException {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new UserModel(
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("address"),
                            rs.getString("dpi"),
                            rs.getString("password"),
                            rs.getInt("role_id")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public List<UserModel> findAll() throws SQLException {
        List<UserModel> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                users.add(new UserModel(
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("address"),
                        rs.getString("dpi"),
                        rs.getString("password"),
                        rs.getInt("role_id")
                ));
            }
        }
        return users;
    }

    @Override
    public void update(UserModel entity) throws SQLException {
        String sql = "UPDATE users SET name = ?, email = ?, address = ?, dpi = ?, password = ?, role_id = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, entity.getName());
            stmt.setString(2, entity.getEmail());
            stmt.setString(3, entity.getAddress());
            stmt.setString(4, entity.getDpi());
            stmt.setString(5, entity.getPassword());
            stmt.setInt(6, entity.getRoleId());
            stmt.setInt(7, entity.getId());

            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }
}
