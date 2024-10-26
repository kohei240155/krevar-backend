package com.example.krevar_backend.service;

import com.example.krevar_backend.requestdto.DeckCreateRequest;
import com.example.krevar_backend.requestdto.DeckUpdateRequest;
import com.example.krevar_backend.responsedto.DeckListResponse;

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
  void save(Long userId, DeckCreateRequest deckCreateRequest);

  /**
   * デッキを更新する
   *
   * @param deckRequest デッキリクエスト
   */
  void update(Long userId, DeckUpdateRequest deckUpdateRequest, Long deckId);

  /**
   * デッキを削除する
   *
   * @param deckId デッキID
   */
  void delete(Long deckId);
}
