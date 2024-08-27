package com.example.iruka_backend.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

import com.example.iruka_backend.entity.WordEntity;

public class WordEntityRowMapper implements RowMapper<WordEntity> {

    @Override
    public WordEntity mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
        WordEntity word = new WordEntity();
        word.setId(rs.getLong("id"));
        word.setOriginalText(rs.getString("original_text"));
        word.setTranslatedText(rs.getString("translated_text"));
        word.setNuanceText(rs.getString("nuance_text"));
        word.setImageUrl(rs.getString("image_url"));
        word.setReviewIntervalId(rs.getLong("review_interval_id"));
        word.setNextPracticeDate(rs.getDate("next_practice_date").toLocalDate());
        word.setCorrectCount(rs.getLong("correct_count"));
        word.setIncorrectCount(rs.getLong("incorrect_count"));
        word.setIsNormalModeCorrect(rs.getBoolean("is_normal_mode_correct"));
        word.setIsExtraModeCorrect(rs.getBoolean("is_extra_mode_correct"));
        word.setDeckId(rs.getLong("deck_id"));
        word.setCreatedAt(rs.getTimestamp("created_at"));
        word.setUpdatedAt(rs.getTimestamp("updated_at"));
        word.setDeletedAt(rs.getTimestamp("deleted_at"));
        return word;
    }
}
