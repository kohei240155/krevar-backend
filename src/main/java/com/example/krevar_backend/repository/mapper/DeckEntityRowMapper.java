package com.example.krevar_backend.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import com.example.krevar_backend.entity.DeckEntity;

public class DeckEntityRowMapper implements RowMapper<DeckEntity> {

  @Override
  public DeckEntity mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
    long id = rs.getInt("id");
    long userId = rs.getInt("user_id");
    String deckName = rs.getString("deck_name");
    LocalDateTime createdAt = rs.getObject("created_at", LocalDateTime.class);
    LocalDateTime updatedAt = rs.getObject("updated_at", LocalDateTime.class);
    int deleted = rs.getInt("deleted");

    DeckEntity deckEntity = new DeckEntity(id, deckName, userId, createdAt, updatedAt, deleted);

    return deckEntity;
  }
}
