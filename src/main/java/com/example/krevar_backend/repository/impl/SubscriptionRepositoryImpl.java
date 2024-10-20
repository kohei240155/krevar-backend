package com.example.krevar_backend.repository.impl;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import com.example.krevar_backend.entity.SubscriptionEntity;
import com.example.krevar_backend.repository.SubscriptionRepository;
import com.example.krevar_backend.repository.mapper.SubscriptionEntityRowMapper;
import jakarta.transaction.Transactional;

@Transactional
@Repository
public class SubscriptionRepositoryImpl implements SubscriptionRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * サブスクリプションを取得
     *
     * @param plan プラン
     * @return サブスクリプション
     */
    @Override
    public SubscriptionEntity getSubscription(String plan) {

        Map<String, Object> params = new HashMap<>();
        params.put("plan", plan);

        SubscriptionEntity subscription = namedParameterJdbcTemplate
                .queryForObject(SQL_GET_SUBSCRIPTION_ID, params, new SubscriptionEntityRowMapper());

        return subscription;
    }

    private static final String SQL_GET_SUBSCRIPTION_ID = """
            SELECT
                id,
                plan,
                max_image_generation
            FROM
                subscription_statuses
            WHERE
                plan = :plan
            """;

}
