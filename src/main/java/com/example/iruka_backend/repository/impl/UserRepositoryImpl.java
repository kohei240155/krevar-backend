package com.example.iruka_backend.repository.impl;

import com.example.iruka_backend.entity.UserEntity;
import com.example.iruka_backend.entity.UserLoginEntity;
import com.example.iruka_backend.entity.UserSettingsEntity;
import com.example.iruka_backend.repository.UserRepository;
import com.example.iruka_backend.repository.mapper.UserEntityRowMapper;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public UserEntity findUserByUserId(Long userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);

        try {
            return namedParameterJdbcTemplate.queryForObject(SQL_FIND_USER_BY_USER_ID, params,
                    new UserEntityRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private static final String SQL_FIND_USER_BY_USER_ID = """
            SELECT
                *
            FROM
                users
            WHERE
                id = :userId
                AND deleted = 0
            """;

    @Override
    public void saveNewUser(UserLoginEntity userLoginEntity) {
        // パラメータをマップに格納
        Map<String, Object> params = new HashMap<>();
        params.put("email", userLoginEntity.getEmail());
        params.put("name", userLoginEntity.getName());
        params.put("googleId", userLoginEntity.getGoogleId());
        params.put("role", userLoginEntity.getRole());
        params.put("createdAt", LocalDateTime.now());
        params.put("updatedAt", LocalDateTime.now());
        namedParameterJdbcTemplate.update(SQL_SAVE_NEW_USER, params);
    }

    private static final String SQL_SAVE_NEW_USER = """
            INSERT INTO
                users (email, name, google_id, role, created_at, updated_at)
            VALUES
                (:email, :name, :googleId, :role, :createdAt, :updatedAt)
            """;

    /**
     * ユーザー情報を更新する
     *
     * @param user ユーザー
     */
    @Override
    public void update(UserSettingsEntity userSettingsEntity) {
        // パラメータをマップに格納
        Map<String, Object> params = new HashMap<>();
        params.put("defaultNativeLanguageId", userSettingsEntity.getDefaultNativeLanguageId());
        params.put("defaultLearningLanguageId", userSettingsEntity.getDefaultLearningLanguageId());
        params.put("highlightColor", userSettingsEntity.getHighlightColor());
        params.put("updatedAt", LocalDateTime.now());

        namedParameterJdbcTemplate.update(SQL_UPDATE_USER, params);
    }

    private static final String SQL_UPDATE_USER =
            """
                    UPDATE
                        users
                    SET
                        default_native_language_id = :defaultNativeLanguageId, default_learning_language_id = :defaultLearningLanguageId, highlight_color = :highlightColor, updated_at = :updatedAt
                    WHERE
                        email = :email
                    """;

    @Override
    public UserEntity findUserByEmail(String email) {
        Map<String, Object> params = new HashMap<>();
        params.put("email", email);

        try {
            return namedParameterJdbcTemplate.queryForObject(SQL_FIND_BY_EMAIL, params,
                    new UserEntityRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private static final String SQL_FIND_BY_EMAIL = """
            SELECT
                *
            FROM
                users
            WHERE
                email = :email
                AND deleted = 0
            """;

}
