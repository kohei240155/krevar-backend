package com.example.krevar_backend.repository.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import com.example.krevar_backend.entity.WordCreateEntity;
import com.example.krevar_backend.entity.WordEntity;
import com.example.krevar_backend.entity.WordUpdateEntity;
import com.example.krevar_backend.repository.WordRepository;
import com.example.krevar_backend.repository.mapper.WordEntityRowMapper;

@Transactional
@Repository
public class WordRepositoryImpl implements WordRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String FIND_WORDS_BY_DECK_ID_SQL = """
            SELECT
                *
            FROM
                words
            WHERE
                deck_id = :deckId
                and deleted = 0
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
    public void save(WordCreateEntity wordCreateEntity) {

        // パラメータを設定
        Map<String, Object> params = new HashMap<>();
        params.put("deckId", wordCreateEntity.getDeckId());
        params.put("originalText", wordCreateEntity.getOriginalText());
        params.put("translatedText", wordCreateEntity.getTranslatedText());
        params.put("nuanceText", wordCreateEntity.getNuanceText());
        params.put("imageUrl", wordCreateEntity.getImageUrl());

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
                updated_at = :updatedAt
            WHERE
                id = :wordId
            """;

    @Override
    public void update(WordUpdateEntity wordUpdateEntity) {

        // パラメータを設定
        Map<String, Object> params = new HashMap<>();
        params.put("originalText", wordUpdateEntity.getOriginalText());
        params.put("translatedText", wordUpdateEntity.getTranslatedText());
        params.put("nuanceText", wordUpdateEntity.getNuanceText());
        params.put("wordId", wordUpdateEntity.getWordId());
        params.put("updatedAt", wordUpdateEntity.getUpdatedAt());
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
                deleted = :deleted
            WHERE
                id = :wordId
            """;

    @Override
    public void delete(Long wordId) {

        // パラメータを設定
        Map<String, Object> params = new HashMap<>();
        params.put("wordId", wordId);
        params.put("deleted", 1);

        // クエリを実行
        namedParameterJdbcTemplate.update(DELETE_WORD_SQL, params);
    }
}
