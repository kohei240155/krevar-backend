package com.example.iruka_backend.repository.impl;

import java.util.HashMap;
import java.util.Map;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import com.example.iruka_backend.entity.WordEntity;
import com.example.iruka_backend.repository.QuizRepository;
import com.example.iruka_backend.repository.mapper.WordEntityRowMapper;

@Transactional
@Repository
public class QuizRepositoryImpl implements QuizRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String SQL_FIND_BY_DECK_ID = """
                SELECT
                    *
                FROM
                    words
                WHERE
                    deck_id = :deckId AND
                    next_practice_date <= NOW() AND
                    deleted_at IS NULL
                ORDER BY
                    RAND()
                LIMIT 1
            """;

    /**
     * デッキIDを指定してクイズを取得する
     *
     * @param deckId デッキID
     * @return クイズ
     */
    @Override
    public WordEntity findNormalQuizByDeckId(Long deckId) {

        Map<String, Object> params = new HashMap<>();
        params.put("deckId", deckId);

        WordEntity word = namedParameterJdbcTemplate.queryForObject(SQL_FIND_BY_DECK_ID, params,
                new WordEntityRowMapper());

        return word;
    }

    private static final String SQL_GET_LEFT_NORMAL_QUIZ_COUNT = """
            SELECT
                COUNT(*)
            FROM
                words
            WHERE
                deck_id = :deckId AND
                next_practice_date <= NOW() AND
                deleted_at IS NULL
            """;

    /**
     * デッキIDを指定してクイズの残数を取得する
     *
     * @param deckId デッキID
     * @return クイズの残数
     */
    @Override
    public int getLeftNormalQuizCount(Long deckId) {

        Map<String, Object> params = new HashMap<>();
        params.put("deckId", deckId);

        Integer count = namedParameterJdbcTemplate.queryForObject(SQL_GET_LEFT_NORMAL_QUIZ_COUNT,
                params, Integer.class);

        return count != null ? count : 0;
    }
}
