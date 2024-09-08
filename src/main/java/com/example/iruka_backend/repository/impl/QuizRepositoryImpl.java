package com.example.iruka_backend.repository.impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import com.example.iruka_backend.entity.ExtraQuizResultEntity;
import com.example.iruka_backend.entity.NormalQuizResultEntity;
import com.example.iruka_backend.entity.WordEntity;
import com.example.iruka_backend.repository.QuizRepository;
import com.example.iruka_backend.repository.mapper.WordEntityRowMapper;

@Transactional
@Repository
public class QuizRepositoryImpl implements QuizRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * ノーマルクイズ取得
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
     * ノーマルクイズの残数取得
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
     * ノーマルクイズ更新
     *
     * @param wordId
     * @param isExtraModeCorrect
     */
    @Override
    public void updateNormalQuiz(NormalQuizResultEntity quizResult) {

        Map<String, Object> params = new HashMap<>();
        params.put("wordId", quizResult.getId());
        params.put("reviewIntervalId", quizResult.getReviewIntervalId());
        params.put("nextPracticeDate", quizResult.getNextPracticeDate());
        params.put("correctCount", quizResult.getCorrectCount());
        params.put("incorrectCount", quizResult.getIncorrectCount());
        params.put("updatedAt", quizResult.getUpdatedAt());

        namedParameterJdbcTemplate.update(SQL_UPDATE_NORMAL_QUIZ, params);
    }

    private static final String SQL_UPDATE_NORMAL_QUIZ = """
            UPDATE
                words
            SET
                review_interval_id = :reviewIntervalId,
                next_practice_date = :nextPracticeDate,
                correct_count = correct_count + :correctCount,
                incorrect_count = incorrect_count + :incorrectCount,
                updated_at = :updatedAt
            WHERE
                id = :wordId
            """;

    /**
     * レビュー間隔を取得する
     *
     * @param wordId
     * @return レビュー間隔
     */
    @Override
    public int getReviewIntervalId(Long wordId) {

        Map<String, Object> params = new HashMap<>();
        params.put("wordId", wordId);

        Integer reviewIntervalId = namedParameterJdbcTemplate
                .queryForObject(SQL_GET_REVIEW_INTERVAL_ID, params, Integer.class);

        return reviewIntervalId != null ? reviewIntervalId : 0;
    }

    private static final String SQL_GET_REVIEW_INTERVAL_ID = """
            SELECT
                review_interval_id
            FROM
                words
            WHERE
                id = :wordId
            """;

    /**
     * エクストラクイズ取得
     *
     * @param deckId デッキID
     * @return クイズ
     */
    @Override
    public WordEntity findExtraQuizByDeckId(Long deckId) {

        Map<String, Object> params = new HashMap<>();
        params.put("deckId", deckId);

        WordEntity word = namedParameterJdbcTemplate.queryForObject(SQL_FIND_EXTRA_QUIZ_BY_DECK_ID,
                params, new WordEntityRowMapper());

        return word;
    }

    private static final String SQL_FIND_EXTRA_QUIZ_BY_DECK_ID = """
            SELECT
                *
            FROM
                words
            WHERE
                deck_id = :deckId AND
                is_extra_mode_correct = 0 AND
                deleted_at IS NULL
            ORDER BY
                RAND()
            LIMIT 1
            """;

    /**
     * エクストラクイズ更新
     *
     * @param wordId
     * @param isExtraModeCorrect
     */
    @Override
    public void updateExtraQuiz(ExtraQuizResultEntity quizResult) {

        Map<String, Object> params = new HashMap<>();
        params.put("wordId", quizResult.getId());
        params.put("isExtraModeCorrect", quizResult.getIsExtraModeCorrect());
        params.put("updatedAt", quizResult.getUpdatedAt());

        namedParameterJdbcTemplate.update(SQL_UPDATE_EXTRA_QUIZ, params);
    }

    private static final String SQL_UPDATE_EXTRA_QUIZ = """
            UPDATE
                words
            SET
                is_extra_mode_correct = :isExtraModeCorrect,
                updated_at = :updatedAt
            WHERE
                id = :wordId
            """;

    /**
     * エクストラクイズの残数取得
     *
     * @param deckId デッキID
     * @return クイズの残数
     */
    @Override
    public int getLeftExtraQuizCount(Long deckId) {

        Map<String, Object> params = new HashMap<>();
        params.put("deckId", deckId);

        Integer count = namedParameterJdbcTemplate.queryForObject(SQL_GET_LEFT_EXTRA_QUIZ_COUNT,
                params, Integer.class);

        return count != null ? count : 0;
    }

    private static final String SQL_GET_LEFT_EXTRA_QUIZ_COUNT = """
            SELECT
                COUNT(*)
            FROM
                words
            WHERE
                deck_id = :deckId AND
                is_extra_mode_correct = 0 AND
                deleted_at IS NULL
            """;

    /**
     * エクストラクイズの結果をリセット
     *
     * @param deckId
     */
    @Override
    public void resetExtraQuiz(Long deckId) {

        Map<String, Object> params = new HashMap<>();
        params.put("deckId", deckId);
        params.put("updatedAt", LocalDateTime.now());
        namedParameterJdbcTemplate.update(SQL_RESET_EXTRA_QUIZ, params);
    }

    private static final String SQL_RESET_EXTRA_QUIZ = """
            UPDATE
                words
            SET
                is_extra_mode_correct = 0,
                updated_at = :updatedAt
            WHERE
                deck_id = :deckId
            """;
}
