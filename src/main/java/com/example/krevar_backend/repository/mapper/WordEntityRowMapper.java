package com.example.krevar_backend.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

import com.example.krevar_backend.entity.WordEntity;

public class WordEntityRowMapper implements RowMapper<WordEntity> {

    @Override
    public WordEntity mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {

        long id = rs.getLong("id");
        String originalText = rs.getString("original_text");
        String translatedText = rs.getString("translated_text");
        String nuanceText = rs.getString("nuance_text");
        String imageUrl = rs.getString("image_url");
        long reviewIntervalId = rs.getLong("review_interval_id");
        LocalDate nextPracticeDate = rs.getDate("next_practice_date").toLocalDate();
        long correctCount = rs.getLong("correct_count");
        long incorrectCount = rs.getLong("incorrect_count");
        long isExtraModeCorrect = rs.getLong("is_extra_mode_correct");
        long deckId = rs.getLong("deck_id");
        LocalDateTime createdAt = rs.getObject("created_at", LocalDateTime.class);
        LocalDateTime updatedAt = rs.getObject("updated_at", LocalDateTime.class);
        int deleted = rs.getInt("deleted");

        WordEntity word = new WordEntity(id, originalText, translatedText, nuanceText, imageUrl,
                deckId, reviewIntervalId, nextPracticeDate, correctCount, incorrectCount,
                isExtraModeCorrect, createdAt, updatedAt, deleted);

        return word;
    }
}
