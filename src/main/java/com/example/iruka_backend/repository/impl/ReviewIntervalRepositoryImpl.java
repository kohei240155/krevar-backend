package com.example.iruka_backend.repository.impl;

import java.util.Map;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import com.example.iruka_backend.repository.ReviewIntervalRepository;
import jakarta.transaction.Transactional;

@Transactional
@Repository
public class ReviewIntervalRepositoryImpl implements ReviewIntervalRepository {


    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * レビュー間隔を取得する
     *
     * @param reviewIntervalId
     * @return レビュー間隔
     */
    @Override
    public int getIntervalDay(int reviewIntervalId) {

        Map<String, Object> params = new HashMap<>();
        params.put("reviewIntervalId", reviewIntervalId);

        Integer intervalDay = namedParameterJdbcTemplate.queryForObject(SQL_GET_INTERVAL_DAY,
                params, Integer.class);

        return intervalDay != null ? intervalDay : 0;
    }

    private static final String SQL_GET_INTERVAL_DAY = """
            SELECT
                interval_days
            FROM
                review_intervals
            WHERE
                id = :reviewIntervalId
            """;

}
