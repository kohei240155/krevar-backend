package com.example.krevar_backend.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import java.time.LocalDate;
import com.example.krevar_backend.entity.UserEntity;

public class UserEntityRowMapper implements RowMapper<UserEntity> {

    @Override
    public UserEntity mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {

        long id = rs.getInt("id");
        String email = rs.getString("email");
        String role = rs.getString("role");
        String googleId = rs.getString("google_id");
        String name = rs.getString("name");
        int defaultNativeLanguageId = rs.getInt("default_native_language_id");
        int defaultLearningLanguageId = rs.getInt("default_learning_language_id");
        int imageGenerationRemaining = rs.getInt("image_generation_remaining");
        LocalDate imageGenerationResetDate =
                rs.getObject("image_generation_reset_date", LocalDate.class);
        int subscriptionStatusId = rs.getInt("subscription_status_id");
        String subscriptionId = rs.getString("subscription_id");
        String highlightColor = rs.getString("highlight_color");
        LocalDateTime createdAt = rs.getObject("created_at", LocalDateTime.class);
        LocalDateTime updatedAt = rs.getObject("updated_at", LocalDateTime.class);
        int deleted = rs.getInt("deleted");

        UserEntity user = new UserEntity(id, email, role, googleId, name, defaultNativeLanguageId,
                defaultLearningLanguageId, imageGenerationRemaining, imageGenerationResetDate,
                subscriptionStatusId, subscriptionId, highlightColor, createdAt, updatedAt,
                deleted);
        return user;
    }
}
