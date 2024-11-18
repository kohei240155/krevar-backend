package com.example.krevar_backend.repository.impl;

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
import com.example.krevar_backend.entity.DeckCreateEntity;
import com.example.krevar_backend.entity.DeckEntity;
import com.example.krevar_backend.entity.DeckUpdateEntity;
import com.example.krevar_backend.repository.DeckRepository;
import com.example.krevar_backend.repository.mapper.DeckEntityRowMapper;

@Transactional
@Repository
public class DeckRepositoryImpl implements DeckRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * ユーザーIDに紐づくデッキを取得する
     *
     * @param userId ユーザーID
     * @return デッキリスト
     */
    @Override
    public List<DeckEntity> findByUserId(Long userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        List<DeckEntity> deck = namedParameterJdbcTemplate.query(FIND_BY_USER_ID_SQL, params,
                new DeckEntityRowMapper());
        return deck;
    }

    private static final String FIND_BY_USER_ID_SQL = """
            SELECT
                *
            FROM
                decks
            WHERE
                user_id = :userId
            AND
                deleted = 0
            """;

    /**
     * デッキIDに紐づくデッキを取得する
     *
     * @param deckId デッキID
     * @return デッキ
     */
    @Override
    public DeckEntity findById(Long deckId) {
        Map<String, Object> params = new HashMap<>();
        params.put("deckId", deckId);
        DeckEntity deck = namedParameterJdbcTemplate.queryForObject(FIND_BY_ID_SQL, params,
                new DeckEntityRowMapper());
        return deck;
    }

    private static final String FIND_BY_ID_SQL = """
            SELECT
                *
            FROM
                decks
            WHERE
                id = :deckId
            AND
                deleted = 0
            """;

    /**
     * デッキを登録する
     *
     * @param deckCreateEntity デッキ登録リクエスト
     */
    @Override
    public void save(DeckCreateEntity deckCreateEntity) {

        // パラメータをマップに格納
        Map<String, Object> params = new HashMap<>();
        params.put("userId", deckCreateEntity.getUserId());
        params.put("deckName", deckCreateEntity.getDeckName());
        params.put("nativeLanguageId", deckCreateEntity.getNativeLanguageId());
        params.put("learningLanguageId", deckCreateEntity.getLearningLanguageId());
        params.put("createdAt", LocalDateTime.now());
        params.put("updatedAt", LocalDateTime.now());

        // SQLを実行してデータを保存
        namedParameterJdbcTemplate.update(SAVE_SQL, params);
    }

    private static final String SAVE_SQL =
            """
                    INSERT INTO
                        decks (user_id, deck_name, native_language_id, learning_language_id, created_at, updated_at)
                    VALUES
                        (:userId, :deckName, :nativeLanguageId, :learningLanguageId, :createdAt, :updatedAt)
                    """;

    /**
     * デッキを更新する
     *
     * @param deckUpdateEntity デッキ更新リクエスト
     * @param deckId デッキID
     */
    @Override
    public void update(DeckUpdateEntity deckUpdateEntity, Long deckId) {

        // パラメータをマップに格納
        Map<String, Object> params = new HashMap<>();
        params.put("userId", deckUpdateEntity.getUserId());
        params.put("deckId", deckId);
        params.put("deckName", deckUpdateEntity.getDeckName());
        params.put("nativeLanguageId", deckUpdateEntity.getNativeLanguageId());
        params.put("learningLanguageId", deckUpdateEntity.getLearningLanguageId());
        params.put("updatedAt", LocalDateTime.now());

        // SQLを実行してデータを更新
        namedParameterJdbcTemplate.update(UPDATE_SQL, params);
    }

    private static final String UPDATE_SQL = """
            UPDATE
                decks
            SET
                deck_name = :deckName,
                native_language_id = :nativeLanguageId,
                learning_language_id = :learningLanguageId,
                updated_at = :updatedAt
            WHERE
                id = :deckId
            """;

    /**
     * デッキを削除する
     *
     * @param deckId デッキID
     */
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

    /**
     * デッキIDに紐づくユーザーIDを取得する
     *
     * @param deckId デッキID
     * @return ユーザーID
     */
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


    /**
     * デッキIDに紐づくクイズの進捗を取得する
     *
     * @param deckId デッキID
     * @return クイズの進捗
     */
    @Override
    public int getRemainingQuestionCountByDeckId(Long deckId) {

        // パラメータを設定
        Map<String, Object> params = new HashMap<>();
        params.put("deckId", deckId);
        params.put("today", LocalDate.now());

        // クエリを実行
        Integer result = namedParameterJdbcTemplate.queryForObject(FIND_PROGRESS_BY_DECK_ID_SQL,
                params, Integer.class);

        return Optional.ofNullable(result).orElse(0);
    }

    private static final String FIND_PROGRESS_BY_DECK_ID_SQL = """
            SELECT
                COUNT(*)
            FROM
                words
            WHERE
                deck_id = :deckId
                and next_practice_date <= :today
                and deleted = 0
            """;
}
