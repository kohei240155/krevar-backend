package com.example.iruka_backend.repository.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import com.example.iruka_backend.entity.WordEntity;
import com.example.iruka_backend.repository.WordRepository;
import com.example.iruka_backend.repository.mapper.WordEntityRowMapper;
import com.example.iruka_backend.requestdto.WordCreateRequest;
import com.example.iruka_backend.requestdto.WordUpdateRequest;

@Transactional
@Repository
public class WordRepositoryImpl implements WordRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String FIND_PROGRESS_BY_DECK_ID_SQL = """
            SELECT
                COUNT(*)
            FROM
                words
            WHERE
                deck_id = :deckId
                and next_practice_date <= :today
                and deleted_at is null
            """;

    @Override
    public int getProgressByDeckId(Long deckId) {

        // パラメータを設定
        Map<String, Object> params = new HashMap<>();
        params.put("deckId", deckId);
        params.put("today", LocalDate.now());

        // クエリを実行
        Integer result = namedParameterJdbcTemplate.queryForObject(FIND_PROGRESS_BY_DECK_ID_SQL,
                params, Integer.class);

        return Optional.ofNullable(result).orElse(0);
    }


    private static final String FIND_WORDS_BY_DECK_ID_SQL = """
            SELECT
                *
            FROM
                words
            WHERE
                deck_id = :deckId
                and deleted_at is null
            """;

    @Override
    public List<WordEntity> findByDeckId(Long deckId) {

        // パラメータを設定
        Map<String, Object> params = new HashMap<>();
        params.put("deckId", deckId);

        // クエリを実行
        return namedParameterJdbcTemplate.query(FIND_WORDS_BY_DECK_ID_SQL, params,
                new WordEntityRowMapper());
    }

    private static final String SAVE_WORD_SQL = """
            INSERT INTO words (
                deck_id,
                original_text,
                translated_text,
                nuance_text,
                image_url
            ) VALUES (
                :deckId,
                :originalText,
                :translatedText,
                :nuanceText,
                :imageUrl
            )
            """;

    @Override
    public void save(WordCreateRequest wordCreateRequest) {

        // パラメータを設定
        Map<String, Object> params = new HashMap<>();
        params.put("deckId", wordCreateRequest.getDeckId());
        params.put("originalText", wordCreateRequest.getOriginalText());
        params.put("translatedText", wordCreateRequest.getTranslatedText());
        params.put("nuanceText", wordCreateRequest.getNuanceText());
        params.put("imageUrl", wordCreateRequest.getImageUrl());

        // クエリを実行
        namedParameterJdbcTemplate.update(SAVE_WORD_SQL, params);
    }

    private static final String UPDATE_WORD_SQL = """
            UPDATE
                words
            SET
                original_text = :originalText,
                translated_text = :translatedText,
                nuance_text = :nuanceText,
            WHERE
                id = :wordId
            """;

    @Override
    public void update(WordUpdateRequest wordUpdateRequest) {

        // パラメータを設定
        Map<String, Object> params = new HashMap<>();
        params.put("originalText", wordUpdateRequest.getOriginalText());
        params.put("translatedText", wordUpdateRequest.getTranslatedText());
        params.put("nuanceText", wordUpdateRequest.getNuanceText());
        params.put("wordId", wordUpdateRequest.getWordId());

        // クエリを実行
        namedParameterJdbcTemplate.update(UPDATE_WORD_SQL, params);
    }

    private static final String FIND_WORD_BY_ID_SQL = """
            SELECT
                *
            FROM
                words
            WHERE
                id = :wordId
            """;

    @Override
    public WordEntity findById(Long wordId) {

        // パラメータを設定
        Map<String, Object> params = new HashMap<>();
        params.put("wordId", wordId);

        // クエリを実行
        return namedParameterJdbcTemplate.queryForObject(FIND_WORD_BY_ID_SQL, params,
                new WordEntityRowMapper());
    }

    private static final String DELETE_WORD_SQL = """
            UPDATE
                words
            SET
                deleted_at = :deletedAt
            WHERE
                id = :wordId
            """;

    @Override
    public void delete(Long wordId) {

        // パラメータを設定
        Map<String, Object> params = new HashMap<>();
        params.put("wordId", wordId);
        params.put("deletedAt", LocalDateTime.now());

        // クエリを実行
        namedParameterJdbcTemplate.update(DELETE_WORD_SQL, params);
    }
}
