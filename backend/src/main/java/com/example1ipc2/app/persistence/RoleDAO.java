    
package com.example1ipc2.app.persistence;

import com.example1ipc2.app.aplication.DBConnection;
import com.example1ipc2.app.model.RoleModel;
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
public class RoleDAO extends CrudDAO<RoleModel> {

    @Override
    public RoleModel insert(RoleModel entity) throws SQLException {
        String query = "INSERT INTO role (role_name, description) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement 
                stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, entity.getName());
            stmt.setString(2, entity.getDescription());
            
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
    public RoleModel findById(Integer id) throws SQLException {
        String sql = "SELECT * FROM role WHERE id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new RoleModel(rs.getInt("id"), rs.getString("role_name"), rs.getString("description"));
                }
            }
        }
        return null;
    }

    @Override
    public List<RoleModel> findAll() throws SQLException {
        List<RoleModel> roles = new ArrayList<>();
        String sql = "SELECT * FROM role";
        try (Connection conn = DBConnection.getConnection(); 
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                roles.add(new RoleModel(rs.getInt("id"), rs.getString("role_name"), rs.getString("description")));
            }
        }
        return roles;
    }

    @Override
    public void update(RoleModel entity) throws SQLException {
        
        String sql = "UPDATE role SET role_name = ?, description = ?, WHERE id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, entity.getName());
            stmt.setString(2, entity.getDescription());
            stmt.setInt(3, entity.getId());

            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(Integer id) throws SQLException {
        String sql = "DELETE FROM role WHERE id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

}
