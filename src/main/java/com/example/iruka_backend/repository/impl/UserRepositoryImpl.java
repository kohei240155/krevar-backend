package com.example.iruka_backend.repository.impl;

import com.example.iruka_backend.entity.LanguageEntity;
import com.example.iruka_backend.entity.SubscriptionEntity;
import com.example.iruka_backend.entity.UserEntity;
import com.example.iruka_backend.entity.UserLoginEntity;
import com.example.iruka_backend.entity.UserSubscriptionEntity;
import com.example.iruka_backend.repository.UserRepository;
import com.example.iruka_backend.repository.mapper.LanguageEntityRowMapper;
import com.example.iruka_backend.repository.mapper.UserEntityRowMapper;
import com.example.iruka_backend.requestdto.UserSettingsUpdateRequest;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
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
        params.put("subscriptionStatusId", userLoginEntity.getSubscriptionStatusId());
        params.put("imageGenerationRemaining", userLoginEntity.getImageGenerationRemaining());
        params.put("createdAt", LocalDateTime.now());
        params.put("updatedAt", LocalDateTime.now());
        namedParameterJdbcTemplate.update(SQL_SAVE_NEW_USER, params);
    }

    private static final String SQL_SAVE_NEW_USER =
            """
                    INSERT INTO
                        users (email, name, google_id, role, subscription_status_id, image_generation_remaining, created_at, updated_at)
                    VALUES
                        (:email, :name, :googleId, :role, :subscriptionStatusId, :imageGenerationRemaining, :createdAt, :updatedAt)
                    """;

    /**
     * ユーザー情報を更新する
     *
     * @param user ユーザー
     */
    @Override
    public void update(Long userId, UserSettingsUpdateRequest userSettingsUpdateRequest) {
        // パラメータをマップに格納
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("defaultNativeLanguageId", userSettingsUpdateRequest.getNativeLanguageId());
        params.put("defaultLearningLanguageId", userSettingsUpdateRequest.getLearningLanguageId());
        params.put("highlightColor", userSettingsUpdateRequest.getHighlightColor());
        params.put("updatedAt", LocalDateTime.now());

        namedParameterJdbcTemplate.update(SQL_UPDATE_USER, params);
    }

    private static final String SQL_UPDATE_USER = """
            UPDATE
                users
            SET
                default_native_language_id = :defaultNativeLanguageId,
                default_learning_language_id = :defaultLearningLanguageId,
                highlight_color = :highlightColor,
                updated_at = :updatedAt
            WHERE
                id = :userId
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

    @Override
    public List<LanguageEntity> findAllLanguage() {
        return namedParameterJdbcTemplate.query(SQL_FIND_ALL_LANGUAGE,
                new LanguageEntityRowMapper());
    }

    private static final String SQL_FIND_ALL_LANGUAGE = """
            SELECT
                *
            FROM
                languages
            WHERE
                deleted = 0
            """;

    @Override
    public void saveUserSubscription(UserSubscriptionEntity userSubscription) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userSubscription.getId());
        params.put("subscriptionStatusId", userSubscription.getSubscriptionStatusId());
        params.put("imageGenerationRemaining", userSubscription.getImageGenerationRemaining());
        params.put("imageGenerationResetDate", userSubscription.getImageGenerationResetDate());
        params.put("subscriptionId", userSubscription.getSubscriptionId());
        params.put("updatedAt", LocalDateTime.now());

        namedParameterJdbcTemplate.update(SQL_SAVE_USER_SUBSCRIPTION, params);
    }

    private static final String SQL_SAVE_USER_SUBSCRIPTION = """
            UPDATE
                 users
             SET
                 image_generation_remaining = :imageGenerationRemaining,
                 image_generation_reset_date = :imageGenerationResetDate,
                 subscription_id = :subscriptionId,
                 subscription_status_id = :subscriptionStatusId,
                 updated_at = :updatedAt
             WHERE
                 id = :userId
             """;

    @Override
    public void cancelSubscription(Long userId, SubscriptionEntity subscription) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("updatedAt", LocalDateTime.now());
        params.put("subscriptionStatusId", subscription.getId());
        params.put("imageGenerationRemaining", 0);
        params.put("imageGenerationResetDate", LocalDateTime.now());
        namedParameterJdbcTemplate.update(SQL_CANCEL_SUBSCRIPTION, params);
    }

    private static final String SQL_CANCEL_SUBSCRIPTION = """
            UPDATE
                users
            SET
                subscription_id = null,
                subscription_status_id = :subscriptionStatusId,
                image_generation_remaining = :imageGenerationRemaining,
                image_generation_reset_date = :imageGenerationResetDate,
                updated_at = :updatedAt
            WHERE
                id = :userId
            """;
}
