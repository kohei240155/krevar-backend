package com.example.iruka_backend.service;

import com.example.iruka_backend.requestdto.DeckCreateRequest;
import com.example.iruka_backend.requestdto.DeckUpdateRequest;
import com.example.iruka_backend.responsedto.DeckListResponse;

public interface DeckService {

  /**
   * ユーザーIDに紐づくデッキを取得する
   *
   * @param userId ユーザーID
   * @param page ページ番号
   * @param limit ページサイズ
   * @return デッキリスト
   */
  DeckListResponse getDecksByUserId(Long userId, Long page, Long limit);

  /**
   * デッキを保存する
   *
   * @param userId ユーザーID
   * @param deckRequest デッキリクエスト
   * @return 保存されたデッキ
   */
  void save(DeckCreateRequest deckCreateRequest);

  /**
   * デッキを更新する
   *
   * @param deckRequest デッキリクエスト
   */
  void update(DeckUpdateRequest deckUpdateRequest, Long deckId);

  /**
   * デッキを削除する
   *
   * @param deckId デッキID
   */
  void delete(Long deckId);

  /**
   * ユーザーIDをチェックする
   *
   * @param userId ユーザーID
   */
  void verifyUser(Long requestedUserId);

  /**
   * デッキに紐づくユーザーIDを取得する
   *
   * @param deckId デッキID
   */
  Long getUserIdByDeckId(Long deckId);
}
