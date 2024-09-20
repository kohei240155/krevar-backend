package com.example.iruka_backend.repository.impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import com.example.iruka_backend.entity.DeckCreateEntity;
import com.example.iruka_backend.entity.DeckEntity;
import com.example.iruka_backend.entity.DeckUpdateEntity;
import com.example.iruka_backend.repository.DeckRepository;
import com.example.iruka_backend.repository.mapper.DeckEntityRowMapper;

@Transactional
@Repository
public class DeckRepositoryImpl implements DeckRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<DeckEntity> findByUserId(Long userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        List<DeckEntity> deck =
                namedParameterJdbcTemplate.query(FIND_BY_ID_SQL, params, new DeckEntityRowMapper());
        return deck;
    }

    private static final String FIND_BY_ID_SQL = """
            SELECT
                *
            FROM
                decks
            WHERE
                user_id = :userId
            AND
                deleted = 0
            """;

    @Override
    public void save(DeckCreateEntity deckCreateEntity) {

        // パラメータをマップに格納
        Map<String, Object> params = new HashMap<>();
        params.put("userId", deckCreateEntity.getUserId());
        params.put("deckName", deckCreateEntity.getDeckName());
        params.put("createdAt", LocalDateTime.now());
        params.put("updatedAt", LocalDateTime.now());

        // SQLを実行してデータを保存
        namedParameterJdbcTemplate.update(SAVE_SQL, params);
    }

    private static final String SAVE_SQL = """
            INSERT INTO
                decks (user_id, deck_name, created_at, updated_at)
            VALUES
                (:userId, :deckName, :createdAt, :updatedAt)
            """;

    @Override
    public void update(DeckUpdateEntity deckUpdateEntity, Long deckId) {

        // パラメータをマップに格納
        Map<String, Object> params = new HashMap<>();
        params.put("userId", deckUpdateEntity.getUserId());
        params.put("deckId", deckId);
        params.put("deckName", deckUpdateEntity.getDeckName());
        params.put("updatedAt", LocalDateTime.now());

        // SQLを実行してデータを更新
        namedParameterJdbcTemplate.update(UPDATE_SQL, params);
    }

    private static final String UPDATE_SQL = """
            UPDATE
                decks
            SET
                deck_name = :deckName,
                updated_at = :updatedAt
            WHERE
                id = :deckId
            AND
                user_id = :userId
            """;

    @Override
    public void delete(Long deckId) {

        // パラメータをマップに格納
        Map<String, Object> params = new HashMap<>();
        params.put("deckId", deckId);
        params.put("deleted", 1);

        // SQLを実行してデータを削除
        namedParameterJdbcTemplate.update(DELETE_SQL, params);
    }

    private static final String DELETE_SQL = """
            UPDATE
                decks
            SET
                deleted = :deleted
            WHERE
                id = :deckId
            """;

    @Override
    public Long getUserIdByDeckId(Long deckId) {

        // パラメータをマップに格納
        Map<String, Object> params = new HashMap<>();
        params.put("deckId", deckId);

        // SQLを実行してデータを取得
        Long userId = namedParameterJdbcTemplate.queryForObject(GET_USER_ID_BY_DECK_ID_SQL, params,
                Long.class);

        return Optional.ofNullable(userId).orElse(null);
    }

    private static final String GET_USER_ID_BY_DECK_ID_SQL = """
            SELECT
                user_id
            FROM
                decks
            WHERE
                id = :deckId
            AND
                deleted = 0
            """;
}
