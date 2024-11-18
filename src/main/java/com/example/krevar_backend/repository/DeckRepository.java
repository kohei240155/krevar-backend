package com.example.krevar_backend.repository;

import java.util.List;
import com.example.krevar_backend.entity.DeckCreateEntity;
import com.example.krevar_backend.entity.DeckEntity;
import com.example.krevar_backend.entity.DeckUpdateEntity;

public interface DeckRepository {

  /**
   * ユーザーIDに紐づくデッキを取得する
   *
   * @param userId ユーザーID
   * @return デッキ一覧
   */
  List<DeckEntity> findDecksByUserId(Long userId);

  /**
   * デッキIDからデッキを取得する
   *
   * @param deckId デッキID
   * @return デッキ
   */
  DeckEntity findById(Long deckId);

  /**
   * デッキを保存する
   *
   * @param deckRequest デッキリクエスト
   */
  void save(DeckCreateEntity deckCreateEntity);

  /**
   * デッキを更新する
   *
   * @param deckUpdateRequest デッキ更新リクエスト
   * @param deckId デッキID
   */
  void update(DeckUpdateEntity deckUpdateEntity, Long deckId);

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
  Long getUserIdByDeckId(Long deckId);

  /**
   * デッキIDに紐づくクイズの進捗を取得する
   *
   * @param deckId デッキID
   * @return クイズの進捗
   */
  int getRemainingQuestionCountByDeckId(Long deckId);
}
