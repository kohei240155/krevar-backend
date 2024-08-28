package com.example.iruka_backend.repository.impl;

import com.example.iruka_backend.entity.DeckEntity;
import com.example.iruka_backend.repository.DeckRepository;
import com.example.iruka_backend.repository.mapper.DeckEntityRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Transactional
@Repository
public class DeckRepositoryImpl implements DeckRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String FIND_BY_ID_SQL = """
            SELECT
                *
            FROM
                decks
            WHERE
                id = :id
            AND
                deleted_at IS NULL
            """;

    @Override
    public Optional<DeckEntity> findById(Long id) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("id", id);
            DeckEntity deck = namedParameterJdbcTemplate.queryForObject(FIND_BY_ID_SQL, params, new DeckEntityRowMapper());
            return Optional.ofNullable(deck);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private static final String FIND_BY_DELETED_AT_IS_NULL_SQL = """
            SELECT
                *
            FROM
                decks
            WHERE
                deleted_at IS NULL
            """;

    @Override
    public List<DeckEntity> findByDeletedAtIsNull() {
        return jdbcTemplate.query(FIND_BY_DELETED_AT_IS_NULL_SQL, new DeckEntityRowMapper());
    }

    private static final String FIND_ALL_BY_DELETED_AT_IS_NULL_SQL = """
            SELECT
                *
            FROM
                decks
            WHERE
                deleted_at IS NULL
            LIMIT :limit OFFSET :offset
            """;

    @Override
    public Page<DeckEntity> findAllByDeletedAtIsNull(Pageable pageable) {
        Map<String, Object> params = new HashMap<>();
        params.put("limit", pageable.getPageSize());
        params.put("offset", pageable.getOffset());
        List<DeckEntity> decks = namedParameterJdbcTemplate.query(FIND_ALL_BY_DELETED_AT_IS_NULL_SQL, params, new DeckEntityRowMapper());
        long total = countByDeletedAtIsNull();
        return new PageImpl<>(decks, pageable, total);
    }

    private static final String COUNT_BY_DELETED_AT_IS_NULL_SQL = """
            SELECT
                COUNT(*)
            FROM
                decks
            WHERE
                deleted_at IS NULL
            """;

    @Override
    public long countByDeletedAtIsNull() {
        Long count = jdbcTemplate.queryForObject(COUNT_BY_DELETED_AT_IS_NULL_SQL, Long.class);
        return count != null ? count : 0L;
    }

    private static final String FIND_COUNT_BY_DECK_ID_AND_IS_NORMAL_MODE_CORRECT_FALSE_AND_NEXT_PRACTICE_DATE_SQL = """
            SELECT
                COUNT(*)
            FROM
                decks
            WHERE
                id = :deckId
            AND
                is_normal_mode_correct = false
            AND next_practice_date <= CURRENT_DATE
            """;

    @Override
    public Long findCountByDeckIdAndIsNormalModeCorrectFalseAndNextPracticeDate(Long deckId) {
        Map<String, Object> params = new HashMap<>();
        params.put("deckId", deckId);
        return namedParameterJdbcTemplate.queryForObject(FIND_COUNT_BY_DECK_ID_AND_IS_NORMAL_MODE_CORRECT_FALSE_AND_NEXT_PRACTICE_DATE_SQL, params, Long.class);
    }

    private static final String COUNT_BY_DECK_ID_AND_IS_NORMAL_MODE_CORRECT_TRUE_SQL = """
            SELECT
                COUNT(*)
            FROM
                decks
            WHERE
                id = :deckId
            AND
                is_normal_mode_correct = true
            """;

    @Override
    public Long countByDeckIdAndIsNormalModeCorrectTrue(Long deckId) {
        Map<String, Object> params = new HashMap<>();
        params.put("deckId", deckId);
        return namedParameterJdbcTemplate.queryForObject(COUNT_BY_DECK_ID_AND_IS_NORMAL_MODE_CORRECT_TRUE_SQL, params, Long.class);
    }

    private static final String FIND_TODAY_QUESTION_COUNT_BY_DECK_ID_SQL = """
            SELECT
                COUNT(*)
            FROM
                decks
            WHERE
                id = :deckId
            AND
                next_practice_date = CURRENT_DATE
            """;

    @Override
    public Long findTodayQuestionCountByDeckId(Long deckId) {
        Map<String, Object> params = new HashMap<>();
        params.put("deckId", deckId);
        return namedParameterJdbcTemplate.queryForObject(FIND_TODAY_QUESTION_COUNT_BY_DECK_ID_SQL, params, Long.class);
    }

    private static final String COUNT_BY_DECK_ID_AND_NEXT_PRACTICE_DATE_AND_IS_NORMAL_MODE_CORRECT_SQL = """
            SELECT
                COUNT(*)
            FROM
                decks
            WHERE
                id = :deckId
            AND
                next_practice_date = CURRENT_DATE
            AND
                is_normal_mode_correct = true
            """;

    @Override
    public long countByDeckIdAndNextPracticeDateAndIsNormalModeCorrect(Long deckId, LocalDate nextPracticeDate, Boolean isNormalModeCorrect) {
        Map<String, Object> params = new HashMap<>();
        params.put("deckId", deckId);
        params.put("nextPracticeDate", nextPracticeDate);
        params.put("isNormalModeCorrect", isNormalModeCorrect);
        Long count = namedParameterJdbcTemplate.queryForObject(COUNT_BY_DECK_ID_AND_NEXT_PRACTICE_DATE_AND_IS_NORMAL_MODE_CORRECT_SQL, params, Long.class);
        return count != null ? count : 0L;
    }

    private static final String SAVE_DECK_SQL = """
            INSERT INTO decks (deck_name, user_id, last_practiced_date, created_at, updated_at, deleted_at)
            VALUES (:deckName, :userId, :lastPracticedDate, :createdAt, :updatedAt, :deletedAt)
            """;

    @Override
    public DeckEntity save(DeckEntity deck) {
        Map<String, Object> params = new HashMap<>();
        params.put("deckName", deck.getDeckName());
        params.put("userId", deck.getUserId());
        params.put("lastPracticedDate", deck.getLastPracticedDate());
        params.put("createdAt", deck.getCreatedAt());
        params.put("updatedAt", deck.getUpdatedAt());
        params.put("deletedAt", deck.getDeletedAt());
        namedParameterJdbcTemplate.update(SAVE_DECK_SQL, params);
        return deck;
    }

    private static final String UPDATE_DECK_SQL = """
            UPDATE decks
            SET
                deck_name = :deckName,
                updated_at = :updatedAt,
                deleted_at = :deletedAt
            WHERE
                id = :id
            """;

    @Override
    public DeckEntity update(DeckEntity deck) {
        Map<String, Object> params = new HashMap<>();
        params.put("deckName", deck.getDeckName());
        params.put("updatedAt", deck.getUpdatedAt());
        params.put("deletedAt", deck.getDeletedAt());
        params.put("id", deck.getId());
        namedParameterJdbcTemplate.update(UPDATE_DECK_SQL, params);
        return deck;
    }
}
