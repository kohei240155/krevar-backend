package com.example.iruka_backend.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

import com.example.iruka_backend.entity.DeckEntity;

public class DeckEntityRowMapper implements RowMapper<DeckEntity> {

    @Override
    public DeckEntity mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
        DeckEntity deckEntity = new DeckEntity();
        deckEntity.setId(rs.getLong("id"));
        deckEntity.setCreatedAt(rs.getTimestamp("created_at"));
        deckEntity.setUpdatedAt(rs.getTimestamp("updated_at"));
        deckEntity.setDeletedAt(rs.getTimestamp("deleted_at") != null ? rs.getTimestamp("deleted_at") : null);
        return deckEntity;
    }
}
