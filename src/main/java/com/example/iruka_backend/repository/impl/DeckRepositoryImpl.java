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
import com.example.iruka_backend.entity.DeckEntity;
import com.example.iruka_backend.repository.DeckRepository;
import com.example.iruka_backend.repository.mapper.DeckEntityRowMapper;
import com.example.iruka_backend.requestdto.DeckCreateRequest;
import com.example.iruka_backend.requestdto.DeckUpdateRequest;

@Transactional
@Repository
public class DeckRepositoryImpl implements DeckRepository {

  @Autowired
  private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  private static final String FIND_BY_ID_SQL = """
      SELECT
          *
      FROM
          decks
      WHERE
          user_id = :userId
      AND
          deleted_at IS NULL
      """;

  @Override
  public List<DeckEntity> findByUserId(int userId) {
    Map<String, Object> params = new HashMap<>();
    params.put("userId", userId);
    List<DeckEntity> deck =
        namedParameterJdbcTemplate.query(FIND_BY_ID_SQL, params, new DeckEntityRowMapper());
    return deck;
  }

  private static final String SAVE_SQL = """
      INSERT INTO
          decks (user_id, deck_name, created_at, updated_at)
      VALUES
          (:userId, :deckName, :createdAt, :updatedAt)
      """;

  @Override
  public void save(DeckCreateRequest deckCreateRequest) {

    // パラメータをマップに格納
    Map<String, Object> params = new HashMap<>();
    params.put("userId", deckCreateRequest.getUserId());
    params.put("deckName", deckCreateRequest.getDeckName());
    params.put("createdAt", LocalDateTime.now());
    params.put("updatedAt", LocalDateTime.now());

    // SQLを実行してデータを保存
    namedParameterJdbcTemplate.update(SAVE_SQL, params);
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
  public void update(DeckUpdateRequest deckUpdateRequest, Long deckId) {

    // パラメータをマップに格納
    Map<String, Object> params = new HashMap<>();
    params.put("userId", deckUpdateRequest.getUserId());
    params.put("deckId", deckId);
    params.put("deckName", deckUpdateRequest.getDeckName());
    params.put("updatedAt", LocalDateTime.now());

    // SQLを実行してデータを更新
    namedParameterJdbcTemplate.update(UPDATE_SQL, params);
  }

  private static final String DELETE_SQL = """
      UPDATE
          decks
      SET
          deleted_at = :deletedAt
      WHERE
          id = :deckId
      """;

  @Override
  public void delete(Long deckId) {

    // パラメータをマップに格納
    Map<String, Object> params = new HashMap<>();
    params.put("deckId", deckId);
    params.put("deletedAt", LocalDateTime.now());

    // SQLを実行してデータを削除
    namedParameterJdbcTemplate.update(DELETE_SQL, params);
  }

  private static final String GET_USER_ID_BY_DECK_ID_SQL = """
      SELECT
          user_id
      FROM
          decks
      WHERE
          id = :deckId
      AND
          deleted_at IS NULL
      """;

  @Override
  public int getUserIdByDeckId(Long deckId) {

    // パラメータをマップに格納
    Map<String, Object> params = new HashMap<>();
    params.put("deckId", deckId);

    // SQLを実行してデータを取得
    Integer userId = namedParameterJdbcTemplate.queryForObject(GET_USER_ID_BY_DECK_ID_SQL, params,
        Integer.class);

    return Optional.ofNullable(userId).orElse(0);
  }
}
