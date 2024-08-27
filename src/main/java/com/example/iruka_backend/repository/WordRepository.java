package com.example.iruka_backend.repository;

import java.util.List;
import java.util.Optional; // 追加
import java.time.LocalDate; // 追加
import java.util.HashMap; // 追加
import java.util.Map; // 追加

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate; // 追加
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;

import com.example.iruka_backend.entity.WordEntity;
import com.example.iruka_backend.repository.mapper.WordEntityRowMapper;

import jakarta.transaction.Transactional;

@Transactional
@Repository
public class WordRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate; // 追加

    private static final String FIND_WORDS_BY_DECK_ID_SQL = """
                SELECT
                    *
                FROM
                    words
                WHERE
                    deck_id = :deckId
            """;

    public List<WordEntity> findWordsByDeckId(Long deckId) {
        Map<String, Object> params = new HashMap<>();
        params.put("deckId", deckId);
        return namedParameterJdbcTemplate.query(FIND_WORDS_BY_DECK_ID_SQL, params, new WordEntityRowMapper());
    }

    private static final String SAVE_WORD_SQL = """
            INSERT INTO words (original_text, translated_text, nuance_text, image_url, review_interval_id, next_practice_date, correct_count, incorrect_count, is_normal_mode_correct, is_extra_mode_correct, deck_id) 
            VALUES (:originalText, :translatedText, :nuanceText, :imageUrl, :reviewIntervalId, :nextPracticeDate, :correctCount, :incorrectCount, :isNormalModeCorrect, :isExtraModeCorrect, :deckId)
            """;

    public WordEntity save(WordEntity word) {
        Map<String, Object> params = new HashMap<>();
        params.put("originalText", word.getOriginalText());
        params.put("translatedText", word.getTranslatedText());
        params.put("nuanceText", word.getNuanceText());
        params.put("imageUrl", word.getImageUrl());
        params.put("reviewIntervalId", word.getReviewIntervalId());
        params.put("nextPracticeDate", word.getNextPracticeDate());
        params.put("correctCount", word.getCorrectCount());
        params.put("incorrectCount", word.getIncorrectCount());
        params.put("isNormalModeCorrect", word.getIsNormalModeCorrect());
        params.put("isExtraModeCorrect", word.getIsExtraModeCorrect());
        params.put("deckId", word.getDeckId());
        namedParameterJdbcTemplate.update(SAVE_WORD_SQL, params);
        return word;
    }

    private static final String FIND_BY_ID_SQL = """
            SELECT
                *
            FROM
                words
            WHERE
                id = :id
            """;

    public Optional<WordEntity> findById(Long wordId) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", wordId);
        List<WordEntity> results = namedParameterJdbcTemplate.query(FIND_BY_ID_SQL, params, new WordEntityRowMapper());
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    private static final String UPDATE_WORD_SQL = """
            UPDATE words SET original_text = :originalText, translated_text = :translatedText, nuance_text = :nuanceText, image_url = :imageUrl, review_interval_id = :reviewIntervalId, next_practice_date = :nextPracticeDate, correct_count = :correctCount, incorrect_count = :incorrectCount, is_normal_mode_correct = :isNormalModeCorrect, is_extra_mode_correct = :isExtraModeCorrect, deck_id = :deckId WHERE id = :id
            """;

    public WordEntity update(WordEntity word) {
        Map<String, Object> params = new HashMap<>();
        params.put("originalText", word.getOriginalText());
        params.put("translatedText", word.getTranslatedText());
        params.put("nuanceText", word.getNuanceText());
        params.put("imageUrl", word.getImageUrl());
        params.put("reviewIntervalId", word.getReviewIntervalId());
        params.put("nextPracticeDate", word.getNextPracticeDate());
        params.put("correctCount", word.getCorrectCount());
        params.put("incorrectCount", word.getIncorrectCount());
        params.put("isNormalModeCorrect", word.getIsNormalModeCorrect());
        params.put("isExtraModeCorrect", word.getIsExtraModeCorrect());
        params.put("deckId", word.getDeckId());
        params.put("id", word.getId());
        namedParameterJdbcTemplate.update(UPDATE_WORD_SQL, params);
        return word;
    }

    private static final String COUNT_BY_DELETED_AT_IS_NULL_SQL = """
            SELECT
                COUNT(*)
            FROM
                words
            WHERE
                deleted_at IS NULL
            """;

    public long countByDeletedAtIsNull() {
        Long count = jdbcTemplate.queryForObject(COUNT_BY_DELETED_AT_IS_NULL_SQL, Long.class);
        return count != null ? count : 0L;
    }

    private static final String FIND_COUNT_BY_DECK_ID_AND_IS_NORMAL_MODE_CORRECT_FALSE_AND_NEXT_PRACTICE_DATE_SQL = """
            SELECT
                COUNT(*)
            FROM
                words
            WHERE
                deck_id = :deckId
            AND
                is_normal_mode_correct = false
            AND next_practice_date <= CURRENT_DATE
            """;

    public Long findCountByDeckIdAndIsNormalModeCorrectFalseAndNextPracticeDate(Long deckId) {
        Map<String, Object> params = new HashMap<>();
        params.put("deckId", deckId);
        return namedParameterJdbcTemplate.queryForObject(FIND_COUNT_BY_DECK_ID_AND_IS_NORMAL_MODE_CORRECT_FALSE_AND_NEXT_PRACTICE_DATE_SQL, params, Long.class);
    }

    private static final String COUNT_BY_DECK_ID_AND_IS_NORMAL_MODE_CORRECT_TRUE_SQL = """
            SELECT
                COUNT(*)
            FROM
                words
            WHERE
                deck_id = :deckId
            AND
                is_normal_mode_correct = true
            """;

    public Long countByDeckIdAndIsNormalModeCorrectTrue(Long deckId) {
        Map<String, Object> params = new HashMap<>();
        params.put("deckId", deckId);
        return namedParameterJdbcTemplate.queryForObject(COUNT_BY_DECK_ID_AND_IS_NORMAL_MODE_CORRECT_TRUE_SQL, params, Long.class);
    }

    private static final String FIND_TODAY_QUESTION_COUNT_BY_DECK_ID_SQL = """
            SELECT
                COUNT(*)
            FROM
                words
            WHERE
                deck_id = :deckId
            AND
                next_practice_date = CURRENT_DATE
            """;

    public Long findTodayQuestionCountByDeckId(Long deckId) {
        Map<String, Object> params = new HashMap<>();
        params.put("deckId", deckId);
        return namedParameterJdbcTemplate.queryForObject(FIND_TODAY_QUESTION_COUNT_BY_DECK_ID_SQL, params, Long.class);
    }

    private static final String COUNT_BY_DECK_ID_AND_NEXT_PRACTICE_DATE_AND_IS_NORMAL_MODE_CORRECT_SQL = """
            SELECT
                COUNT(*)
            FROM
                words
            WHERE
                deck_id = :deckId
            AND
                next_practice_date = CURRENT_DATE
            AND
                is_normal_mode_correct = true
            """;

    public long countByDeckIdAndNextPracticeDateAndIsNormalModeCorrect(Long deckId, LocalDate nextPracticeDate, Boolean isNormalModeCorrect) {
        Map<String, Object> params = new HashMap<>();
        params.put("deckId", deckId);
        params.put("nextPracticeDate", nextPracticeDate);
        params.put("isNormalModeCorrect", isNormalModeCorrect);
        return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(COUNT_BY_DECK_ID_AND_NEXT_PRACTICE_DATE_AND_IS_NORMAL_MODE_CORRECT_SQL, params, Long.class))
                       .orElse(0L);
    }

    private static final String FIND_ALL_BY_DELETED_AT_IS_NULL_SQL = """
            SELECT
                *
            FROM
                words
            WHERE
                deleted_at IS NULL
            LIMIT :limit OFFSET :offset
            """;

    public Page<WordEntity> findAllByDeletedAtIsNull(Pageable pageable) {
        Map<String, Object> params = new HashMap<>();
        params.put("limit", pageable.getPageSize());
        params.put("offset", pageable.getOffset());
        List<WordEntity> words = namedParameterJdbcTemplate.query(FIND_ALL_BY_DELETED_AT_IS_NULL_SQL, params, new WordEntityRowMapper());
        return new PageImpl<>(words, pageable, countByDeletedAtIsNull());
    }
}