package com.example.iruka_backend.repository;

import java.util.List;
import com.example.iruka_backend.entity.DeckEntity;
import com.example.iruka_backend.requestdto.DeckCreateRequest;
import com.example.iruka_backend.requestdto.DeckUpdateRequest;

public interface DeckRepository {

  /**
   * ユーザーIDに紐づくデッキを取得する
   *
   * @param userId ユーザーID
   * @return デッキリスト
   */
  List<DeckEntity> findByUserId(int userId);

  /**
   * デッキを保存する
   *
   * @param deckRequest デッキリクエスト
   */
  void save(DeckCreateRequest deckCreateRequest);

  /**
   * デッキを更新する
   *
   * @param deckUpdateRequest デッキ更新リクエスト
   * @param deckId デッキID
   */
  void update(DeckUpdateRequest deckUpdateRequest, Long deckId);

  /**
   * デッキを削除する
   *
   * @param deckId デッキID
   */
  void delete(Long deckId);

  /**
   * デッキに紐づくユーザーIDを取得する
   *
   * @param deckId デッキID
   */
  int getUserIdByDeckId(Long deckId);
}
