package com.example.iruka_backend.repository.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.iruka_backend.entity.WordEntity;
import com.example.iruka_backend.repository.QuizRepository;
import com.example.iruka_backend.repository.mapper.WordEntityRowMapper;

import jakarta.transaction.Transactional;

@Transactional
@Repository
public class QuizRepositoryImpl implements QuizRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String FIND_RANDOM_WORD_BY_DECK_ID_AND_DATE_SQL = """
            SELECT
                *
            FROM
                words
            WHERE
                next_practice_date = CURRENT_DATE
            AND
                deck_id = :deckId
            AND
                is_normal_mode_correct = FALSE
            ORDER BY RAND()
            """;

    @Override
    public List<WordEntity> findRandomWordByDeckIdAndDate(Long deckId) {
        Map<String, Object> params = new HashMap<>();
        params.put("deckId", deckId);
        return namedParameterJdbcTemplate.query(FIND_RANDOM_WORD_BY_DECK_ID_AND_DATE_SQL, params, new WordEntityRowMapper());
    }

    private static final String FIND_COUNT_BY_DECK_ID_AND_IS_NORMAL_MODE_CORRECT_FALSE_AND_NEXT_PRACTICE_DATE_SQL = """
            SELECT
                COUNT(*)
            FROM
                words
            WHERE
                deck_id = :deckId
            AND
                is_normal_mode_correct = FALSE
            AND
                next_practice_date = CURRENT_DATE
            """;

    @Override
    public Long findCountByDeckIdAndIsNormalModeCorrectFalseAndNextPracticeDate(Long deckId) {
        Map<String, Object> params = new HashMap<>();
        params.put("deckId", deckId);
        return namedParameterJdbcTemplate.queryForObject(FIND_COUNT_BY_DECK_ID_AND_IS_NORMAL_MODE_CORRECT_FALSE_AND_NEXT_PRACTICE_DATE_SQL, params, Long.class);
    }

    private static final String FIND_TODAY_NORMAL_QUESTION_COUNT_BY_DECK_ID_SQL = """
            SELECT
                COUNT(*)
            FROM
                words
            WHERE
                deck_id = :deckId
            AND
                next_practice_date = CURRENT_DATE
            AND
                is_normal_mode_correct = FALSE
            """;

    @Override
    public Long findTodayNormalQuestionCountByDeckId(Long deckId) {
        Map<String, Object> params = new HashMap<>();
        params.put("deckId", deckId);
        return namedParameterJdbcTemplate.queryForObject(FIND_TODAY_NORMAL_QUESTION_COUNT_BY_DECK_ID_SQL, params, Long.class);
    }

    private static final String FIND_TODAY_EXTRA_QUESTION_COUNT_BY_DECK_ID_SQL = """
            SELECT
                COUNT(*)
            FROM
                words
            WHERE
                deck_id = :deckId
            AND
                is_extra_mode_correct = FALSE
            """;

    @Override
    public Long findTodayExtraQuestionCountByDeckId(Long deckId) {
        Map<String, Object> params = new HashMap<>();
        params.put("deckId", deckId);
        return namedParameterJdbcTemplate.queryForObject(FIND_TODAY_EXTRA_QUESTION_COUNT_BY_DECK_ID_SQL, params, Long.class);
    }

    private static final String FIND_WORDS_BY_DECK_ID_SQL = """
            SELECT
                *
            FROM
                words
            WHERE
                deck_id = :deckId
            """;

    @Override
    public List<WordEntity> findWordsByDeckId(Long deckId) {
        Map<String, Object> params = new HashMap<>();
        params.put("deckId", deckId);
        return namedParameterJdbcTemplate.query(FIND_WORDS_BY_DECK_ID_SQL, params, new WordEntityRowMapper());
    }

    private static final String FIND_EXTRA_WORD_BY_DECK_ID_SQL = """
            SELECT
                *
            FROM
                words
            WHERE
                deck_id = :deckId
            AND
                next_practice_date >= CURRENT_DATE
            AND
                is_extra_mode_correct = FALSE
            ORDER BY
                correct_count ASC,
                next_practice_date ASC
            """;

    @Override
    public List<WordEntity> findExtraWordByDeckId(Long deckId) {
        Map<String, Object> params = new HashMap<>();
        params.put("deckId", deckId);
        return namedParameterJdbcTemplate.query(FIND_EXTRA_WORD_BY_DECK_ID_SQL, params, new WordEntityRowMapper());
    }

    private static final String FIND_COUNT_BY_DECK_ID_AND_IS_NORMAL_MODE_CORRECT_TRUE_AND_NEXT_PRACTICE_DATE_SQL = """
            SELECT
                COUNT(*)
            FROM
                words
            WHERE
                deck_id = :deckId
            AND
                is_normal_mode_correct = TRUE
            AND
                next_practice_date = CURRENT_DATE
            """;

    @Override
    public Long findCountByDeckIdAndIsNormalModeCorrectTrueAndNextPracticeDate(Long deckId) {
        Map<String, Object> params = new HashMap<>();
        params.put("deckId", deckId);
        return namedParameterJdbcTemplate.queryForObject(FIND_COUNT_BY_DECK_ID_AND_IS_NORMAL_MODE_CORRECT_TRUE_AND_NEXT_PRACTICE_DATE_SQL, params, Long.class);
    }

    private static final String FIND_BY_ID_SQL = """
            SELECT
                *
            FROM
                words
            WHERE
                word_id = :wordId
            """;

    @Override
    public Optional<WordEntity> findById(Long id) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("wordId", id);
            WordEntity word = namedParameterJdbcTemplate.queryForObject(FIND_BY_ID_SQL, params, new WordEntityRowMapper());
            return Optional.ofNullable(word);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private static final String SAVE_WORD_SQL = """
            INSERT INTO words (word_id, deck_id, word, is_normal_mode_correct, correct_count, incorrect_count, review_interval_id, next_practice_date, is_extra_mode_correct)
            VALUES (:wordId, :deckId, :word, :isNormalModeCorrect, :correctCount, :incorrectCount, :reviewIntervalId, :nextPracticeDate, :isExtraModeCorrect)
            """;

    private static final String UPDATE_WORD_SQL = """
            UPDATE words
            SET
                deck_id = :deckId,
                word = :word,
                is_normal_mode_correct = :isNormalModeCorrect,
                correct_count = :correctCount,
                incorrect_count = :incorrectCount,
                review_interval_id = :reviewIntervalId,
                next_practice_date = :nextPracticeDate,
                is_extra_mode_correct = :isExtraModeCorrect
            WHERE
                word_id = :wordId
            """;

    @Override
    public WordEntity save(WordEntity word) {
        Map<String, Object> params = new HashMap<>();
        params.put("wordId", word.getId());
        params.put("deckId", word.getDeckId());
        params.put("word", word.getOriginalText());
        params.put("isNormalModeCorrect", word.getIsNormalModeCorrect());
        params.put("correctCount", word.getCorrectCount());
        params.put("incorrectCount", word.getIncorrectCount());
        params.put("reviewIntervalId", word.getReviewIntervalId());
        params.put("nextPracticeDate", word.getNextPracticeDate());
        params.put("isExtraModeCorrect", word.getIsExtraModeCorrect());
        namedParameterJdbcTemplate.update(SAVE_WORD_SQL, params);
        return word;
    }

    @Override
    public WordEntity update(WordEntity word) {
        Map<String, Object> params = new HashMap<>();
        params.put("deckId", word.getDeckId());
        params.put("word", word.getOriginalText());
        params.put("isNormalModeCorrect", word.getIsNormalModeCorrect());
        params.put("correctCount", word.getCorrectCount());
        params.put("incorrectCount", word.getIncorrectCount());
        params.put("reviewIntervalId", word.getReviewIntervalId());
        params.put("nextPracticeDate", word.getNextPracticeDate());
        params.put("isExtraModeCorrect", word.getIsExtraModeCorrect());
        params.put("wordId", word.getId());
        namedParameterJdbcTemplate.update(UPDATE_WORD_SQL, params);
        return word;
    }
}
