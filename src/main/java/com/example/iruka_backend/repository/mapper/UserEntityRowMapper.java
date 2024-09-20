package com.example.iruka_backend.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

import com.example.iruka_backend.entity.UserEntity;

public class UserEntityRowMapper implements RowMapper<UserEntity> {

    @Override
    public UserEntity mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
        UserEntity user = new UserEntity();
        user.setId(rs.getLong("id"));
        user.setEmail(rs.getString("email"));
        user.setRole(rs.getString("role"));
        user.setGoogleId(rs.getString("google_id"));
        user.setCreatedAt(rs.getTimestamp("created_at"));
        user.setUpdatedAt(rs.getTimestamp("updated_at"));
        user.setDeleted(rs.getInt("deleted"));
        return user;
    }
}
